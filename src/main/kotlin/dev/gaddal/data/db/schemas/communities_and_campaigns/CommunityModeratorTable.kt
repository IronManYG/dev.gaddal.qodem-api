package dev.gaddal.data.db.schemas.communities_and_campaigns

import dev.gaddal.data.db.schemas.communities_and_campaigns.CommunityModeratorTable.community_id
import dev.gaddal.data.db.schemas.communities_and_campaigns.CommunityModeratorTable.createdAt
import dev.gaddal.data.db.schemas.communities_and_campaigns.CommunityModeratorTable.user_id
import dev.gaddal.data.db.schemas.core_donation_management.UserTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

/**
 * Represents the community_moderators table in a PostgresSQL database, linking users with elevated privileges to specific communities.
 * This table is used to manage the moderators for each community, ensuring proper governance and management of the community space.
 *
 * @property community_id Primary key and foreign key reference to the community.
 * @property user_id Foreign key reference to the user who is a moderator of the community.
 * @property createdAt Timestamp of when the user became a moderator of the community.
 */
object CommunityModeratorTable : Table("community_moderators") {
    val community_id = integer("community_id").references(CommunityTable.id)
    val user_id = integer("user_id").references(UserTable.id)
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestamp())

    // Composite primary key to ensure each pair is unique
    override val primaryKey = PrimaryKey(community_id, user_id, name = "pk_community_moderators")
}
