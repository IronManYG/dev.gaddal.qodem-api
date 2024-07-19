package dev.gaddal.data.db.schemas.appointments_and_scheduling

import dev.gaddal.data.db.schemas.appointments_and_scheduling.AppointmentsTable.appointment_datetime
import dev.gaddal.data.db.schemas.appointments_and_scheduling.AppointmentsTable.createdAt
import dev.gaddal.data.db.schemas.appointments_and_scheduling.AppointmentsTable.donation_center_id
import dev.gaddal.data.db.schemas.appointments_and_scheduling.AppointmentsTable.donor_id
import dev.gaddal.data.db.schemas.appointments_and_scheduling.AppointmentsTable.id
import dev.gaddal.data.db.schemas.appointments_and_scheduling.AppointmentsTable.reminder_sent
import dev.gaddal.data.db.schemas.appointments_and_scheduling.AppointmentsTable.status
import dev.gaddal.data.db.schemas.appointments_and_scheduling.enums.AppointmentStatus
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTable
import dev.gaddal.data.db.schemas.core_donation_management.UserTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

/**
 * Represents the appointments table in a PostgresSQL database, storing scheduled donation appointments.
 * This table links a donor with a specific donation center and includes details like date, time, and status of the appointment.
 *
 * @property id Unique identifier for the appointment, automatically incremented.
 * @property donor_id Foreign key reference to the associated donor.
 * @property donation_center_id Foreign key reference to the associated donation center.
 * @property appointment_datetime Optional date and time of the appointment.
 * @property status Status of the appointment, defaulting to 'scheduled'.
 * @property reminder_sent Indicates if a reminder has been sent for the appointment, with false for not sent and true for sent.
 * @property createdAt Timestamp of the creation date of the appointment record.
 */
object AppointmentsTable : IntIdTable("appointments") {
    val donor_id = integer("donor_id").references(UserTable.id)
    val donation_center_id = integer("donation_center_id").references(DonationCenterTable.id)
    val appointment_datetime = timestampWithTimeZone("appointment_datetime").nullable()
    val status = enumerationByName("status", 50, AppointmentStatus::class).default(AppointmentStatus.SCHEDULED)
    val reminder_sent = bool("reminder_sent").default(false)
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestamp())
}
