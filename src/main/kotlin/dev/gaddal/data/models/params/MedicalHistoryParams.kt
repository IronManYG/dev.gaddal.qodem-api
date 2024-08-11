package dev.gaddal.data.models.params

/**
 * Data class that encapsulates all the necessary parameters for managing medical history in the system.
 * This class is used primarily for passing medical history data between the client and the server
 * during update operations.
 *
 * @see dev.gaddal.data.db.schemas.medical_and_regulatory.MedicalHistoryTable
 */
data class MedicalHistoryParams(
    val userId: Int,
    val otherEvents: String,
)
