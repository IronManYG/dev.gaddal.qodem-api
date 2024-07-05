package dev.gaddal.data.db.schemas.appointments_and_scheduling

import dev.gaddal.data.db.schemas.appointments_and_scheduling.AvailableTimeslotsTable.createdAt
import dev.gaddal.data.db.schemas.appointments_and_scheduling.AvailableTimeslotsTable.donation_center_id
import dev.gaddal.data.db.schemas.appointments_and_scheduling.AvailableTimeslotsTable.end_datetime
import dev.gaddal.data.db.schemas.appointments_and_scheduling.AvailableTimeslotsTable.id
import dev.gaddal.data.db.schemas.appointments_and_scheduling.AvailableTimeslotsTable.start_datetime
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

/**
 * Represents the available_timeslots table in a PostgresSQL database, managing open appointment slots at each donation center.
 * This table tracks the start and end times for each available slot, facilitating the scheduling of appointments.
 *
 * @property id Unique identifier for the timeslot, automatically incremented.
 * @property donation_center_id Foreign key reference to the associated donation center.
 * @property start_datetime Start date and time of the available slot, stored as a UNIX timestamp.
 * @property end_datetime End date and time of the available slot, stored as a UNIX timestamp.
 * @property createdAt Timestamp of the creation date of the timeslot record.
 */
object AvailableTimeslotsTable : Table("available_timeslots") {
    val id = integer("id").autoIncrement()
    val donation_center_id = integer("donation_center_id").references(DonationCenterTable.id)
    val start_datetime = timestampWithTimeZone("start_datetime")
    val end_datetime = timestampWithTimeZone("end_datetime")
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestamp())

    override val primaryKey = PrimaryKey(id)
}
