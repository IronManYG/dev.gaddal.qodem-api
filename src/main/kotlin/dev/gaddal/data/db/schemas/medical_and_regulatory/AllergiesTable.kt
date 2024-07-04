package dev.gaddal.data.db.schemas.medical_and_regulatory

import dev.gaddal.data.db.schemas.medical_and_regulatory.AllergiesTable.createdAt
import dev.gaddal.data.db.schemas.medical_and_regulatory.AllergiesTable.id
import dev.gaddal.data.db.schemas.medical_and_regulatory.AllergiesTable.medical_history_id
import dev.gaddal.data.db.schemas.medical_and_regulatory.AllergiesTable.name
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

/**
 * Represents the allergies table in a PostgresSQL database, documenting specific allergies the user has.
 * This information is crucial for ensuring safe blood donations and transfusions, especially regarding allergens that could affect medical treatments.
 *
 * @property id Unique identifier for the allergen record, automatically incremented.
 * @property medical_history_id Foreign key reference to the associated medical history.
 * @property name Name of the allergen.
 * @property createdAt Timestamp of the creation date of the allergen record.
 */
object AllergiesTable : Table("allergies") {
    val id = integer("id").autoIncrement()
    val medical_history_id = integer("medical_history_id").references(MedicalHistoryTable.id)
    val name = varchar("name", 255)
    val createdAt = datetime("created_at").defaultExpression(CurrentTimestamp())

    override val primaryKey = PrimaryKey(id)
}
