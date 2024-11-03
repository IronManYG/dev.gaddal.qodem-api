package dev.gaddal.utils

/**
 * Utility class for building standardized messages related to entity operations and authentication.
 *
 * This class generates consistent success and error messages for CRUD operations,
 * authentication processes, and other common scenarios, parameterized by the entity name.
 *
 * @property entity The name of the entity for which messages are being generated.
 */
class MessageBuilder(private val entity: String) {

    /**
     * Generates a success message for entity creation.
     *
     * @return A string indicating successful creation of the entity.
     */
    fun createdSuccess() = "$entity successfully created"

    /**
     * Generates a success message for entity update.
     *
     * @return A string indicating successful update of the entity.
     */
    fun updatedSuccess() = "$entity successfully updated"

    /**
     * Generates a success message for entity deletion.
     *
     * @return A string indicating successful deletion of the entity.
     */
    fun deletedSuccess() = "$entity successfully deleted"

    /**
     * Generates a message for when an entity is not found.
     *
     * @return A string indicating that the entity was not found.
     */
    fun notFound() = "$entity not found"

    /**
     * Generates a message for general processing errors related to the entity.
     *
     * @return A string indicating that an error occurred while processing the entity.
     */
    fun processingError() = "An error occurred while processing the $entity"

    /**
     * Generates a generic success message.
     *
     * @return A string indicating a successful operation.
     */
    fun success() = "Success"

    /**
     * Generates a success message for user registration.
     *
     * @return A string indicating successful user registration.
     */
    fun registrationSuccess() = "User successfully registered"

    /**
     * Generates a success message for user login.
     *
     * @return A string indicating successful user login.
     */
    fun loginSuccess() = "User successfully logged in"

    /**
     * Generates an error message for failed login attempts.
     *
     * @return A string indicating login failure.
     */
    fun loginFailure() = "Invalid email or password"

    /**
     * Generates a message for when a user logs out.
     *
     * @return A string indicating that the user has logged out.
     */
    fun logoutSuccess() = "Logout successful"

    /**
     * Generates a success message for token refresh.
     *
     * @return A string indicating successful token refresh.
     */
    fun tokenRefreshed() = "Token successfully refreshed"

    /**
     * Generates an error message for invalid tokens.
     *
     * @return A string indicating an invalid token.
     */
    fun invalidToken() = "Invalid or expired token"

    /**
     * Generates a message for when an email is already registered.
     *
     * @return A string indicating that the email is already in use.
     */
    fun emailAlreadyRegistered() = "Email is already registered"

    /**
     * Generates a message for when a user is not authorized.
     *
     * @return A string indicating that the user is not authorized.
     */
    fun notAuthorized() = "User is not authorized for this action"

    /**
     * Generates a message for when a password reset is requested.
     *
     * @return A string indicating that a password reset link has been sent.
     */
    fun passwordResetRequested() = "Password reset link sent to email"

    /**
     * Generates a success message for password change.
     *
     * @return A string indicating successful password change.
     */
    fun passwordChangeSuccess() = "Password successfully changed"
}