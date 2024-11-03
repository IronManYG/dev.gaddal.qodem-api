package dev.gaddal.repository.refresh_token

import dev.gaddal.data.db.DatabaseFactory
import dev.gaddal.data.db.schemas.refresh_token_service.RefreshTokenTable
import dev.gaddal.data.models.entities.RefreshToken
import dev.gaddal.data.models.entities.toRefreshToken
import dev.gaddal.utils.OperationError
import dev.gaddal.utils.Result
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll

class RefreshTokenRepositoryImpl : RefreshTokenRepository {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override suspend fun getRefreshTokenByValue(value: String): Result<RefreshToken> {
        return try {
            val refreshToken = DatabaseFactory.dbQuery {
                RefreshTokenTable.selectAll()
                    .where { RefreshTokenTable.value eq value }
                    .first()
                    .toRefreshToken()
            }
            if (refreshToken != null) {
                Result.Success(refreshToken)
            } else {
                Result.Error(OperationError.NotFound("RefreshToken with value $value not found"))
            }
        } catch (e: Exception) {
            logger.error(e) { "Error retrieving refreshToken with value $value" }
            Result.Error(OperationError.Database("Error retrieving refreshToken"))
        }
    }

    override suspend fun add(userId: Int, value: String): Result<RefreshToken> {
        return try {
            val refreshToken = DatabaseFactory.dbQuery {
                // Insert refresh token
                val refreshTokenId = RefreshTokenTable.insertAndGetId {
                    it[RefreshTokenTable.userId] = userId
                    it[RefreshTokenTable.value] = value
                    // createdAt and updatedAt will use the default CurrentTimestamp()
                }

                // Retrieve the newly created refresh token
                RefreshTokenTable
                    .selectAll()
                    .where { (RefreshTokenTable.id eq refreshTokenId) and (RefreshTokenTable.userId eq userId) }
                    .firstOrNull()
                    ?.toRefreshToken()
            }
            if (refreshToken != null) {
                Result.Success(refreshToken)
            } else {
                Result.Error(OperationError.Database("Failed to insert refreshToken"))
            }
        } catch (e: Exception) {
            logger.error(e) { "Error adding refreshToken" }
            Result.Error(OperationError.Database("Error adding refreshToken"))
        }
    }

    override suspend fun invalidateRefreshToken(value: String): Result<Boolean> {
        return try {
            val deletedCount = DatabaseFactory.dbQuery {
                RefreshTokenTable.deleteWhere { RefreshTokenTable.value eq value }
            }
            if (deletedCount > 0) {
                logger.info { "Invalidated refresh token successfully" }
                Result.Success(true)
            } else {
                Result.Error(OperationError.NotFound("RefreshToken with value $value not found"))
            }
        } catch (e: Exception) {
            logger.error(e) { "Error invalidating refreshToken" }
            Result.Error(OperationError.Database("Error invalidating refreshToken"))
        }
    }
}