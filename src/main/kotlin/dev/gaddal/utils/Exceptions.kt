package dev.gaddal.utils

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

/**
 * Exception thrown when an unauthorized access attempt is made.
 *
 * @param message The error message describing the unauthorized access attempt.
 */
class UnauthorizedException(message: String) : Exception(message)

/**
 * Exception thrown when an error occurs while processing a JWT.
 *
 * @param message The error message.
 * @param cause The underlying cause of the error.
 */
class JwtException(message: String, cause: Throwable? = null) : Exception(message, cause)

// Add any other custom exceptions here