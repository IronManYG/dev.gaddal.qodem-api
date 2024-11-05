package dev.gaddal.routes.user_donor_management

import dev.gaddal.controller.user_donor_management.UserDonorManagementController
import dev.gaddal.plugins.authenticate
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

/**
 * Configures the user and donor management-related routes for the application.
 *
 * This function sets up the routing for user and donor operations, defining endpoints
 * for retrieving, updating, and deleting users/donors, as well as managing
 * their medical histories, donations, and badges. It uses a [UserDonorManagementController]
 * to handle the actual processing of requests.
 *
 * All routes are prefixed with "/api/v1/users" and require authentication.
 *
 * @receiver [Application] The Ktor application to configure routes for.
 */
fun Application.userDonorManagementRoutes() {
    // Inject an instance of UserDonorManagementController using Koin
    val userDonorManagementController: UserDonorManagementController by inject()

    routing {
        // Group all user and donor management-related routes under "/api/v1/users"
        authenticate {
            route("/api/v1/users") {
                /**
                 * Admin-only operation
                 * GET /api/v1/users
                 * Retrieves a list of all users/donors or a paginated list if query parameters are provided
                 */
                get {
                    userDonorManagementController.getUsers(call)
                }

                /**
                 * GET /api/v1/users/profile
                 * Retrieves authenticated user's profile
                 */
                get("/profile") {
                    userDonorManagementController.getUserProfile(call)
                }

                /**
                 * PUT /api/v1/users/profile
                 * Updates authenticated user's profile
                 */
                put("/profile") {
                    userDonorManagementController.updateProfile(call)
                }

                /**
                 * Admin-only operation
                 * DELETE /api/v1/users/{id}
                 * Deletes a user/donor identified by their ID
                 */
                delete("{id}") {
                    userDonorManagementController.deleteUser(call)
                }

                /**
                 * GET /api/v1/users/medical-history
                 * Retrieves authenticated user's medical history
                 */
                get("/medical-history") {
                    userDonorManagementController.getMedicalHistory(call)
                }

                /**
                 * PUT /api/v1/users/medical-history
                 * Updates authenticated user's medical history
                 */
                put("/medical-history") {
                    userDonorManagementController.updateMedicalHistory(call)
                }

                /**
                 * GET /api/v1/users/donations
                 * Retrieves authenticated user's donations
                 */
                get("/donations") {
                    userDonorManagementController.getDonations(call)
                }

                /**
                 * GET /api/v1/users/badges
                 * Retrieves authenticated user's badges
                 */
                get("/badges") {
                    userDonorManagementController.getBadges(call)
                }
            }
        }
    }
}