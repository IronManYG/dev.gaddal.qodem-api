package dev.gaddal.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import dev.gaddal.repository.core_donation_management.UserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours

class JwtConfig private constructor(
    private val application: Application,
    private val userRepository: UserRepository
) {
    private val logger = KotlinLogging.logger {}

    private val secret: String
    private val issuer: String
    private val audience: String
    val realm: String
    private val algorithm: Algorithm
    private val accessTokenDuration: Duration
    private val refreshTokenDuration: Duration

    init {
        try {
            secret = getConfigProperty("jwt.secret")
            issuer = getConfigProperty("jwt.issuer")
            audience = getConfigProperty("jwt.audience")
            realm = getConfigProperty("jwt.realm")
            accessTokenDuration = getConfigProperty("jwt.accessTokenDuration").toLong().hours
            refreshTokenDuration = getConfigProperty("jwt.refreshTokenDuration").toLong().days
            algorithm = Algorithm.HMAC256(secret)
        } catch (e: Exception) {
            logger.error(e) { "Failed to initialize JwtConfig" }
            throw RuntimeException("JWT configuration error", e)
        }
    }

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .withAudience(audience)
        .build()

    val accessTokenExpirationTimestamp: Long
        get() = System.currentTimeMillis() + accessTokenDuration.inWholeMilliseconds

    fun createAccessToken(id: Int): String = createJwtToken(id, accessTokenDuration.inWholeMilliseconds)

    fun createRefreshToken(id: Int): String = createJwtToken(id, refreshTokenDuration.inWholeMilliseconds)

    private fun createJwtToken(id: Int, expireIn: Long): String {
        return try {
            JWT.create()
                .withIssuer(issuer)
                .withAudience(audience)
                .withClaim(CLAIM_ID, id)
                .withExpiresAt(Date(System.currentTimeMillis() + expireIn))
                .sign(algorithm)
        } catch (e: JWTCreationException) {
            logger.error(e) { "Failed to create JWT token" }
            throw RuntimeException("Token creation failed", e)
        }
    }

    fun createCustomToken(claims: Map<String, Any>, expireIn: Long): String {
        return try {
            val token = JWT.create()
                .withIssuer(issuer)
                .withAudience(audience)
                .withExpiresAt(Date(System.currentTimeMillis() + expireIn))

            claims.forEach { (key, value) ->
                when (value) {
                    is Int -> token.withClaim(key, value)
                    is Long -> token.withClaim(key, value)
                    is String -> token.withClaim(key, value)
                    is Boolean -> token.withClaim(key, value)
                    // Add more types as needed
                }
            }

            token.sign(algorithm)
        } catch (e: JWTCreationException) {
            logger.error(e) { "Failed to create custom JWT token" }
            throw RuntimeException("Custom token creation failed", e)
        }
    }

    suspend fun customValidator(credential: JWTCredential): JWTPrincipal? {
        val userId = credential.payload.getClaim(CLAIM_ID).asInt()
        return when {
            userId == null -> {
                logger.warn { "JWT validation failed: Missing user ID claim" }
                null
            }

            !audienceMatches(credential) -> {
                logger.warn { "JWT validation failed: Audience mismatch" }
                null
            }

            else -> validateUser(userId, credential)
        }
    }

    private suspend fun validateUser(userId: Int, credential: JWTCredential): JWTPrincipal? {
        return try {
            when (val result = userRepository.getUserById(userId)) {
                is Result.Success -> {
                    logger.info { "JWT validation successful for user ID: $userId" }
                    JWTPrincipal(credential.payload)
                }

                is Result.Error -> {
                    TODO()
                }
            }
        } catch (e: Exception) {
            logger.error(e) { "Error during user validation for JWT" }
            null
        }
    }

    private fun audienceMatches(credential: JWTCredential): Boolean =
        credential.payload.audience.contains(audience)

    fun audienceMatches(audience: String): Boolean = this.audience == audience

    private fun getConfigProperty(path: String): String =
        application.environment.config.property(path).getString()

    companion object {
        const val CLAIM_ID = "id"

        lateinit var instance: JwtConfig
            private set

        fun initialize(application: Application, userRepository: UserRepository) {
            synchronized(this) {
                if (!this::instance.isInitialized) {
                    instance = JwtConfig(application, userRepository)
                }
            }
        }
    }
}