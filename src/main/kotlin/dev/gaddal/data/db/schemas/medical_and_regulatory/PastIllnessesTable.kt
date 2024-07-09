package dev.gaddal.data.db.schemas.medical_and_regulatory

import dev.gaddal.data.db.schemas.medical_and_regulatory.PastIllnessesTable.createdAt
import dev.gaddal.data.db.schemas.medical_and_regulatory.PastIllnessesTable.id
import dev.gaddal.data.db.schemas.medical_and_regulatory.PastIllnessesTable.medical_history_id
import dev.gaddal.data.db.schemas.medical_and_regulatory.PastIllnessesTable.name
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

/**
 * Represents the past_illnesses table in a PostgresSQL database, documenting past illnesses of a user that could impact their eligibility to donate blood.
 * This table stores information about specific illnesses associated with the user's medical history.
 *
 * @property id Unique identifier for the illness record, automatically incremented.
 * @property medical_history_id Foreign key reference to the associated medical history.
 * @property name Name of the illness.
 * @property createdAt Timestamp of the creation date of the illness record.
 */
object PastIllnessesTable : IntIdTable("past_illnesses") {
    val medical_history_id = integer("medical_history_id").references(MedicalHistoryTable.id)
    val name = varchar("name", 255)
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestamp())
}