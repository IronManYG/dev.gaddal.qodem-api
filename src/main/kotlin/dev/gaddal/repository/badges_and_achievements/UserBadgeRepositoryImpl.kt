package dev.gaddal.repository.badges_and_achievements

import dev.gaddal.data.db.DatabaseFactory
import dev.gaddal.data.db.schemas.badges_and_achievements.UserBadgeTable
import dev.gaddal.data.models.entities.UserBadge
import dev.gaddal.data.models.entities.toUserBadge
import dev.gaddal.utils.OperationError
import dev.gaddal.utils.Result
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jetbrains.exposed.sql.selectAll

/**
 * Implementation of [UserBadgeRepository] that interacts with the database to perform user badge-related operations.
 *
 * This class provides methods for retrieving user badges by user ID, as well as error handling.
 * It uses [DatabaseFactory] to manage database connections and transactions.
 */
class UserBadgeRepositoryImpl : UserBadgeRepository {
    private val logger = KotlinLogging.logger {}

    override suspend fun getUserBadgesByUserId(userId: Int): Result<List<UserBadge>> {
        return try {
            val badges = DatabaseFactory.dbQuery {
                UserBadgeTable.selectAll().where { UserBadgeTable.user_id eq userId }.mapNotNull { it.toUserBadge() }
            }
            Result.Success(badges)
        } catch (e: Exception) {
            logger.error(e) { "Error retrieving user badges for user with id $userId" }
            Result.Error(OperationError.Database("Error retrieving uer badges"))
        }
    }
}