package dev.gaddal.repository.core_donation_management

import dev.gaddal.data.models.common.PaginatedResult
import dev.gaddal.data.models.entities.User
import dev.gaddal.data.models.params.UserParams
import dev.gaddal.utils.Result

/**
 * Repository interface for managing user-related operations.
 * This interface defines methods for creating, reading, updating, and deleting user data,
 * as well as retrieving paginated lists of users.
 */
interface UserRepository {
    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id The unique identifier of the user to retrieve.
     * @return A [Result] containing the [User] if found, or an error if not found or if a database error occurs.
     */
    suspend fun getUserById(id: Int): Result<User>

    /**
     * Retrieves all users from the database.
     *
     * @return A [Result] containing a list of all [User]s, or an error if a database error occurs.
     */
    suspend fun getUsers(): Result<List<User>>

    /**
     * Retrieves a paginated list of users.
     *
     * @param page The page number to retrieve (1-indexed).
     * @param limit The maximum number of users per page.
     * @return A [Result] containing a [PaginatedResult] of [User]s, or an error if a database error occurs.
     */
    suspend fun getUsers(page: Int, limit: Int): Result<PaginatedResult<User>>

    /**
     * Adds a new user to the database.
     *
     * @param userParams The parameters for creating the new user.
     * @return A [Result] containing the newly created [User], or an error if creation fails.
     */
    suspend fun addUser(userParams: UserParams): Result<User>

    /**
     * Updates an existing user in the database.
     *
     * @param id The unique identifier of the user to update.
     * @param userParams The new parameters for the user.
     * @return A [Result] containing the updated [User], or an error if update fails or user is not found.
     */
    suspend fun updateUser(id: Int, userParams: UserParams): Result<User>

    /**
     * Deletes a user from the database.
     *
     * @param userId The unique identifier of the user to delete.
     * @return A [Result] indicating success (true) or failure (error) of the deletion operation.
     */
    suspend fun deleteUser(userId: Int): Result<Boolean>
}