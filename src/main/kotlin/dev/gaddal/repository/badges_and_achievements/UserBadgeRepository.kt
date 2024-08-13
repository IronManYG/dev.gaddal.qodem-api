package dev.gaddal.repository.badges_and_achievements

import dev.gaddal.data.models.entities.UserBadge
import dev.gaddal.utils.Result

/**
 * Repository interface for managing user badge-related operations.
 * This interface defines methods for retrieving user badges by user ID.
 */
interface UserBadgeRepository {
    /**
     * Retrieves all badges for a user by their unique identifier.
     *
     * @param userId The unique identifier of the user to retrieve badges for.
     * @return A [Result] containing a list of [UserBadge]s if found, or an error if not found or if a database error occurs.
     */
    suspend fun getUserBadgesByUserId(userId: Int): Result<List<UserBadge>>
}