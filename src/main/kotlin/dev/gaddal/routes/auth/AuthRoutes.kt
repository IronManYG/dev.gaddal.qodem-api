package dev.gaddal.routes.auth

import dev.gaddal.controller.auth.AuthController
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

/**
 * Configures the authentication routes for the application.
 *
 * This function sets up the routing for authentication-related endpoints, including
 * user registration, login, token refresh, and logout. It uses [AuthController] to handle
 * the business logic for each route.
 *
 * @receiver [Application] The Ktor application to configure routes for.
 */
fun Application.configureAuthRoutes() {
    // Inject AuthController using Koin
    val authController: AuthController by inject()

    routing {
        route("/auth") {
            /**
             * POST /auth/register
             * Registers a new user in the system.
             */
            post("/register") {
                authController.registerUser(call)
            }

            /**
             * POST /auth/login
             * Authenticates a user and returns access and refresh tokens.
             */
            post("/login") {
                authController.loginUser(call)
            }

            /**
             * POST /auth/refresh
             * Refreshes the access token using a valid refresh token.
             */
            post("/refresh") {
                authController.refreshToken(call)
            }

            /**
             * POST /auth/logout
             * Logs out a user by invalidating their refresh token.
             */
            post("/logout") {
                authController.logoutUser(call)
            }
        }
    }
}