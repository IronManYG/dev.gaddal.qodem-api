package dev.gaddal.utils

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

/**
 * The default value for the 'meta' query parameter.
 */
const val DEFAULT_META = false

object ResponseUtils {
    /**
     * Responds to an HTTP request based on the specified metadata preference.
     *
     * This utility function facilitates response handling by conditionally formatting the output
     * based on the 'meta' query parameter. If 'meta' is true, it sends a structured response.
     * If 'meta' is false, it sends only the essential data or a minimal error message.
     *
     * @param call The context of the current HTTP call to send responses.
     * @param result The `BaseResponse` object containing the response data or error details.
     * @param meta A boolean indicating whether to include metadata in the response.
     */
    suspend fun respondWithOptionalMeta(
        call: ApplicationCall,
        result: BaseResponse<Any>,
        meta: Boolean = call.request.queryParameters["meta"]?.toBoolean() ?: DEFAULT_META
    ) {
        if (meta) {
            val response = when (result) {
                is BaseResponse.SuccessResponse -> mapOf(
                    "data" to result.data,
                    "message" to result.message,
                    "statusCode" to result.statusCode.value
                )
                is BaseResponse.ErrorResponse -> mapOf(
                    "error" to result.message,
                    "statusCode" to result.statusCode.value
                )
            }
            call.respond(result.statusCode, response)
        } else {
            when (result) {
                is BaseResponse.SuccessResponse<*> -> {
                    call.respond(result.statusCode, result.data ?: HttpStatusCode.NoContent)
                }
                is BaseResponse.ErrorResponse -> {
                    call.respond(result.statusCode, mapOf("error" to result.message))
                }
            }
        }
    }
}