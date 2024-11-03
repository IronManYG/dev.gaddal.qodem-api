package dev.gaddal.data.models.params

/**
 * Data class that encapsulates the login parameters required for a user to authenticate.
 * This class is typically used to gather login credentials from user input on a UI form or an API request.
 *
 * @property email The email address of the user attempting to log in. This should be a valid email format.
 * @property password The password associated with the user's account. This is taken as plain text here,
 * but should be securely handled and never stored or logged.
 */
data class LoginParams(
    val email: String,
    val password: String
)
