package dev.gaddal.data.db.schemas.core_donation_management

import dev.gaddal.data.db.schemas.core_donation_management.UserNameTable.createdAt
import dev.gaddal.data.db.schemas.core_donation_management.UserNameTable.first_name
import dev.gaddal.data.db.schemas.core_donation_management.UserNameTable.id
import dev.gaddal.data.db.schemas.core_donation_management.UserNameTable.last_name
import dev.gaddal.data.db.schemas.core_donation_management.UserNameTable.middle_name
import dev.gaddal.data.db.schemas.core_donation_management.UserNameTable.user_id
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

/**
 * Represents the users_name table in a PostgresSQL database, capturing the full name of donors.
 * This table includes first, middle (if applicable), and last names.
 *
 * @property id Unique identifier for the name entry, automatically incremented.
 * @property user_id Foreign key reference to the user table, associating the name with a specific user.
 * @property first_name User's first name.
 * @property middle_name Optional user's middle name.
 * @property last_name User's last name.
 * @property createdAt Timestamp of the creation date of the username record.
 */
object UserNameTable : IntIdTable("users_name") {
    val user_id = integer("user_id").references(UserTable.id)
    val first_name = varchar("first_name", 255)
    val middle_name = varchar("middle_name", 255).nullable()
    val last_name = varchar("last_name", 255)
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestamp())
}
