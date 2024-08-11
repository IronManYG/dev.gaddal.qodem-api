package dev.gaddal.data.models.entities

import com.fasterxml.jackson.annotation.JsonInclude
import dev.gaddal.data.db.schemas.badges_and_achievements.BadgeTable
import org.jetbrains.exposed.sql.ResultRow

/**
 * Represents a badge in the application.
 *
 * This data class is used to model the data from the `BadgeTable` in the database.
 * Each property in the class corresponds to a column in the `BadgeTable`.
 *
 * @see dev.gaddal.data.db.schemas.badges_and_achievements.BadgeTable
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Badge(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val createdAt: String
)

fun ResultRow?.toBadge(): Badge? {
    return if (this == null) null
    else Badge(
        id = this[BadgeTable.id].value,
        name = this[BadgeTable.name],
        description = this[BadgeTable.description],
        imageUrl = this[BadgeTable.image_url],
        createdAt = this[BadgeTable.createdAt].toString()
    )
}
