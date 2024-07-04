package dev.gaddal.data.db.schemas.medical_and_regulatory

import dev.gaddal.data.db.schemas.medical_and_regulatory.SurgeriesTable.createdAt
import dev.gaddal.data.db.schemas.medical_and_regulatory.SurgeriesTable.id
import dev.gaddal.data.db.schemas.medical_and_regulatory.SurgeriesTable.medical_history_id
import dev.gaddal.data.db.schemas.medical_and_regulatory.SurgeriesTable.name
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

/**
 * Represents the surgeries table in a PostgresSQL database, documenting surgical procedures the user has undergone.
 * This table is crucial for assessing eligibility for blood donation, as certain surgeries may temporarily or permanently affect this eligibility.
 *
 * @property id Unique identifier for the surgery record, automatically incremented.
 * @property medical_history_id Foreign key reference to the associated medical history.
 * @property name Name or description of the surgery.
 * @property createdAt Timestamp of the creation date of the surgery record.
 */
object SurgeriesTable : Table("surgeries") {
    val id = integer("id").autoIncrement()
    val medical_history_id = integer("medical_history_id").references(MedicalHistoryTable.id)
    val name = varchar("name", 255)
    val createdAt = datetime("created_at").defaultExpression(CurrentTimestamp())

    override val primaryKey = PrimaryKey(id)
}
