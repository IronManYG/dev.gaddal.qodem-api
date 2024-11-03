package dev.gaddal.data.db.schemas.refresh_token_service

import dev.gaddal.data.db.schemas.core_donation_management.UserTable
import dev.gaddal.data.db.schemas.refresh_token_service.RefreshTokenTable.createdAt
import dev.gaddal.data.db.schemas.refresh_token_service.RefreshTokenTable.id
import dev.gaddal.data.db.schemas.refresh_token_service.RefreshTokenTable.updatedAt
import dev.gaddal.data.db.schemas.refresh_token_service.RefreshTokenTable.userId
import dev.gaddal.data.db.schemas.refresh_token_service.RefreshTokenTable.value
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

/**
 * Represents the refresh_tokens table in the database.
 * This table stores the refresh tokens for OAuth authentication.
 *
 * @property id Unique identifier for the OAuth token, automatically incremented.
 * @property userId Foreign key reference to the associated user, with cascade delete enabled.
 * @property value The refresh token value.
 * @property createdAt Timestamp with time zone for when the record was created, defaults to current timestamp.
 * @property updatedAt Timestamp with time zone for when the record was last updated, defaults to current timestamp.
 */
object RefreshTokenTable : IntIdTable("refresh_tokens") {
    val userId = integer("user_id").references(UserTable.id, onDelete = ReferenceOption.CASCADE)
    val value = varchar("refresh_token", 255)
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestamp())
    val updatedAt = timestampWithTimeZone("updated_at").defaultExpression(CurrentTimestamp())
}
