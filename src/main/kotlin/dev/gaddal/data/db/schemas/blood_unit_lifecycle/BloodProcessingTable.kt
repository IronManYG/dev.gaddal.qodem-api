package dev.gaddal.data.db.schemas.blood_unit_lifecycle

import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodProcessingTable.components_produced
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodProcessingTable.createdAt
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodProcessingTable.donation_tracking_id
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodProcessingTable.id
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodProcessingTable.processing_datetime
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodProcessingTable.volume_per_component
import dev.gaddal.data.db.schemas.core_donation_management.DonationTrackingTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

/**
 * Represents the blood_processing table in a PostgresSQL database, tracking the processing of donated blood units into various components.
 * This table includes information about when a blood unit is processed and details about the components produced, such as red blood cells, plasma, and platelets.
 *
 * @property id Unique identifier for the blood processing record, automatically incremented.
 * @property donation_tracking_id Foreign key reference to the associated donation tracking record.
 * @property processing_datetime Timestamp for when the blood was processed.
 * @property components_produced Description of the blood components produced (e.g., '2 units red blood cells, 1 unit platelets, 1 unit plasma').
 * @property volume_per_component Optional field to track the volume of each component produced.
 * @property createdAt Timestamp of the creation date of the blood processing record.
 */
object BloodProcessingTable : Table("blood_processing") {
    val id = integer("id").autoIncrement()
    val donation_tracking_id = integer("donation_tracking_id").references(DonationTrackingTable.id)
    val processing_datetime = long("processing_datetime")  // Using long to store UNIX timestamp
    val components_produced = text("components_produced")
    val volume_per_component = float("volume_per_component").nullable()
    val createdAt = datetime("created_at").defaultExpression(CurrentTimestamp())

    override val primaryKey = PrimaryKey(id)
}