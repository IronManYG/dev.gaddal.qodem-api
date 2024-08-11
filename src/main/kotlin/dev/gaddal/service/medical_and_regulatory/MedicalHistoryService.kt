package dev.gaddal.service.medical_and_regulatory

import dev.gaddal.data.models.params.MedicalHistoryParams
import dev.gaddal.utils.BaseResponse

/**
 * Service interface for managing medical history-related operations.
 * This interface defines methods for retrieving and updating medical history data.
 */
interface MedicalHistoryService {
    /**
     * Retrieves the medical history of a user by their unique identifier.
     *
     * @param userId The unique identifier of the user to retrieve medical history for.
     * @return A [BaseResponse] containing the medical history data if found, or an error message if not found.
     */
    suspend fun getMedicalHistoryByUserId(userId: Int): BaseResponse<Any>

    /**
     * Updates the medical history of a user.
     *
     * @param userId The unique identifier of the user to update medical history for.
     * @param medicalHistoryParams The new medical history parameters for the user.
     * @return A [BaseResponse] indicating the success or failure of the update operation.
     */
    suspend fun updateMedicalHistory(userId: Int, medicalHistoryParams: MedicalHistoryParams): BaseResponse<Any>
}