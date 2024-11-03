package dev.gaddal.repository.refresh_token

import dev.gaddal.data.models.entities.RefreshToken
import dev.gaddal.utils.Result

/**
 * Repository responsible for managing refresh tokens in the application.
 *
 * This interface defines methods for retrieving and adding refresh tokens to the database.
 */
interface RefreshTokenRepository {

    /**
     * Retrieves a refresh token by its unique value.
     *
     * @param value The unique value of the refresh token to retrieve.
     * @return The [RefreshToken] object if found, or null if no refresh token is found with the specified ID.
     */
    suspend fun getRefreshTokenByValue(value: String): Result<RefreshToken>

    /**
     * Adds a new refresh token based on the provided parameters.
     *
     * @param userId The ID of the user to associate with the new refresh token.
     * @param value The value of the new refresh token.
     * @return The newly created [RefreshToken] object if successful.
     */
    suspend fun add(userId: Int, value: String): Result<RefreshToken>

    suspend fun invalidateRefreshToken(value: String): Result<Boolean>
}