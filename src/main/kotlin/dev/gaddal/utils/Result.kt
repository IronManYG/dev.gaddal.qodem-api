package dev.gaddal.utils

/**
 * Represents the result of an operation.
 */
sealed class Result<out T> {
    /**
     * Represents a successful operation with a value of type T.
     */
    data class Success<out T>(val value: T) : Result<T>()

    /**
     * Represents a failed operation with an error.
     */
    data class Error(val error: OperationError) : Result<Nothing>()
}