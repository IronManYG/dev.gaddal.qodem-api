package dev.gaddal.utils

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

const val CLAIM_ID = "id"  // Custom claim key used to store the user ID.

/**
 * Utility class for authentication-related operations.
 */
object AuthUtils {
    /**
     * Extracts the authenticated user ID from the call.
     *
     * @param call The ApplicationCall to extract the user ID from.
     * @return The authenticated user's ID, or null if not authenticated.
     */
    private fun extractPrincipalId(call: ApplicationCall): Int? {
        return call.principal<JWTPrincipal>()
            ?.payload
            ?.getClaim(CLAIM_ID)
            ?.asInt()
    }

    /**
     * Extracts the authenticated user ID from the call.
     * Throws an exception if the user is not authenticated.
     *
     * @param call The ApplicationCall to extract the user ID from.
     * @return The authenticated user's ID.
     * @throws UnauthorizedException if the user is not authenticated.
     */
    fun extractAuthenticatedUserId(call: ApplicationCall): Int {
        return extractPrincipalId(call) ?: throw UnauthorizedException("User not authenticated")
    }
}

/**
 * Custom exception for unauthorized access attempts.
 *
 * @param message The error message.
 */
class UnauthorizedException(message: String) : Exception(message)