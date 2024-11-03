package dev.gaddal.utils

import kotlin.time.Duration

/**
 * Data class holding configuration parameters for JWT operations.
 *
 * @property secret The secret key used for signing JWTs.
 * @property issuer The issuer claim for JWTs.
 * @property audience The audience claim for JWTs.
 * @property realm The realm used for authentication.
 * @property accessTokenDuration The duration for which access tokens are valid.
 * @property refreshTokenDuration The duration for which refresh tokens are valid.
 */
data class JwtConfigParams(
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String,
    val accessTokenDuration: Duration,
    val refreshTokenDuration: Duration
)