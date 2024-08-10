package dev.gaddal.repository.core_donation_management

import dev.gaddal.data.models.common.PaginatedResult
import dev.gaddal.data.models.entities.DonationRecord
import dev.gaddal.utils.Result

/**
 * Repository interface for managing donation record-related operations.
 * This interface defines methods for retrieving donation records by user ID.
 * It also provides methods for retrieving paginated lists of donation records.
 */
interface DonationRecordRepository {
    /**
     * Retrieves a list of donation records for a user by their unique identifier.
     *
     * @param userId The unique identifier of the user to retrieve donation records for.
     * @return A [Result] containing the list of [DonationRecord]s if found, or an error if not found or if a database error occurs.
     */
    suspend fun getDonationRecordsByUserId(userId: Int): Result<List<DonationRecord>>

    /**
     * Retrieves a paginated list of donation records for a user by their unique identifier.
     *
     * @param userId The unique identifier of the user to retrieve donation records for.
     * @param page The page number to retrieve (1-indexed).
     * @param limit The maximum number of donation records per page.
     * @return A [Result] containing a [PaginatedResult] of [DonationRecord]s, or an error if a database error occurs.
     */
    suspend fun getDonationRecordsByUserId(userId: Int, page: Int, limit: Int): Result<PaginatedResult<DonationRecord>>
}