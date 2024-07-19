package dev.gaddal.data.db.schemas.blood_unit_lifecycle

import dev.gaddal.data.db.schemas.blood_unit_lifecycle.Transfusions.amount_transfused
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.Transfusions.createdAt
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.Transfusions.donation_tracking_id
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.Transfusions.id
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.Transfusions.patient_id
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.Transfusions.transfused_component
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.Transfusions.transfusion_datetime
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.enums.BloodComponent
import dev.gaddal.data.db.schemas.core_donation_management.DonationTrackingTable
import dev.gaddal.data.db.schemas.core_donation_management.UserTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

/**
 * Represents the transfusions table in a PostgresSQL database, logging when a blood unit or component is used in a transfusion.
 * This table includes details about the recipient, the date and time of the transfusion, and specifics about the transfused component.
 *
 * @property id Unique identifier for the transfusion record, automatically incremented.
 * @property donation_tracking_id Foreign key reference to the associated donation tracking record.
 * @property patient_id Foreign key reference to the associated user (the recipient of the transfusion).
 * @property transfusion_datetime Timestamp for when the transfusion occurred.
 * @property transfused_component Blood component transfused (e.g., 'red blood cells', 'plasma').
 * @property amount_transfused Volume of the component transfused, in milliliters.
 * @property createdAt Timestamp of the creation date of the transfusion record.
 */
object Transfusions : IntIdTable("transfusions") {
    val donation_tracking_id = integer("donation_tracking_id").references(DonationTrackingTable.id)
    val patient_id = integer("patient_id").references(UserTable.id)
    val transfusion_datetime = timestampWithTimeZone("transfusion_datetime")
    val transfused_component = enumerationByName("component_produced", 20, BloodComponent::class)
    val amount_transfused = float("amount_transfused")
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestamp())
}