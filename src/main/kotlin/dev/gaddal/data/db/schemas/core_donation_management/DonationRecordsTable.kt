package dev.gaddal.data.db.schemas.core_donation_management

import dev.gaddal.data.db.schemas.core_donation_management.DonationRecordsTable.amount
import dev.gaddal.data.db.schemas.core_donation_management.DonationRecordsTable.blood_type
import dev.gaddal.data.db.schemas.core_donation_management.DonationRecordsTable.createdAt
import dev.gaddal.data.db.schemas.core_donation_management.DonationRecordsTable.donation_center_id
import dev.gaddal.data.db.schemas.core_donation_management.DonationRecordsTable.donation_purpose
import dev.gaddal.data.db.schemas.core_donation_management.DonationRecordsTable.donation_timestamp
import dev.gaddal.data.db.schemas.core_donation_management.DonationRecordsTable.donation_type
import dev.gaddal.data.db.schemas.core_donation_management.DonationRecordsTable.donor_id
import dev.gaddal.data.db.schemas.core_donation_management.DonationRecordsTable.id
import dev.gaddal.data.db.schemas.core_donation_management.DonationRecordsTable.is_active
import dev.gaddal.data.db.schemas.core_donation_management.DonationRecordsTable.is_authenticated
import dev.gaddal.data.db.schemas.core_donation_management.DonationRecordsTable.volume
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

/**
 * Represents the donation_records table in a PostgresSQL database, capturing details about each donation instance.
 * This table includes information linking to the donor, donation center, type of donation, and the specifics like amount and purpose.
 *
 * @property id Unique identifier of the donation record, automatically incremented.
 * @property donor_id Foreign key reference to the associated donor.
 * @property donation_center_id Foreign key reference to the associated donation center.
 * @property blood_type Blood type of the donor.
 * @property donation_type Type of donation (e.g., whole blood, plasma, platelets).
 * @property donation_purpose Purpose of the donation (e.g., directed, voluntary).
 * @property amount The amount of blood donated.
 * @property volume The volume of blood donated in milliliters.
 * @property donation_timestamp Timestamp of the donation event.
 * @property is_active Indicates whether the donation record is currently active.
 * @property is_authenticated Indicates whether the donation record has been authenticated.
 * @property createdAt Timestamp of the creation date of the donation record.
 */
object DonationRecordsTable : Table("donation_records") {
    val id = integer("id").autoIncrement()
    val donor_id = integer("donor_id").references(UserTable.id)
    val donation_center_id = integer("donation_center_id").references(DonationCenterTable.id)
    val blood_type = varchar("blood_type", 50)
    val donation_type = varchar("donation_type", 100)
    val donation_purpose = varchar("donation_purpose", 100)
    val amount = float("amount")
    val volume = float("volume")
    val donation_timestamp = timestamp("donation_timestamp")
    val is_active = bool("is_active").clientDefault { true }
    val is_authenticated = bool("is_authenticated").clientDefault { false }
    val createdAt = datetime("created_at").defaultExpression(CurrentTimestamp())

    override val primaryKey = PrimaryKey(id)
}
