package dev.gaddal.controller.user_donor_management

import dev.gaddal.data.models.params.MedicalHistoryParams
import dev.gaddal.data.models.params.UserParams
import dev.gaddal.service.badges_and_achievements.UserBadgeService
import dev.gaddal.service.core_donation_management.DonationRecordService
import dev.gaddal.service.core_donation_management.UserService
import dev.gaddal.service.medical_and_regulatory.MedicalHistoryService
import dev.gaddal.utils.AuthUtils.extractAuthenticatedUserId
import dev.gaddal.utils.RouteUtils
import dev.gaddal.utils.ValidationUtils
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.server.application.*
import io.ktor.server.request.*

/**
 * Controller responsible for handling user and donor management-related HTTP requests.
 *
 * This controller acts as an intermediary between the routing layer and various service layers,
 * handling operations related to users/donors, their medical history, donations, and badges.
 * It encompasses functionality for both general users and specific donor-related actions.
 *
 * @property userService Service for user-related operations.
 * @property medicalHistoryService Service for medical history operations.
 * @property donationRecordService Service for donation record operations.
 * @property userBadgeService Service for user badge operations.
 */
class UserDonorManagementController(
    private val userService: UserService,
    private val medicalHistoryService: MedicalHistoryService,
    private val donationRecordService: DonationRecordService,
    private val userBadgeService: UserBadgeService
) {
    private val logger = KotlinLogging.logger {}

    /**
     * Admin only route.
     * Handles the request to retrieve all users/donors, with optional pagination.
     *
     * @param call The ApplicationCall containing the request details.
     */
    suspend fun getUsers(call: ApplicationCall) {
        logger.info { "Received request to get users/donors" }
        RouteUtils.handleRoute(call, logger) {
            val page = call.request.queryParameters["page"]?.toIntOrNull()
            val limit = call.request.queryParameters["limit"]?.toIntOrNull()

            if (page != null && limit != null) {
                ValidationUtils.validatePaginationParams(page, limit)
                userService.getUsers(page, limit)
            } else {
                userService.getUsers()
            }
        }
    }

    /**
     * Handles the request to retrieve the authenticated user's profile.
     *
     * @param call The ApplicationCall containing the request details with JWT token.
     */
    suspend fun getUserProfile(call: ApplicationCall) {
        logger.info { "Received request to get user/donor by ID" }
        RouteUtils.handleRoute(call, logger) {
            val userId = extractAuthenticatedUserId(call)
            userService.getUserById(userId)
        }
    }

    /**
     * Handles the request to update the authenticated user's profile.
     *
     * @param call The ApplicationCall containing the request details with JWT token.
     */
    suspend fun updateProfile(call: ApplicationCall) {
        logger.info { "Received request to update a user/donor" }
        RouteUtils.handleRoute(call, logger) {
            val userId = extractAuthenticatedUserId(call)
            val userParams = call.receive<UserParams>()
            userService.updateUser(userId, userParams)
        }
    }

    /**
     * Admin only route.
     * Handles the request to delete a user/donor.
     *
     * @param call The ApplicationCall containing the request details.
     */
    suspend fun deleteUser(call: ApplicationCall) {
        logger.info { "Received request to delete a user/donor" }
        RouteUtils.handleRoute(call, logger) {
            val id = ValidationUtils.validateIntParameter(call.parameters["id"], "user/donor ID")
            userService.deleteUser(id)
        }
    }

    /**
     * Handles the request to retrieve the authenticated user's medical history.
     *
     * @param call The ApplicationCall containing the request details with JWT token.
     */
    suspend fun getMedicalHistory(call: ApplicationCall) {
        logger.info { "Received request to get user/donor's medical history" }
        RouteUtils.handleRoute(call, logger) {
            val userId = extractAuthenticatedUserId(call)
            medicalHistoryService.getMedicalHistoryByUserId(userId)
        }
    }

    /**
     * Handles the request to update the authenticated user's medical history.
     *
     * @param call The ApplicationCall containing the request details with JWT token.
     */
    suspend fun updateMedicalHistory(call: ApplicationCall) {
        logger.info { "Received request to update user/donor's medical history" }
        RouteUtils.handleRoute(call, logger) {
            val userId = extractAuthenticatedUserId(call)
            val medicalHistoryParams = call.receive<MedicalHistoryParams>()
            medicalHistoryService.updateMedicalHistory(userId, medicalHistoryParams)
        }
    }

    /**
     * Handles the request to retrieve the authenticated user's donations, with optional pagination.
     *
     * @param call The ApplicationCall containing the request details with JWT token.
     */
    suspend fun getDonations(call: ApplicationCall) {
        logger.info { "Received request to get user/donor's donations" }
        RouteUtils.handleRoute(call, logger) {
            val userId = extractAuthenticatedUserId(call)
            val page = call.request.queryParameters["page"]?.toIntOrNull()
            val limit = call.request.queryParameters["limit"]?.toIntOrNull()

            if (page != null && limit != null) {
                ValidationUtils.validatePaginationParams(page, limit)
                donationRecordService.getDonationRecordsByUserId(userId, page, limit)
            } else {
                donationRecordService.getDonationRecordsByUserId(userId)
            }
        }
    }

    /**
     * Handles the request to retrieve the authenticated user's badges.
     *
     * @param call The ApplicationCall containing the request details with JWT token.
     */
    suspend fun getBadges(call: ApplicationCall) {
        logger.info { "Received request to get user/donor's badges" }
        RouteUtils.handleRoute(call, logger) {
            val userId = extractAuthenticatedUserId(call)
            userBadgeService.getUserBadgesByUserId(userId)
        }
    }
}