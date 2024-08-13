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
     * Handles the request to retrieve users/donors, with optional pagination.
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
     * Handles the request to retrieve a user/donor by their ID.
     *
     * @param call The ApplicationCall containing the request details.
     */
    suspend fun getUserById(call: ApplicationCall) {
        logger.info { "Received request to get user/donor by ID" }
        RouteUtils.handleRoute(call, logger) {
            val id = ValidationUtils.validateIntParameter(call.parameters["id"], "user/donor ID")
            userService.getUserById(id)
        }
    }

    /**
     * Handles the request to add a new user/donor.
     *
     * @param call The ApplicationCall containing the request details.
     */
    suspend fun addUser(call: ApplicationCall) {
        logger.info { "Received request to add a new user/donor" }
        RouteUtils.handleRoute(call, logger) {
            val userParams = call.receive<UserParams>()
            userService.addUser(userParams)
        }
    }

    /**
     * Handles the request to update an existing user/donor.
     *
     * @param call The ApplicationCall containing the request details.
     */
    suspend fun updateUser(call: ApplicationCall) {
        logger.info { "Received request to update a user/donor" }
        RouteUtils.handleRoute(call, logger) {
            val id = ValidationUtils.validateIntParameter(call.parameters["id"], "user/donor ID")
            val userParams = call.receive<UserParams>()
            userService.updateUser(id, userParams)
        }
    }

    /**
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
     * Handles the request to retrieve a user/donor's medical history.
     *
     * @param call The ApplicationCall containing the request details.
     */
    suspend fun getUserMedicalHistory(call: ApplicationCall) {
        logger.info { "Received request to get user/donor's medical history" }
        RouteUtils.handleRoute(call, logger) {
            val userId = ValidationUtils.validateIntParameter(call.parameters["id"], "user/donor ID")
            medicalHistoryService.getMedicalHistoryByUserId(userId)
        }
    }

    /**
     * Handles the request to update a user/donor's medical history.
     *
     * @param call The ApplicationCall containing the request details.
     */
    suspend fun updateUserMedicalHistory(call: ApplicationCall) {
        logger.info { "Received request to update user/donor's medical history" }
        RouteUtils.handleRoute(call, logger) {
            val userId = ValidationUtils.validateIntParameter(call.parameters["id"], "user/donor ID")
            val medicalHistoryParams = call.receive<MedicalHistoryParams>()
            medicalHistoryService.updateMedicalHistory(userId, medicalHistoryParams)
        }
    }

    /**
     * Handles the request to retrieve a user/donor's donations, with optional pagination.
     *
     * @param call The ApplicationCall containing the request details.
     */
    suspend fun getUserDonations(call: ApplicationCall) {
        logger.info { "Received request to get user/donor's donations" }
        RouteUtils.handleRoute(call, logger) {
            val userId = ValidationUtils.validateIntParameter(extractAuthenticatedUserId(call).toString(), "user/donor ID")
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
     * Handles the request to retrieve a user/donor's badges.
     *
     * @param call The ApplicationCall containing the request details.
     */
    suspend fun getUserBadges(call: ApplicationCall) {
        logger.info { "Received request to get user/donor's badges" }
        RouteUtils.handleRoute(call, logger) {
            val userId = ValidationUtils.validateIntParameter(call.parameters["id"], "user/donor ID")
            userBadgeService.getUserBadgesByUserId(userId)
        }
    }
}