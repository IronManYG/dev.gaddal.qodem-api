package dev.gaddal.data.db.schemas.communities_and_campaigns

import dev.gaddal.data.db.schemas.communities_and_campaigns.CommunityTable.createdAt
import dev.gaddal.data.db.schemas.communities_and_campaigns.CommunityTable.description
import dev.gaddal.data.db.schemas.communities_and_campaigns.CommunityTable.id
import dev.gaddal.data.db.schemas.communities_and_campaigns.CommunityTable.image_url
import dev.gaddal.data.db.schemas.communities_and_campaigns.CommunityTable.is_public
import dev.gaddal.data.db.schemas.communities_and_campaigns.CommunityTable.location
import dev.gaddal.data.db.schemas.communities_and_campaigns.CommunityTable.name
import dev.gaddal.data.db.schemas.communities_and_campaigns.CommunityTable.type
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

/**
 * Represents the communities table in a PostgresSQL database, storing basic identifying information for donor communities or groups.
 * This table includes details about the community's name, purpose, location, type, and visibility (public or private).
 *
 * @property id Unique identifier for the community, automatically incremented.
 * @property name Name of the community.
 * @property description Brief description of the community's purpose or focus.
 * @property location Geographical location or online indicator for the community.
 * @property type Type of community (e.g., 'local', 'cause-based', 'organization-sponsored').
 * @property image_url Optional URL or name of the community's profile image or logo.
 * @property is_public Indicates if the community is open to everyone or requires approval to join; '0' for private, '1' for public.
 * @property createdAt Timestamp of the creation date of the community record.
 */
object CommunityTable : IntIdTable("communities") {
    val name = varchar("name", 255)
    val description = text("description")
    val location = varchar("location", 255)
    val type = varchar("type", 100)
    val image_url = varchar("image_url", 255).nullable()
    val is_public = bool("is_public").clientDefault { true }
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestamp())
}
