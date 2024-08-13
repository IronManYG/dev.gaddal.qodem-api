package dev.gaddal.data.models.entities

import com.fasterxml.jackson.annotation.JsonInclude
import dev.gaddal.data.db.schemas.medical_and_regulatory.MedicalHistoryTable
import org.jetbrains.exposed.sql.ResultRow

/**
 * Represents a MedicalHistory in the application.
 *
 * This data class is used to model the data from the `MedicalHistoryTable` in the database.
 * Each property in the class corresponds to a column in the `MedicalHistoryTable`.
 *
 * @see dev.gaddal.data.db.schemas.medical_and_regulatory.MedicalHistoryTable
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class MedicalHistory(
    val id: Int,
    val userId: Int,
    val otherEvents: String,
    val createdAt: String
)

fun ResultRow?.toMedicalHistory(): MedicalHistory? {
    return if (this == null) null
    else MedicalHistory(
        id = this[MedicalHistoryTable.id].value,
        userId = this[MedicalHistoryTable.user_id],
        otherEvents = this[MedicalHistoryTable.other_events],
        createdAt = this[MedicalHistoryTable.createdAt].toString()
    )
}
