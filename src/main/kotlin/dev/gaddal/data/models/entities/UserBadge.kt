package dev.gaddal.data.models.entities

import dev.gaddal.data.db.schemas.badges_and_achievements.UserBadgeTable
import org.jetbrains.exposed.sql.ResultRow

/**
 * Represents a user badge in the application.
 *
 * This data class is used to model the data from the `UserBadgeTable` in the database.
 * Each property in the class corresponds to a column in the `UserBadgeTable`.
 *
 * @see dev.gaddal.data.db.schemas.badges_and_achievements.UserBadgeTable
 */
data class UserBadge(
    val userId: Int,
    val badgeId: Int,
)

fun ResultRow?.toUserBadge(): UserBadge? {
    return if (this == null) null
    else UserBadge(
        userId = this[UserBadgeTable.user_id],
        badgeId = this[UserBadgeTable.badge_id],
    )
}
