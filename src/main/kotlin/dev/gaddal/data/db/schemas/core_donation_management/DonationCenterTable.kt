package dev.gaddal.data.db.schemas.core_donation_management

import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTable.address_line2
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTable.city
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTable.country
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTable.createdAt
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTable.donation_center_type_id
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTable.id
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTable.lat
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTable.long
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTable.state
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTable.street_address
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTable.zip_code
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.datetime


/**
 * Represents the donation_centers table in a PostgreSQL database, optimized for performance.
 * This table holds the central information about each donation center including name, location, and contact details.
 *
 * @property id Unique identifier for the donation center, automatically incremented, optimized for PostgreSQL.
 * @property donation_center_type_id References the type of donation center, ensuring relational integrity with a foreign key.
 * @property city City where the donation center is located. Essential for regional sorting and queries.
 * @property state State where the donation center is located. Used in regional filtering.
 * @property country Country where the donation center is located. Important for international queries.
 * @property lat Latitude coordinate for geospatial queries.
 * @property long Longitude coordinate for geospatial queries.
 * @property street_address Primary street address for the donation center.
 * @property address_line2 Optional additional address line for detailed location description.
 * @property zip_code Postal code for the donation center, crucial for local searches and logistics.
 * @property createdAt Timestamp of the creation date of the donation center record.
 */
object DonationCenterTable : Table("donation_centers") {
    val id = integer("id").autoIncrement()
    val donation_center_type_id = integer("donation_center_type_id").references(DonationCenterTypeTable.id)
    val city = varchar("city", 255)
    val state = varchar("state", 255)
    val country = varchar("country", 255)
    val lat = float("lat")
    val long = float("long")
    val street_address = varchar("street_address", 255)
    val address_line2 = varchar("address_line2", 255).nullable()
    val zip_code = varchar("zip_code", 20)
    val createdAt = datetime("created_at").defaultExpression(CurrentTimestamp())

    override val primaryKey = PrimaryKey(id)
}
