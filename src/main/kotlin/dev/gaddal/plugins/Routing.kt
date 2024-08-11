package dev.gaddal.plugins

import dev.gaddal.routes.user_donor_management.userDonorManagementRoutes
import io.ktor.server.application.*

/**
 * Configures the routing for the application.
 * This function sets up all the routes for the Ktor server by calling specific route configuration functions.
 */
fun Application.configureRouting() {
    userDonorManagementRoutes()
}
