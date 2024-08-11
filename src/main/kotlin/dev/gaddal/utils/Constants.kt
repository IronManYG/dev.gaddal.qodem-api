package dev.gaddal.utils

/**
 * Constants for commonly used response messages in the authentication and document management processes.
 */

/** Message indicating the email used in registration is already in use. */
const val MESSAGE_EMAIL_ALREADY_REGISTERED = "Given email is already registered"

/** Message indicating successful user registration. */
const val USER_REGISTRATION_SUCCESS = "dev.gaddal.data.models.User successfully registered"

/** Message indicating successful user login. */
const val USER_LOGIN_SUCCESS = "dev.gaddal.data.models.User successfully logged in"

/** Message indicating a login attempt with incorrect credentials. */
const val USER_LOGIN_FAILURE = "Invalid email or password"

/** Generic error message for unspecified errors that occur during processing. */
const val GENERIC_ERROR = "Some error occurred! Please try again later"

/** Message indicating an invalid or expired authentication token. */
const val INVALID_AUTHENTICATION_TOKEN = "Invalid authentication token, please login again"

/** Generic success status message used in various response scenarios. */
const val SUCCESS = "Success"

/** Message indicating the access token has been successfully refreshed. */
const val ACCESS_TOKEN_REFRESHED = "Access token refreshed successfully"

/** Message indicating the refresh token is invalid or expired. */
const val REFRESH_TOKEN_INVALID = "Invalid refresh token, please login again"