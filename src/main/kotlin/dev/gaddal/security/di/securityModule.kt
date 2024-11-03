package dev.gaddal.security.di

import dev.gaddal.utils.JwtConfig
import dev.gaddal.utils.JwtConfigParams
import io.ktor.server.config.*
import org.koin.dsl.module
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours

/**
 * Koin module for security-related dependencies.
 * This module provides the necessary components for JWT configuration.
 */
val securityModule = module {
    single {
        // Retrieve the ApplicationConfig from Koin
        val config: ApplicationConfig = get()

        JwtConfigParams(
            secret = config.property("jwt.secret").getString(),
            issuer = config.property("jwt.issuer").getString(),
            audience = config.property("jwt.audience").getString(),
            realm = config.property("jwt.realm").getString(),
            accessTokenDuration = config.property("jwt.accessTokenDuration").getString().toLong().hours,
            refreshTokenDuration = config.property("jwt.refreshTokenDuration").getString().toLong().days
        )
    }

    single { JwtConfig(get(), get()) }
}