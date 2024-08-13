package dev.gaddal.repository.medical_and_regulatory

import dev.gaddal.data.models.entities.MedicalHistory
import dev.gaddal.data.models.params.MedicalHistoryParams
import dev.gaddal.utils.Result

/**
 * Repository interface for managing medical history-related operations.
 * This interface defines methods for retrieving and updating medical history data.
 */
interface MedicalHistoryRepository {
    /**
     * Retrieves the medical history of a user by their unique identifier.
     *
     * @param userId The unique identifier of the user to retrieve medical history for.
     * @return A [Result] containing the [MedicalHistory] if found, or an error if not found or if a database error occurs.
     */
    suspend fun getMedicalHistoryByUserId(userId: Int): Result<MedicalHistory>

    /**
     * Updates the medical history of a user by their unique identifier.
     *
     * @param userId The unique identifier of the user to update medical history for.
     * @param medicalHistoryParams The new parameters for the medical history.
     * @return A [Result] containing the updated [MedicalHistory], or an error if update fails or user is not found.
     */
    suspend fun updateMedicalHistory(userId: Int, medicalHistoryParams: MedicalHistoryParams): Result<MedicalHistory>
}