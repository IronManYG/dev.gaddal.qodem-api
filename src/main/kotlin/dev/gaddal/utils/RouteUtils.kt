package dev.gaddal.utils

import io.github.oshai.kotlinlogging.KLogger
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

/**
 * Utility object for handling route operations and exceptions in a consistent manner.
 */
object RouteUtils {
    /**
     * Handles a route by executing the provided block and responding appropriately.
     *
     * This function wraps the execution of route handlers, providing consistent error handling
     * and response formatting across all routes.
     *
     * @param call The ApplicationCall for the current request.
     * @param logger The logger to use for logging any errors.
     * @param block The suspend function to execute, which should return the response data.
     */
    suspend inline fun <reified T : Any> handleRoute(
        call: ApplicationCall,
        logger: KLogger,
        block: () -> T
    ) {
        try {
            when (val result = block()) {
                is BaseResponse<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    (ResponseUtils.respondWithOptionalMeta(call, result as BaseResponse<Any>))
                }
                else -> call.respond(result)
            }
        } catch (e: Exception) {
            handleException(call, e, logger)
        }
    }

    /**
     * Handles exceptions that occur during route processing.
     *
     * This function provides consistent error responses for various types of exceptions.
     *
     * @param call The ApplicationCall for the current request.
     * @param e The exception that was thrown.
     * @param logger The logger to use for logging the error.
     */
    suspend fun handleException(call: ApplicationCall, e: Exception, logger: KLogger) {
        when (e) {
            is IllegalArgumentException -> call.respond(HttpStatusCode.BadRequest, e.message ?: "Invalid input")
            is ContentTransformationException -> call.respond(
                HttpStatusCode.BadRequest,
                "Invalid request body: ${e.message}"
            )
            is NotFoundException -> call.respond(HttpStatusCode.NotFound, e.message ?: "Resource not found")
            is ValidationException -> call.respond(HttpStatusCode.BadRequest, e.message ?: "Validation failed")
            is UnauthorizedException -> call.respond(HttpStatusCode.Unauthorized, e.message ?: "Unauthorized")
            else -> {
                logger.error(e) { "Unexpected error in route handling" }
                call.respond(HttpStatusCode.InternalServerError, "An unexpected error occurred")
            }
        }
    }
}

/**
 * Exception thrown when a requested resource is not found.
 *
 * @param message The error message describing what resource was not found.
 */
class NotFoundException(message: String) : Exception(message)

/**
 * Exception thrown when validation of input data fails.
 *
 * @param message The error message describing the validation failure.
 */
class ValidationException(message: String) : Exception(message)