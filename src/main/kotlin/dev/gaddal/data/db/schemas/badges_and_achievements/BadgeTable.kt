package dev.gaddal.data.db.schemas.badges_and_achievements

import dev.gaddal.data.db.schemas.badges_and_achievements.BadgeTable.createdAt
import dev.gaddal.data.db.schemas.badges_and_achievements.BadgeTable.description
import dev.gaddal.data.db.schemas.badges_and_achievements.BadgeTable.id
import dev.gaddal.data.db.schemas.badges_and_achievements.BadgeTable.image_url
import dev.gaddal.data.db.schemas.badges_and_achievements.BadgeTable.name
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

/**
 * Represents the badges table in a PostgresSQL database, storing digital icons that symbolize achievements within the blood donation system.
 * This table tracks the name, description, and image of each badge, which represent various achievements like donation frequency, educational participation, or community contributions.
 *
 * @property id Unique identifier for the badge, automatically incremented.
 * @property name Name of the badge.
 * @property description Description of what the badge represents or how it can be earned.
 * @property image_url URL or name of the badge's image.
 * @property createdAt Timestamp of the creation date of the badge record.
 */
object BadgeTable : IntIdTable("badges") {
    val name = varchar("name", 255)
    val description = text("description")
    val image_url = varchar("image_url", 255)
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestamp())
}
