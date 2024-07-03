package dev.gaddal.data.db.schemas.core_donation_management

import dev.gaddal.data.db.schemas.core_donation_management.IdentificationTable.createdAt
import dev.gaddal.data.db.schemas.core_donation_management.IdentificationTable.id
import dev.gaddal.data.db.schemas.core_donation_management.IdentificationTable.id_number
import dev.gaddal.data.db.schemas.core_donation_management.IdentificationTable.id_type
import dev.gaddal.data.db.schemas.core_donation_management.IdentificationTable.user_id
import dev.gaddal.data.db.schemas.core_donation_management.enums.IDType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

/**
 * Represents the identification table in a PostgresSQL database, storing identification types and numbers for users.
 * This table facilitates the verification process by storing essential identity documents like driver's licenses or passports.
 *
 * @property id Unique identifier for the identification entry, automatically incremented.
 * @property user_id Foreign key reference to the user table, linking the identification to a specific user.
 * @property id_type Type of identification document (e.g., "Driver's License", "Passport").
 * @property id_number The unique number associated with the identification document.
 * @property createdAt Timestamp of the creation date of the identification record.
 */
object IdentificationTable : Table("identification") {
    val id = integer("id").autoIncrement()
    val user_id = integer("user_id").references(UserTable.id)
    val id_type = enumerationByName("id_type", 50, IDType::class)
    val id_number = varchar("id_number", 100)
    val createdAt = datetime("created_at").defaultExpression(CurrentTimestamp())

    override val primaryKey = PrimaryKey(id)
}
