package dev.gaddal.data.db.schemas.appointments_and_scheduling

import dev.gaddal.data.db.schemas.appointments_and_scheduling.WaitlistTable.added_to_waitlist_at
import dev.gaddal.data.db.schemas.appointments_and_scheduling.WaitlistTable.createdAt
import dev.gaddal.data.db.schemas.appointments_and_scheduling.WaitlistTable.donation_center_id
import dev.gaddal.data.db.schemas.appointments_and_scheduling.WaitlistTable.donor_id
import dev.gaddal.data.db.schemas.appointments_and_scheduling.WaitlistTable.id
import dev.gaddal.data.db.schemas.appointments_and_scheduling.WaitlistTable.preferred_datetime
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTable
import dev.gaddal.data.db.schemas.core_donation_management.UserTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

/**
 * Represents the waitlist table in a PostgresSQL database, enabling donors to join a waitlist if their preferred appointment times are unavailable.
 * This table records the donor's preferred time and when they were added to the waitlist, helping manage demand at donation centers.
 *
 * @property id Unique identifier for the waitlist entry, automatically incremented.
 * @property donor_id Foreign key reference to the associated donor.
 * @property donation_center_id Foreign key reference to the associated donation center.
 * @property preferred_datetime Optional preferred date and time range for an appointment.
 * @property added_to_waitlist_at Timestamp for when the donor was added to the waitlist.
 * @property createdAt Timestamp of the creation date of the waitlist entry.
 */
object WaitlistTable : Table("waitlist") {
    val id = integer("id").autoIncrement()
    val donor_id = integer("donor_id").references(UserTable.id)
    val donation_center_id = integer("donation_center_id").references(DonationCenterTable.id)
    val preferred_datetime = timestampWithTimeZone("preferred_datetime").nullable()
    val added_to_waitlist_at = timestampWithTimeZone("added_to_waitlist_at")
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestamp())

    override val primaryKey = PrimaryKey(id)
}
