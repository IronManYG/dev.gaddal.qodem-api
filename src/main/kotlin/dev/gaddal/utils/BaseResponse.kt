package dev.gaddal.utils

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.ktor.http.*

/**
 * A sealed class to define a base response structure for HTTP requests. It's designed to be extended by specific
 * response types to ensure type-safe usage and a consistent response format across different API endpoints.
 *
 * @param T the type of data included in the success response.
 * @property statusCode The HTTP status code associated with the response, annotated with [JsonIgnore] to omit it from the serialized JSON.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)  // Exclude null properties from the JSON output
@JsonSerialize  // Indicates that the class is to be serialized
sealed class BaseResponse<T>(
    @JsonIgnore open val statusCode: HttpStatusCode
) {
    /**
     * A data class representing a successful response from the server.
     *
     * @param data The payload of the response which can be any type specified by T.
     * @param message An optional message that can accompany the response.
     * @param statusCode The HTTP status code, defaulted to 200 OK, but can be overridden if needed.
     */
    @JsonSerialize  // Ensure this class is included in serialization process
    data class SuccessResponse<T>(
        val data: T?,  // The data payload of the response
        val message: String? = null,  // An optional message with the response
        @JsonIgnore  // Ignore this property during serialization
        override val statusCode: HttpStatusCode = HttpStatusCode.OK  // Default HTTP status code
    ) : BaseResponse<T>(statusCode)

    /**
     * A data class representing an error response from the server.
     *
     * @param message The error message describing what went wrong.
     * @param statusCode The HTTP status code for the error, defaulted to 400 Bad Request, but can be overridden if needed.
     */
    @JsonSerialize  // Ensure this class is included in serialization process
    data class ErrorResponse(
        val message: String,  // The error message
        @JsonIgnore  // Ignore this property during serialization
        override val statusCode: HttpStatusCode = HttpStatusCode.BadRequest  // Default HTTP status code
    ) : BaseResponse<Any>(statusCode)
}
