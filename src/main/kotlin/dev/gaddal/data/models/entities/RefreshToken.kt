package dev.gaddal.data.models.entities

import dev.gaddal.data.db.schemas.refresh_token_service.RefreshTokenTable
import org.jetbrains.exposed.sql.ResultRow

/**
 * Represents a RefreshToken in the application.
 *
 * This data class is used to model the data from the `refresh_tokens` table in the database.
 * Each property in the class corresponds to a column in the `refresh_tokens` table.
 *
 * @see dev.gaddal.data.db.schemas.refresh_token_service.RefreshTokenTable
 */
data class RefreshToken(
    val id: Int,
    val userId: Int,
    val value: String,
    val createdAt: String,
    val updatedAt: String,
)

fun ResultRow?.toRefreshToken(): RefreshToken? {
    return if (this == null) null
    else RefreshToken(
        id = this[RefreshTokenTable.id].value,
        userId = this[RefreshTokenTable.userId],
        value = this[RefreshTokenTable.value],
        createdAt = this[RefreshTokenTable.createdAt].toString(),
        updatedAt = this[RefreshTokenTable.updatedAt].toString()
    )
}