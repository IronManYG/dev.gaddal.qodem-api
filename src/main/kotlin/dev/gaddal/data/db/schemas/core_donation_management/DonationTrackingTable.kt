package dev.gaddal.data.db.schemas.core_donation_management

import dev.gaddal.data.db.schemas.core_donation_management.DonationTrackingTable.blood_unit_id
import dev.gaddal.data.db.schemas.core_donation_management.DonationTrackingTable.collection_datetime
import dev.gaddal.data.db.schemas.core_donation_management.DonationTrackingTable.createdAt
import dev.gaddal.data.db.schemas.core_donation_management.DonationTrackingTable.donation_record_id
import dev.gaddal.data.db.schemas.core_donation_management.DonationTrackingTable.id
import dev.gaddal.data.db.schemas.core_donation_management.DonationTrackingTable.received_datetime
import dev.gaddal.data.db.schemas.core_donation_management.DonationTrackingTable.status
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

/**
 * Represents the donation_tracking table in a PostgreSQL database, tracking the journey of a blood unit from collection to transfusion.
 * This table holds detailed information about each stage of the process, including collection, testing, and final transfusion status.
 *
 * @property id Unique identifier for the donation tracking record, automatically incremented.
 * @property donation_record_id Foreign key reference to the associated donation record.
 * @property blood_unit_id Unique identifier for tracking the blood unit throughout its journey.
 * @property collection_datetime Timestamp of when the blood was collected.
 * @property received_datetime Timestamp of when the blood unit was received by the hospital.
 * @property status Current status of the donation process (e.g., 'collected', 'processed', 'ready for dispatch').
 * @property createdAt Timestamp of the creation date of the donation tracking record.
 */
object DonationTrackingTable : IntIdTable("donation_tracking") {
    val donation_record_id = integer("donation_record_id").references(DonationRecordsTable.id)
    val blood_unit_id = varchar("blood_unit_id", 120)
    val collection_datetime = timestampWithTimeZone("collection_datetime")
    val received_datetime = timestampWithTimeZone("received_datetime")
    val status = varchar("status", 255)
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestamp())
}