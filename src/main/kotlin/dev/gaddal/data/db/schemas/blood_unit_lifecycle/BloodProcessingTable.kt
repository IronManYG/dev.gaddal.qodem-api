package dev.gaddal.data.db.schemas.blood_unit_lifecycle

import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodProcessingTable.component_produced
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodProcessingTable.createdAt
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodProcessingTable.donation_tracking_id
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodProcessingTable.id
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodProcessingTable.processing_datetime
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodProcessingTable.volume_produced
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.enums.BloodComponent
import dev.gaddal.data.db.schemas.core_donation_management.DonationTrackingTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

/**
 * Represents the blood_processing table in a PostgreSQL database, tracking the processing of donated blood units into various components.
 * This table includes information about when a blood unit is processed and details about the components produced.
 *
 * @property id Unique identifier for the blood processing record, automatically incremented.
 * @property donation_tracking_id Foreign key reference to the associated donation tracking record.
 * @property processing_datetime Timestamp for when the blood was processed.
 * @property component_produced The type of blood component produced from this processing.
 * @property volume_produced Volume of the component produced, in milliliters.
 * @property createdAt Timestamp of the creation date of the blood processing record.
 */
object BloodProcessingTable : IntIdTable("blood_processing") {
    val donation_tracking_id = integer("donation_tracking_id").references(DonationTrackingTable.id)
    val processing_datetime = timestampWithTimeZone("processing_datetime")
    val component_produced = enumerationByName("component_produced", 20, BloodComponent::class)
    val volume_produced = float("volume_produced")
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestamp())
}

