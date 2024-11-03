package dev.gaddal.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import dev.gaddal.repository.core_donation_management.UserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.server.auth.jwt.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

/**
 * Configuration and utility class for handling JWT (JSON Web Token) operations.
 * This class provides methods for creating, validating, and managing JWTs used for authentication.
 *
 * @property userRepository Repository for user-related database operations.
 * @property config Configuration parameters for JWT operations.
 */
class JwtConfig(
    private val userRepository: UserRepository,
    private val config: JwtConfigParams,
) {
    // Algorithm used for signing JWTs
    private val algorithm: Algorithm = Algorithm.HMAC256(config.secret)

    val realm: String = config.realm

    /**
     * JWT verifier instance used to validate tokens.
     */
    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(config.issuer)
        .withAudience(config.audience)
        .build()

    /**
     * Calculates the expiration timestamp for access tokens.
     */
    val accessTokenExpirationTimestamp: Long
        get() = System.currentTimeMillis() + config.accessTokenDuration.inWholeMilliseconds

    /**
     * Creates an access token for the given user ID.
     *
     * @param id The ID of the user for whom the token is being created.
     * @return A JWT string representing the access token.
     */
    fun createAccessToken(id: Int): String = createJwtToken(id, config.accessTokenDuration.inWholeMilliseconds)

    /**
     * Creates a refresh token for the given user ID.
     *
     * @param id The ID of the user for whom the token is being created.
     * @return A JWT string representing the refresh token.
     */
    fun createRefreshToken(id: Int): String = createJwtToken(id, config.refreshTokenDuration.inWholeMilliseconds)

    /**
     * Internal function to create a JWT with specified expiration.
     *
     * @param id The ID of the user for whom the token is being created.
     * @param expireIn The duration in milliseconds after which the token will expire.
     * @return A JWT string.
     * @throws JwtException if token creation fails.
     */
    private fun createJwtToken(id: Int, expireIn: Long): String {
        return try {
            JWT.create()
                .withIssuer(config.issuer)
                .withAudience(config.audience)
                .withClaim(CLAIM_ID, id)
                .withExpiresAt(Date(System.currentTimeMillis() + expireIn))
                .sign(algorithm)
        } catch (e: JWTCreationException) {
            logger.error(e) { "Failed to create JWT token" }
            throw JwtException("Token creation failed", e)
        }
    }

    /**
     * Creates a custom JWT with specified claims and expiration.
     *
     * @param claims A map of custom claims to include in the token.
     * @param expireIn The duration in milliseconds after which the token will expire.
     * @return A JWT string with custom claims.
     * @throws JwtException if token creation fails.
     */
    fun createCustomToken(claims: Map<String, Any>, expireIn: Long): String {
        return try {
            val token = JWT.create()
                .withIssuer(config.issuer)
                .withAudience(config.audience)
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
            throw JwtException("Custom token creation failed", e)
        }
    }

    /**
     * Validates a JWT credential and returns a JWTPrincipal if valid.
     *
     * @param credential The JWT credential to validate.
     * @return A JWTPrincipal if the token is valid, null otherwise.
     */
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

    /**
     * Validates a user exists for the given user ID in the credential.
     *
     * @param userId The ID of the user to validate.
     * @param credential The JWT credential being validated.
     * @return A JWTPrincipal if the user is valid, null otherwise.
     */
    private suspend fun validateUser(userId: Int, credential: JWTCredential): JWTPrincipal? {
        return withContext(Dispatchers.IO) {
            try {
                when (val result = userRepository.getUserById(userId)) {
                    is Result.Success -> {
                        logger.info { "JWT validation successful for user ID: $userId" }
                        JWTPrincipal(credential.payload)
                    }

                    is Result.Error -> {
                        logger.warn { "JWT validation failed: User not found" }
                        null
                    }
                }
            } catch (e: Exception) {
                logger.error(e) { "Error during user validation for JWT" }
                null
            }
        }
    }

    /**
     * Checks if the audience in the credential matches the configured audience.
     *
     * @param credential The JWT credential to check.
     * @return true if the audience matches, false otherwise.
     */
    private fun audienceMatches(credential: JWTCredential): Boolean =
        credential.payload.audience.contains(config.audience)

    /**
     * Checks if the given audience string matches the configured audience.
     *
     * @param audience The audience string to check.
     * @return true if the audience matches, false otherwise.
     */
    fun audienceMatches(audience: String): Boolean = config.audience == audience

    companion object {
        private val logger = KotlinLogging.logger {}

        /**
         * Claim key used for storing the user ID in JWTs.
         */
        const val CLAIM_ID = "id"
    }
}