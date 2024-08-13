package dev.gaddal.utils

import io.ktor.server.plugins.*

/**
 * Utility class for input validation operations.
 */
object ValidationUtils {
    /**
     * Validates and parses an integer parameter.
     *
     * @param value The string value to parse.
     * @param paramName The name of the parameter (for error messages).
     * @return The parsed integer value.
     * @throws BadRequestException if the value is null or not a valid integer.
     */
    fun validateIntParameter(value: String?, paramName: String): Int {
        return value?.toIntOrNull() ?: throw BadRequestException("Invalid $paramName")
    }

    /**
     * Validates that a string is not blank.
     *
     * @param value The string value to validate.
     * @param fieldName The name of the field (for error messages).
     * @throws BadRequestException if the value is blank.
     */
    fun validateNotBlank(value: String?, fieldName: String) {
        if (value.isNullOrBlank()) {
            throw BadRequestException("$fieldName cannot be blank")
        }
    }

    /**
     * Validates that a number is positive.
     *
     * @param value The number to validate.
     * @param fieldName The name of the field (for error messages).
     * @throws BadRequestException if the value is not positive.
     */
    fun validatePositive(value: Int, fieldName: String) {
        if (value < 0) {
            throw BadRequestException("$fieldName must be positive")
        }
    }

    /**
     * Validates pagination parameters.
     *
     * @param page The page number.
     * @param limit The number of items per page.
     * @throws BadRequestException if either parameter is not positive.
     */
    fun validatePaginationParams(page: Int, limit: Int) {
        validatePositive(page, "Page number")
        validatePositive(limit, "Limit")
    }
}