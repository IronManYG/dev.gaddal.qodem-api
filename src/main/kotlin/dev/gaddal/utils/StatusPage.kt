package dev.gaddal.utils

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

private val logger = KotlinLogging.logger {}

/**
 * Configures status pages for handling exceptions globally in a Ktor application.
 * This function defines custom responses for various exceptions, ensuring that
 * the client receives clear and actionable error messages.
 *
 * It handles:
 * 1. MismatchedInputException for JSON structure mismatches
 * 2. JsonParseException for invalid JSON format
 * 3. ValidationException for input validation errors
 * 4. NotFoundException for resource not found errors
 * 5. General Throwable for any unhandled exceptions
 */
fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<MismatchedInputException> { call, cause ->
            logger.warn(cause) { "Mismatched input exception occurred" }
            val missingParamName =
                cause.path.filter { it.fieldName != null }.joinToString(separator = ".") { it.fieldName }
            val errorMessage = if (missingParamName.isNotEmpty()) {
                "Missing or invalid attribute '$missingParamName'"
            } else {
                cause.message ?: "Mismatched input"
            }
            val error = BaseResponse.ErrorResponse(
                message = errorMessage,
                statusCode = HttpStatusCode.BadRequest
            )
            call.respond(error.statusCode, error)
        }

        exception<JsonParseException> { call, cause ->
            logger.warn(cause) { "JSON parse exception occurred" }
            val error = BaseResponse.ErrorResponse(
                message = "Invalid JSON format: ${cause.message}",
                statusCode = HttpStatusCode.BadRequest
            )
            call.respond(error.statusCode, error)
        }

        exception<ValidationException> { call, cause ->
            logger.warn(cause) { "Validation exception occurred" }
            val error = BaseResponse.ErrorResponse(
                message = cause.message ?: "Validation failed",
                statusCode = HttpStatusCode.BadRequest
            )
            call.respond(error.statusCode, error)
        }

        exception<NotFoundException> { call, cause ->
            logger.warn(cause) { "Not found exception occurred" }
            val error = BaseResponse.ErrorResponse(
                message = cause.message ?: "Resource not found",
                statusCode = HttpStatusCode.NotFound
            )
            call.respond(error.statusCode, error)
        }

        exception<Throwable> { call, cause ->
            logger.error(cause) { "Unhandled exception occurred" }
            val error = BaseResponse.ErrorResponse(
                message = "An unexpected error occurred",
                statusCode = HttpStatusCode.InternalServerError
            )
            call.respond(error.statusCode, error)
        }
    }
}