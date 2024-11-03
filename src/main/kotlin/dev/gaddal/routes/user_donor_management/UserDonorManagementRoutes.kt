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
 * for retrieving, creating, updating, and deleting users/donors, as well as managing
 * their medical histories, donations, and badges. It uses a [UserDonorManagementController]
 * to handle the actual processing of requests.
 *
 * All routes are prefixed with "/api/v1/users".
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
                 * GET /api/v1/users
                 * Retrieves a list of all users/donors or a paginated list if query parameters are provided
                 */
                get {
                    userDonorManagementController.getUsers(call)
                }

                /**
                 * GET /api/v1/users/{id}
                 * Retrieves a specific user/donor by their ID
                 */

                get("{id}") {
                    userDonorManagementController.getUserById(call)
                }

                /**
                 * PUT /api/v1/users/{id}
                 * Updates an existing user/donor identified by their ID
                 */
                put("{id}") {
                    userDonorManagementController.updateUser(call)
                }

                /**
                 * DELETE /api/v1/users/{id}
                 * Deletes a user/donor identified by their ID
                 */
                delete("{id}") {
                    userDonorManagementController.deleteUser(call)
                }

                /**
                 * GET /api/v1/users/{id}/medical-history
                 * Retrieves the medical history of a user/donor identified by their ID
                 */
                get("{id}/medical-history") {
                    userDonorManagementController.getUserMedicalHistory(call)
                }

                /**
                 * PUT /api/v1/users/{id}/medical-history
                 * Updates the medical history of a user/donor identified by their ID
                 */
                put("{id}/medical-history") {
                    userDonorManagementController.updateUserMedicalHistory(call)
                }

                /**
                 * GET /api/v1/users/{id}/donations
                 * Retrieves the donations of a user/donor identified by their ID
                 */
                get("{id}/donations") {
                    userDonorManagementController.getUserDonations(call)
                }

                /**
                 * GET /api/v1/users/{id}/badges
                 * Retrieves the badges of a user/donor identified by their ID
                 */
                get("{id}/badges") {
                    userDonorManagementController.getUserBadges(call)
                }
            }
        }
    }
}