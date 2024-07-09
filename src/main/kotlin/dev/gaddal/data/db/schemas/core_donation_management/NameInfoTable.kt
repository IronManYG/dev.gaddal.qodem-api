package dev.gaddal.data.db.schemas.core_donation_management

import dev.gaddal.data.db.schemas.core_donation_management.NameInfoTable.arabic
import dev.gaddal.data.db.schemas.core_donation_management.NameInfoTable.createdAt
import dev.gaddal.data.db.schemas.core_donation_management.NameInfoTable.donation_center_id
import dev.gaddal.data.db.schemas.core_donation_management.NameInfoTable.english
import dev.gaddal.data.db.schemas.core_donation_management.NameInfoTable.id
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

/**
 * Represents the name_info table in a PostgreSQL database, optimized for multilingual support.
 * This table stores names of donation centers in multiple languages, primarily Arabic and English.
 *
 * @property id Unique identifier for the name info, automatically incremented.
 * @property donation_center_id References the donation center this name belongs to.
 * @property arabic Donation center's name in Arabic.
 * @property english Donation center's name in English.
 * @property createdAt Timestamp of the creation date of the name info record.
 */
object NameInfoTable : IntIdTable("name_info") {
    val donation_center_id = integer("donation_center_id").references(DonationCenterTable.id)
    val arabic = varchar("arabic", 255)
    val english = varchar("english", 255)
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestamp())
}
