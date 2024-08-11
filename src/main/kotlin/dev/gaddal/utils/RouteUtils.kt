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

                else -> call.respond(BaseResponse.SuccessResponse(data = result))
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
        val errorResponse = when (e) {
            is IllegalArgumentException -> BaseResponse.ErrorResponse(
                message = e.message ?: "Invalid input",
                statusCode = HttpStatusCode.BadRequest
            )

            is ContentTransformationException -> BaseResponse.ErrorResponse(
                message = "Invalid request body: ${e.message}",
                statusCode = HttpStatusCode.BadRequest
            )

            is NotFoundException -> BaseResponse.ErrorResponse(
                message = e.message ?: "Resource not found",
                statusCode = HttpStatusCode.NotFound
            )

            is ValidationException -> BaseResponse.ErrorResponse(
                message = e.message ?: "Validation failed",
                statusCode = HttpStatusCode.BadRequest
            )

            is UnauthorizedException -> BaseResponse.ErrorResponse(
                message = e.message ?: "Unauthorized",
                statusCode = HttpStatusCode.Unauthorized
            )

            else -> {
                logger.error(e) { "Unexpected error in route handling" }
                BaseResponse.ErrorResponse(
                    message = "An unexpected error occurred",
                    statusCode = HttpStatusCode.InternalServerError
                )
            }
        }
        call.respond(errorResponse.statusCode, errorResponse)
    }
}