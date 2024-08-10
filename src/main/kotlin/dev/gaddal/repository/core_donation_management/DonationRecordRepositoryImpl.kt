package dev.gaddal.repository.core_donation_management

import dev.gaddal.data.db.DatabaseFactory
import dev.gaddal.data.db.schemas.core_donation_management.DonationRecordsTable
import dev.gaddal.data.models.common.PaginatedResult
import dev.gaddal.data.models.entities.DonationRecord
import dev.gaddal.data.models.entities.toDonationRecord
import dev.gaddal.utils.OperationError
import dev.gaddal.utils.Result
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll

/**
 * Implementation of [DonationRecordRepository] that interacts with the database to perform donation record-related operations.
 *
 * This class provides methods for retrieving donation records by user ID, as well as pagination and error handling.
 */
class DonationRecordRepositoryImpl : DonationRecordRepository {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    /**
     * Retrieves a list of donation records for a user by their unique identifier.
     *
     * @param userId The unique identifier of the user to retrieve donation records for.
     * @return A [Result] containing the list of [DonationRecord]s if found, or an error if not found or if a database error occurs.
     */
    override suspend fun getDonationRecordsByUserId(userId: Int): Result<List<DonationRecord>> {
        return try {
            val donations = DatabaseFactory.dbQuery {
                DonationRecordsTable.selectAll().where { DonationRecordsTable.donor_id eq userId }
                    .mapNotNull { it.toDonationRecord() }
            }
            Result.Success(donations)
        } catch (e: Exception) {
            logger.error(e) { "Error retrieving donations for user with id $userId" }
            Result.Error(OperationError.Database("Error retrieving donations"))
        }
    }

    /**
     * Retrieves a paginated list of donation records for a user by their unique identifier.
     *
     * @param userId The unique identifier of the user to retrieve donation records for.
     * @param page The page number to retrieve (1-indexed).
     * @param limit The maximum number of donation records per page.
     * @return A [Result] containing a [PaginatedResult] of [DonationRecord]s, or an error if a database error occurs.
     */
    override suspend fun getDonationRecordsByUserId(
        userId: Int,
        page: Int,
        limit: Int
    ): Result<PaginatedResult<DonationRecord>> {
        return try {
            DatabaseFactory.dbQuery {
                val totalCount =
                    DonationRecordsTable.selectAll().where { DonationRecordsTable.donor_id eq userId }.count()
                val totalPages = (totalCount / limit) + if (totalCount % limit > 0) 1 else 0
                val offset = (page - 1) * limit

                val donations = DonationRecordsTable
                    .selectAll().where { DonationRecordsTable.donor_id eq userId }
                    .orderBy(DonationRecordsTable.donation_timestamp, SortOrder.DESC)
                    .limit(limit, offset.toLong())
                    .mapNotNull { it.toDonationRecord() }

                val nextPage = if (page < totalPages) page + 1 else null

                Result.Success(PaginatedResult(totalPages, nextPage?.toLong(), donations))
            }
        } catch (e: Exception) {
            logger.error(e) { "Error retrieving paginated donations for user with id $userId" }
            Result.Error(OperationError.Database("Error retrieving paginated donations"))
        }
    }
}