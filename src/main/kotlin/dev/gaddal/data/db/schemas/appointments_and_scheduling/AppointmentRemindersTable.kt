package dev.gaddal.data.db.schemas.appointments_and_scheduling

import dev.gaddal.data.db.schemas.appointments_and_scheduling.AppointmentRemindersTable.appointment_id
import dev.gaddal.data.db.schemas.appointments_and_scheduling.AppointmentRemindersTable.createdAt
import dev.gaddal.data.db.schemas.appointments_and_scheduling.AppointmentRemindersTable.id
import dev.gaddal.data.db.schemas.appointments_and_scheduling.AppointmentRemindersTable.reminder_type
import dev.gaddal.data.db.schemas.appointments_and_scheduling.AppointmentRemindersTable.scheduled_datetime
import dev.gaddal.data.db.schemas.appointments_and_scheduling.enums.ReminderType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

/**
 * Represents the appointment_reminders table in a PostgresSQL database, managing automated reminders sent to donors about their upcoming appointments.
 * This table tracks the type of reminder (email, SMS, etc.), and when it is scheduled to be sent.
 *
 * @property id Unique identifier for the reminder, automatically incremented.
 * @property appointment_id Foreign key reference to the associated appointment.
 * @property reminder_type Type of reminder (e.g., 'email', 'sms').
 * @property scheduled_datetime Timestamp for when the reminder is scheduled to be sent.
 * @property createdAt Timestamp of the creation date of the reminder record.
 */
object AppointmentRemindersTable : Table("appointment_reminders") {
    val id = integer("id").autoIncrement()
    val appointment_id = integer("appointment_id").references(AppointmentsTable.id)
    val reminder_type = enumerationByName("reminder_type", 50, ReminderType::class).default(ReminderType.SMS)
    val scheduled_datetime = timestampWithTimeZone("scheduled_datetime")
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestamp())

    override val primaryKey = PrimaryKey(id)
}
