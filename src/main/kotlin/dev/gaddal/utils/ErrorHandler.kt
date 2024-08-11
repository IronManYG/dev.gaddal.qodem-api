package dev.gaddal.utils

import io.github.oshai.kotlinlogging.KLogger
import io.ktor.http.*

/**
 * Utility object for handling errors and generating appropriate [BaseResponse.ErrorResponse] instances.
 *
 * This object provides a centralized way to handle different types of [OperationError],
 * log appropriate messages, and create consistent error responses across the application.
 */
object ErrorHandler {
    /**
     * Handles an [OperationError] and returns an appropriate [BaseResponse.ErrorResponse].
     *
     * This function processes different types of operation errors, logs relevant information,
     * and creates a standardized error response. It uses provided messages for common error types
     * and the error's own message for more specific errors.
     *
     * @param operationError The error to be handled.
     * @param logger The logger to use for logging error information.
     * @param notFoundMessage The message to use for "not found" errors.
     * @param processingErrorMessage The message to use for database or general processing errors.
     * @return A [BaseResponse.ErrorResponse] with appropriate status code and message.
     */
    fun handleError(
        operationError: OperationError,
        logger: KLogger,
        notFoundMessage: String,
        processingErrorMessage: String
    ): BaseResponse.ErrorResponse {
        return when (operationError) {
            is OperationError.NotFound -> {
                logger.warn { "Resource not found: ${operationError.message}" }
                BaseResponse.ErrorResponse(
                    statusCode = HttpStatusCode.NotFound,
                    message = notFoundMessage
                )
            }

            is OperationError.Database -> {
                logger.error { "Database error: ${operationError.message}" }
                BaseResponse.ErrorResponse(
                    statusCode = HttpStatusCode.InternalServerError,
                    message = processingErrorMessage
                )
            }

            is OperationError.InvalidInput -> {
                logger.warn { "Invalid input: ${operationError.message}" }
                BaseResponse.ErrorResponse(
                    statusCode = HttpStatusCode.BadRequest,
                    message = operationError.message
                )
            }

            is OperationError.General -> {
                logger.error { "General error: ${operationError.message}" }
                BaseResponse.ErrorResponse(
                    statusCode = HttpStatusCode.InternalServerError,
                    message = operationError.message
                )
            }
        }
    }
}