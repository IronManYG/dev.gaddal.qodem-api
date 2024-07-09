package dev.gaddal.data.db.schemas.core_donation_management

import dev.gaddal.data.db.schemas.core_donation_management.CenterSocialLinkTable.createdAt
import dev.gaddal.data.db.schemas.core_donation_management.CenterSocialLinkTable.donation_center_id
import dev.gaddal.data.db.schemas.core_donation_management.CenterSocialLinkTable.id
import dev.gaddal.data.db.schemas.core_donation_management.CenterSocialLinkTable.link
import dev.gaddal.data.db.schemas.core_donation_management.CenterSocialLinkTable.platform
import dev.gaddal.data.db.schemas.core_donation_management.enums.SocialMediaPlatform
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

/**
 * Represents the center_social_links table in a PostgresSQL database, optimized for linking to social media profiles.
 * This table stores links to social media profiles for donation centers, facilitating online presence and outreach.
 *
 * @property id Unique identifier for the social media link, automatically incremented.
 * @property donation_center_id References the associated donation center, ensuring relational integrity.
 * @property platform Specifies the social media platform (e.g., 'Facebook', 'Twitter', 'Instagram').
 * @property link URL of the social media profile, ensuring easy access to the center's online social media presence.
 * @property createdAt Timestamp of the creation date of the social media link record.
 */
object CenterSocialLinkTable : IntIdTable("center_social_links") {
    val donation_center_id = integer("donation_center_id").references(DonationCenterTable.id)
    val platform = enumerationByName("platform", 50, SocialMediaPlatform::class)
    val link = varchar("link", 255)
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestamp())
}
