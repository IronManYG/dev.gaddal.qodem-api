package dev.gaddal.controller.auth

import dev.gaddal.data.models.params.LoginParams
import dev.gaddal.data.models.params.RefreshTokenParams
import dev.gaddal.data.models.params.UserParams
import dev.gaddal.service.auth.AuthService
import dev.gaddal.utils.RouteUtils
import dev.gaddal.utils.ValidationUtils
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.server.application.*
import io.ktor.server.request.*
import org.apache.coyote.BadRequestException

/**
 * Controller responsible for handling authentication-related HTTP requests.
 *
 * This controller acts as an intermediary between the routing layer and the [AuthService],
 * handling operations related to user authentication, such as registration, login, and token refresh.
 *
 * @property authService Service for authentication operations.
 */
class AuthController(
    private val authService: AuthService,
) {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    /**
     * Handles the request to register a new user.
     *
     * @param call The ApplicationCall containing the request details.
     */
    suspend fun registerUser(call: ApplicationCall) {
        logger.info { "Received request to register new auth/register" }
        RouteUtils.handleRoute(call, logger) {
            val userParams = call.receive<UserParams>()
            ValidationUtils.validateUserParams(userParams)
            authService.registerUser(userParams)
        }
    }

    /**
     * Handles the request to log in a user.
     *
     * @param call The ApplicationCall containing the request details.
     */
    suspend fun loginUser(call: ApplicationCall) {
        logger.info { "Received request to login auth/login" }
        RouteUtils.handleRoute(call, logger) {
            val params = call.receive<LoginParams>()
            ValidationUtils.validateLoginParams(params)
            authService.loginUser(params)
        }
    }

    /**
     * Handles the request to refresh a user's token.
     *
     * @param call The ApplicationCall containing the request details.
     */
    suspend fun refreshToken(call: ApplicationCall) {
        logger.info { "Received request to update auth/refresh" }
        RouteUtils.handleRoute(call, logger) {
            val params = call.receive<RefreshTokenParams>()
            ValidationUtils.validateRefreshTokenParams(params)
            authService.refreshToken(params)
        }
    }

    suspend fun logoutUser(call: ApplicationCall) {
        logger.info { "Received request to logout auth/logout" }
        RouteUtils.handleRoute(call, logger) {
            val refreshToken = call.request.header("Refresh-Token")
                ?: throw BadRequestException("Refresh token is required")
            authService.logoutUser(refreshToken)
        }
    }
}