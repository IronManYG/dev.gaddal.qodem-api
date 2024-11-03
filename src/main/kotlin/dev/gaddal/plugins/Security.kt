package dev.gaddal.plugins

import dev.gaddal.utils.JwtConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

/**
 * Configures security features for the application, primarily JWT authentication.
 */
fun Application.configureSecurity() {
    val jwtConfig: JwtConfig by inject()

    authentication {
        jwt("auth-jwt") {
            realm = jwtConfig.realm
            verifier(jwtConfig.verifier)
            validate { credential ->
                jwtConfig.customValidator(credential)
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}

/**
 * Applies JWT authentication to a specific route.
 */
fun Route.authenticate(build: Route.() -> Unit) = authenticate("auth-jwt", build = build)