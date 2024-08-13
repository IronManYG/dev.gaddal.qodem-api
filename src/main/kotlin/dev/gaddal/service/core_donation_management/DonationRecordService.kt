package dev.gaddal.service.core_donation_management

import dev.gaddal.utils.BaseResponse

/**
 * Service interface for managing donation record-related operations.
 * This interface defines methods for retrieving donation records for a specific user.
 */
interface DonationRecordService {
    /**
     * Retrieves all donation records for a specific user.
     *
     * @param userId The ID of the user whose donation records are to be retrieved.
     * @return A [BaseResponse] containing the donation records data or an error message.
     */
    suspend fun getDonationRecordsByUserId(userId: Int): BaseResponse<Any>

    /**
     * Retrieves a paginated list of donation records for a specific user.
     *
     * @param userId The ID of the user whose donation records are to be retrieved.
     * @param page The page number to retrieve.
     * @param limit The maximum number of records per page.
     * @return A [BaseResponse] containing a paginated list of donation records or an error message.
     */
    suspend fun getDonationRecordsByUserId(userId: Int, page: Int, limit: Int): BaseResponse<Any>
}