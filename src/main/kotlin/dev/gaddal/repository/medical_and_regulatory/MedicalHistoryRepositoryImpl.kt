package dev.gaddal.repository.medical_and_regulatory

import dev.gaddal.data.db.DatabaseFactory
import dev.gaddal.data.db.schemas.medical_and_regulatory.MedicalHistoryTable
import dev.gaddal.data.models.entities.MedicalHistory
import dev.gaddal.data.models.entities.toMedicalHistory
import dev.gaddal.data.models.params.MedicalHistoryParams
import dev.gaddal.utils.OperationError
import dev.gaddal.utils.Result
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

/**
 * Implementation of [MedicalHistoryRepository] that interacts with the database to perform medical history-related operations.
 *
 * This class provides methods for retrieving and updating medical history data, as well as error handling.
 * It uses [DatabaseFactory] to manage database connections and transactions.
 */
class MedicalHistoryRepositoryImpl : MedicalHistoryRepository {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override suspend fun getMedicalHistoryByUserId(userId: Int): Result<MedicalHistory> {
        return try {
            val medicalHistory = DatabaseFactory.dbQuery {
                MedicalHistoryTable.selectAll().where { MedicalHistoryTable.user_id eq userId }
                    .firstOrNull()
                    ?.toMedicalHistory()
            }
            if (medicalHistory != null) {
                Result.Success(medicalHistory)
            } else {
                Result.Error(OperationError.NotFound("Medical history for user with id $userId not found"))
            }
        } catch (e: Exception) {
            logger.error(e) { "Error retrieving medical history for user with id $userId" }
            Result.Error(OperationError.Database("Error retrieving medical history"))
        }
    }

    override suspend fun updateMedicalHistory(
        userId: Int,
        medicalHistoryParams: MedicalHistoryParams
    ): Result<MedicalHistory> {
        return try {
            val updated = DatabaseFactory.dbQuery {
                MedicalHistoryTable.update({ MedicalHistoryTable.user_id eq userId }) {
                    it[user_id] = medicalHistoryParams.userId
                    it[other_events] = medicalHistoryParams.otherEvents
                } > 0
            }
            if (updated) {
                getMedicalHistoryByUserId(userId)
            } else {
                Result.Error(OperationError.NotFound("Medical history for user with id $userId not found"))
            }
        } catch (e: Exception) {
            logger.error(e) { "Error updating medical history for user with id $userId" }
            Result.Error(OperationError.Database("Error updating medical history"))
        }
    }
}