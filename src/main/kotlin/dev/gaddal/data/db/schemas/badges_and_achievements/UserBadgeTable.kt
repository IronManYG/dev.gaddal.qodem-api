package dev.gaddal.data.db.schemas.badges_and_achievements

import dev.gaddal.data.db.schemas.badges_and_achievements.UserBadgeTable.badge_id
import dev.gaddal.data.db.schemas.badges_and_achievements.UserBadgeTable.user_id
import dev.gaddal.data.db.schemas.core_donation_management.UserTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

/**
 * Represents the user_badges table in a PostgresSQL database, tracking badges or awards earned by donors based on donation milestones or community involvement.
 * This table links users to the badges they have earned, indicating which achievements have been recognized.
 *
 * @property user_id Foreign key reference to the user who earned the badge.
 * @property badge_id Foreign key reference to the badge that has been earned.
 * @property createdAt Timestamp of when the badge was awarded to the user.
 */
object UserBadgeTable : Table("user_badges") {
    val user_id = integer("user_id").references(UserTable.id)
    val badge_id = integer("badge_id").references(BadgeTable.id)
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }

    // Composite primary key to ensure each user-badge pair is unique
    override val primaryKey = PrimaryKey(user_id, badge_id, name = "pk_user_badges")
}