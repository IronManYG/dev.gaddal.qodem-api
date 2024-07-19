package dev.gaddal.data.db.schemas.blood_unit_lifecycle

import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodInventoryTable.amount
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodInventoryTable.blood_type
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodInventoryTable.createdAt
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodInventoryTable.donation_center_id
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodInventoryTable.id
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodInventoryTable.inventory_update_timestamp
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodInventoryTable.volume
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.enums.BloodType
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

/**
 * Represents the blood_inventory table in a PostgreSQL database, managing the current supply of different blood types and products at each donation center.
 * This table tracks the type of blood, the amount in units, total volume, and timestamps updates to the inventory.
 *
 * @property id Unique identifier for the blood inventory record, automatically incremented.
 * @property donation_center_id Foreign key reference to the associated donation center.
 * @property blood_type Type of the blood in the inventory.
 * @property amount Number of blood units available.
 * @property volume Volume of blood available in milliliters.
 * @property inventory_update_timestamp Timestamp for tracking updates to the inventory (e.g., receipt, use, expiration).
 * @property createdAt Timestamp of the creation date of the blood inventory record.
 */
object BloodInventoryTable : IntIdTable("blood_inventory") {
    val donation_center_id = integer("donation_center_id").references(DonationCenterTable.id)
    val blood_type = enumerationByName("blood_type", 15, BloodType::class)
    val amount = integer("amount")
    val volume = float("volume")
    val inventory_update_timestamp = timestampWithTimeZone("inventory_update_timestamp")
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestamp())
}


