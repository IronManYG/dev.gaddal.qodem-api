package dev.gaddal.data.db.schemas.core_donation_management

import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTypeTable.appointment_required
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTypeTable.createdAt
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTypeTable.id
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTypeTable.location_type
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTypeTable.short_description_ar
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTypeTable.short_description_en
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTypeTable.type_name
import dev.gaddal.data.db.schemas.core_donation_management.enums.LocationType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

/**
 * Represents the donation_center_types table in a PostgresSQL database, optimized for performance.
 * This table categorizes donation centers by type, such as "Blood Bank" or "Hospital Collection Center,"
 * and specifies whether the location is mobile or fixed, and if appointments are required.
 *
 * @property id Unique identifier for the donation center type, automatically incremented.
 * @property type_name Descriptive name of the donation center type.
 * @property location_type Specifies if the center is 'mobile' or 'fixed'.
 * @property appointment_required Indicates if appointments are primarily required, default is true.
 * @property short_description_en Brief description in English.
 * @property short_description_ar Brief description in Arabic.
 * @property createdAt Timestamp of the creation date of the donation center type record.
 */
object DonationCenterTypeTable : Table("donation_center_types") {
    val id = integer("id").autoIncrement()
    val type_name = varchar("type_name", 255)
    val location_type = enumerationByName("location_type", 50, LocationType::class)
    val appointment_required = bool("appointment_required").clientDefault { true }
    val short_description_en = varchar("short_description_en", 1000)
    val short_description_ar = varchar("short_description_ar", 1000)
    val createdAt = datetime("created_at").defaultExpression(CurrentTimestamp())

    override val primaryKey = PrimaryKey(id)
}