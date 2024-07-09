package dev.gaddal.data.db.schemas.medical_and_regulatory

import dev.gaddal.data.db.schemas.core_donation_management.UserTable
import dev.gaddal.data.db.schemas.medical_and_regulatory.MedicalHistoryTable.createdAt
import dev.gaddal.data.db.schemas.medical_and_regulatory.MedicalHistoryTable.id
import dev.gaddal.data.db.schemas.medical_and_regulatory.MedicalHistoryTable.other_events
import dev.gaddal.data.db.schemas.medical_and_regulatory.MedicalHistoryTable.user_id
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

/**
 * Represents the medical_history table in a PostgresSQL database, capturing the overall medical history of a donor.
 * This table includes information about any relevant medical events or notes associated with the donor.
 *
 * @property id Unique identifier for the medical history record, automatically incremented.
 * @property user_id Foreign key reference to the associated user, linking the medical history to a specific donor.
 * @property other_events Textual description of other relevant medical events or notes.
 * @property createdAt Timestamp of the creation date of the medical history record.
 */
object MedicalHistoryTable : IntIdTable("medical_history") {
    val user_id = integer("user_id").references(UserTable.id)
    val other_events = text("other_events")
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestamp())
}