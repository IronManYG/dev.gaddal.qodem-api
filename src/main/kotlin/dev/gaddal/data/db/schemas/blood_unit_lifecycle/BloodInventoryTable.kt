package dev.gaddal.data.db.schemas.blood_unit_lifecycle

import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodInventoryTable.amount
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodInventoryTable.blood_type
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodInventoryTable.createdAt
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodInventoryTable.donation_center_id
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodInventoryTable.id
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodInventoryTable.inventory_update_timestamp
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodInventoryTable.volume
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

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
object BloodInventoryTable : Table("blood_inventory") {
    val id = integer("id").autoIncrement()
    val donation_center_id = integer("donation_center_id").references(DonationCenterTable.id)
    val blood_type = varchar("blood_type", 50)
    val amount = integer("amount")
    val volume = float("volume")
    val inventory_update_timestamp = long("inventory_update_timestamp")  // Using long to store UNIX timestamp
    val createdAt = datetime("created_at").defaultExpression(CurrentTimestamp())

    override val primaryKey = PrimaryKey(id)
}
