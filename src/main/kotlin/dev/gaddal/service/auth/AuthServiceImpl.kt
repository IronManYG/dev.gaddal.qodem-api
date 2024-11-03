package dev.gaddal.service.auth

import dev.gaddal.data.models.params.LoginParams
import dev.gaddal.data.models.params.RefreshTokenParams
import dev.gaddal.data.models.params.UserParams
import dev.gaddal.repository.auth.AuthRepository
import dev.gaddal.repository.refresh_token.RefreshTokenRepository
import dev.gaddal.utils.*
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * Implementation of [AuthService] that interacts with [AuthRepository] and [RefreshTokenRepository] to perform authentication operations.
 *
 * This class provides methods for registering a new user, logging in a user, and refreshing user tokens.
 *
 * @property authRepository The repository responsible for user authentication operations.
 * @property refreshTokenRepository The service responsible for refreshing user tokens.
 * @property jwtConfig Configuration for JWT operations.
 */
class AuthServiceImpl(
    private val authRepository: AuthRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtConfig: JwtConfig,
) : AuthService {

    companion object {
        private val logger = KotlinLogging.logger {}
        private val messageBuilder = MessageBuilder("Authentication")
    }

    override suspend fun registerUser(userParams: UserParams): BaseResponse<Any> {
        return when (val result = authRepository.registerUser(userParams)) {
            is Result.Success -> {
                val user = result.value
                val accessToken = jwtConfig.createAccessToken(user.id)
                BaseResponse.SuccessResponse(
                    data = mapOf(
                        "user" to user,
                        "accessToken" to accessToken,
                        "expiresAt" to jwtConfig.accessTokenExpirationTimestamp
                    ),
                    message = messageBuilder.createdSuccess()
                )
            }

            is Result.Error -> ErrorHandler.handleError(
                result.error,
                logger,
                messageBuilder.processingError(),
                messageBuilder.processingError()
            )
        }
    }

    override suspend fun loginUser(params: LoginParams): BaseResponse<Any> {
        return when (val result = authRepository.loginUser(params)) {
            is Result.Success -> {
                val user = result.value
                val accessToken = jwtConfig.createAccessToken(user.id)
                val refreshToken = jwtConfig.createRefreshToken(user.id)
                refreshTokenRepository.add(user.id, refreshToken)
                BaseResponse.SuccessResponse(
                    data = mapOf(
                        "user" to user,
                        "accessToken" to accessToken,
                        "refreshToken" to refreshToken,
                        "expiresAt" to jwtConfig.accessTokenExpirationTimestamp
                    ),
                    message = messageBuilder.loginSuccess()
                )
            }

            is Result.Error -> ErrorHandler.handleError(
                result.error,
                logger,
                messageBuilder.loginFailure(),
                messageBuilder.processingError()
            )
        }
    }

    override suspend fun refreshToken(params: RefreshTokenParams): BaseResponse<Any> {
        return when (val result = refreshTokenRepository.getRefreshTokenByValue(params.refreshToken)) {
            is Result.Success -> {
                val refreshToken = result.value
                val newAccessToken = jwtConfig.createAccessToken(refreshToken.userId)
                BaseResponse.SuccessResponse(
                    data = mapOf(
                        "accessToken" to newAccessToken,
                        "expiresAt" to jwtConfig.accessTokenExpirationTimestamp
                    ),
                    message = messageBuilder.tokenRefreshed()
                )
            }

            is Result.Error -> ErrorHandler.handleError(
                result.error,
                logger,
                messageBuilder.invalidToken(),
                messageBuilder.processingError()
            )
        }
    }

    override suspend fun logoutUser(refreshToken: String): BaseResponse<Any> {
        return withContext(Dispatchers.IO) {
            when (val result = refreshTokenRepository.invalidateRefreshToken(refreshToken)) {
                is Result.Success -> {
                    BaseResponse.SuccessResponse(
                        data = null,
                        message = messageBuilder.logoutSuccess()
                    )
                }

                is Result.Error -> ErrorHandler.handleError(
                    result.error,
                    logger,
                    messageBuilder.invalidToken(),
                    messageBuilder.processingError()
                )
            }
        }
    }

//    override suspend fun changePassword(userId: Int, newPassword: String, currentRefreshToken: String): BaseResponse<Any> {
//        return when (val passwordChangeResult = authRepository.changePassword(userId, newPassword)) {
//            is Result.Success -> {
//                // If password change was successful, invalidate all refresh tokens
//                val allTokensInvalidationResult = refreshTokenRepository.invalidateAllUserTokens(userId)
//                val currentTokenInvalidationResult = refreshTokenRepository.invalidateRefreshToken(currentRefreshToken)
//
//                if (allTokensInvalidationResult is Result.Success && currentTokenInvalidationResult is Result.Success) {
//                    BaseResponse.SuccessResponse(
//                        data = null,
//                        message = messageBuilder.passwordChangeSuccess()
//                    )
//                } else {
//                    logger.warn { "Password changed but failed to invalidate all tokens for user $userId" }
//                    BaseResponse.SuccessResponse(
//                        data = null,
//                        message = "${messageBuilder.passwordChangeSuccess()} Please log in again for security reasons."
//                    )
//                }
//            }
//            is Result.Error -> {
//                logger.error { "Failed to change password for user $userId: ${passwordChangeResult.error}" }
//                ErrorHandler.handleError(
//                    passwordChangeResult.error,
//                    logger,
//                    messageBuilder.notFound(),
//                    messageBuilder.processingError()
//                )
//            }
//        }
//    }

}