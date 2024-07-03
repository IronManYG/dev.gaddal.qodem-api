package dev.gaddal.data.db.schemas.core_donation_management

import dev.gaddal.data.db.schemas.core_donation_management.ContactDetailTable.contact_type
import dev.gaddal.data.db.schemas.core_donation_management.ContactDetailTable.donation_center_id
import dev.gaddal.data.db.schemas.core_donation_management.ContactDetailTable.id
import dev.gaddal.data.db.schemas.core_donation_management.ContactDetailTable.value
import dev.gaddal.data.db.schemas.core_donation_management.enums.ContactType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

/**
 * Represents the contact_details table in a PostgresSQL database, optimized for managing contact information.
 * This table stores various types of contact information for donation centers, such as websites, emails, and phone numbers.
 *
 * @property id Unique identifier for the contact detail, automatically incremented.
 * @property donation_center_id References the associated donation center, ensuring relational integrity.
 * @property contact_type Type of contact method (e.g., 'website', 'email', 'phone').
 * @property value Actual contact information, corresponding to the contact type.
 */
object ContactDetailTable : Table("contact_details") {
    val id = integer("id").autoIncrement()
    val donation_center_id = integer("donation_center_id").references(DonationCenterTable.id)
    val contact_type = enumerationByName("contact_type", 50, ContactType::class)
    val value = varchar("value", 255)
    val createdAt = datetime("created_at").defaultExpression(CurrentTimestamp())

    override val primaryKey = PrimaryKey(id)
}