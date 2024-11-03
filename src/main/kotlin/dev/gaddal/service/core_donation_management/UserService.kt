package dev.gaddal.service.core_donation_management

import dev.gaddal.data.models.params.UserParams
import dev.gaddal.utils.BaseResponse

/**
 * Service interface for managing user-related operations.
 * This interface defines methods for creating, reading, updating, and deleting user data,
 * as well as retrieving paginated lists of users.
 */
interface UserService {
    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id The unique identifier of the user to retrieve.
     * @return A [BaseResponse] containing the user data if found, or an error message if not found.
     */
    suspend fun getUserById(id: Int): BaseResponse<Any>

    /**
     * Retrieves all users.
     *
     * @return A [BaseResponse] containing a list of all users, or an error message if the operation fails.
     */
    suspend fun getUsers(): BaseResponse<Any>

    /**
     * Retrieves a paginated list of users.
     *
     * @param page The page number to retrieve (1-indexed).
     * @param limit The maximum number of users per page.
     * @return A [BaseResponse] containing a paginated list of users, or an error message if the operation fails.
     */
    suspend fun getUsers(page: Int, limit: Int): BaseResponse<Any>

    /**
     * Updates an existing user.
     *
     * @param id The unique identifier of the user to update.
     * @param userParams The new parameters for the user.
     * @return A [BaseResponse] containing the updated user data, or an error message if the operation fails.
     */
    suspend fun updateUser(id: Int, userParams: UserParams): BaseResponse<Any>

    /**
     * Deletes a user from the system.
     *
     * @param userId The unique identifier of the user to delete.
     * @return A [BaseResponse] indicating the success or failure of the deletion operation.
     */
    suspend fun deleteUser(userId: Int): BaseResponse<Any>
}