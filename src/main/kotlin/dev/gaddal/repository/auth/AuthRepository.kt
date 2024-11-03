package dev.gaddal.repository.auth

import dev.gaddal.data.models.entities.User
import dev.gaddal.data.models.params.LoginParams
import dev.gaddal.data.models.params.UserParams
import dev.gaddal.utils.Result

/**
 * Repository interface for managing user authentication operations.
 * This interface defines methods for registering, logging in, and finding users by email.
 */
interface AuthRepository {
    /**
     * Registers a new user with the provided parameters.
     *
     * @param userParams The UserParams object containing the user details.
     * @return A [Result] containing the registered [User] if successful, or an error if registration fails.
     */
    suspend fun registerUser(userParams: UserParams): Result<User>

    /**
     * Attempts to log in a user with the given email and password.
     *
     * @param loginParams The LoginParams object containing the user's login details.
     * @return A [Result] containing the logged-in [User] if successful, or an error if login fails.
     */
    suspend fun loginUser(loginParams: LoginParams): Result<User>

    /**
     * Finds a user by their email address.
     *
     * @param email The email address to search for.
     * @return A [Result] containing the [User] if found, or an error if the user is not found.
     */
    suspend fun findUserByEmail(email: String): Result<User>
}