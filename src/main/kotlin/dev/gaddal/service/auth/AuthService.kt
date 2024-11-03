package dev.gaddal.service.auth

import dev.gaddal.data.models.params.LoginParams
import dev.gaddal.data.models.params.RefreshTokenParams
import dev.gaddal.data.models.params.UserParams
import dev.gaddal.utils.BaseResponse

/**
 * Service interface for managing authentication-related operations.
 * This interface defines methods for registering, logging in, and refreshing user tokens.
 */
interface AuthService {
    /**
     * Registers a new user in the system.
     *
     * @param userParams The parameters for creating the new user.
     * @return A [BaseResponse] indicating success or failure of the registration operation.
     */
    suspend fun registerUser(userParams: UserParams): BaseResponse<Any>

    /**
     * Authenticates a user using their email and password.
     *
     * @param params The UserLoginParams containing the user's email and password.
     * @return A [BaseResponse] with login details if successful, or an error message if authentication fails.
     */
    suspend fun loginUser(params: LoginParams): BaseResponse<Any>

    /**
     * Refreshes an access token using a provided refresh token.
     *
     * @param params The RefreshTokenParams containing the refresh token.
     * @return A [BaseResponse] containing the new access token or an error message.
     */
    suspend fun refreshToken(params: RefreshTokenParams): BaseResponse<Any>

    /**
     * Logs out a user by invalidating their refresh token.
     *
     * @param refreshToken The refresh token to invalidate.
     * @return A [BaseResponse] indicating success or failure of the logout operation.
     */
    suspend fun logoutUser(refreshToken: String): BaseResponse<Any>

//    /**
//     * Changes the user's password and invalidates all existing refresh tokens.
//     *
//     * @param userId The ID of the user changing their password.
//     * @param newPassword The new password to set.
//     * @param currentRefreshToken The current refresh token of the user, which will be invalidated.
//     * @return A [BaseResponse] indicating the success or failure of the password change operation.
//     */
//    suspend fun changePassword(userId: Int, newPassword: String, currentRefreshToken: String): BaseResponse<Any>
}