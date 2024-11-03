package dev.gaddal.utils

/**
 * Represents different types of errors that can occur during various operations.
 */
sealed class OperationError {
    /**
     * Represents an error when a requested resource is not found.
     */
    data class NotFound(val message: String) : OperationError()

    /**
     * Represents a database-related error.
     */
    data class Database(val message: String) : OperationError()

    /**
     * Represents an error due to invalid input parameters.
     */
    data class InvalidInput(val message: String) : OperationError()

    /**
     * Represents an error due to a conflict with existing data.
     */
    data class Conflict(val message: String) : OperationError()

    /**
     * Represents a general error for cases not covered by other specific error types.
     */
    data class General(val message: String) : OperationError()
}