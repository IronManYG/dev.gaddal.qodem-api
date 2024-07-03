package dev.gaddal.data.db.schemas.core_donation_management

import dev.gaddal.data.db.schemas.core_donation_management.OperationInfoTable.createdAt
import dev.gaddal.data.db.schemas.core_donation_management.OperationInfoTable.donation_center_id
import dev.gaddal.data.db.schemas.core_donation_management.OperationInfoTable.donorLimit
import dev.gaddal.data.db.schemas.core_donation_management.OperationInfoTable.gapBetweenAppointment
import dev.gaddal.data.db.schemas.core_donation_management.OperationInfoTable.id
import dev.gaddal.data.db.schemas.core_donation_management.OperationInfoTable.workingDays
import dev.gaddal.data.db.schemas.core_donation_management.OperationInfoTable.workingHours
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

/**
 * Represents the operation_info table in a PostgresSQL database, containing operational details for donation centers.
 * This table includes information about working hours, operating days, gaps required between appointments, and daily donor limits.
 *
 * @property id Unique identifier for the operation information record, automatically incremented.
 * @property donation_center_id Foreign key reference to the associated donation center, ensuring relational integrity.
 * @property workingHours Operating hours of the donation center (e.g., '8AM-5PM', '24/7').
 * @property workingDays Days of the week the center is open (e.g., 'Mon-Fri', '7 days a week').
 * @property gapBetweenAppointment Minimum time gap required between appointments for a donor, in minutes.
 * @property donorLimit Maximum number of donors the center can handle per day or per timeslot.
 * @property createdAt Timestamp of the creation date of the operation information record.
 */
object OperationInfoTable : Table("operation_info") {
    val id = integer("id").autoIncrement()
    val donation_center_id = integer("donation_center_id").references(DonationCenterTable.id)
    val workingHours = varchar("workingHours", 255)
    val workingDays = varchar("workingDays", 255)
    val gapBetweenAppointment = integer("gapBetweenAppointment")
    val donorLimit = integer("donorLimit")
    val createdAt = datetime("created_at").defaultExpression(CurrentTimestamp())

    override val primaryKey = PrimaryKey(id)
}
