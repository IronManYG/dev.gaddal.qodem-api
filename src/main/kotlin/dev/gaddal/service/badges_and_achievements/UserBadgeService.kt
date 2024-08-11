package dev.gaddal.service.badges_and_achievements

import dev.gaddal.utils.BaseResponse

/**
 * Service interface for managing user badge operations.
 * This interface defines methods for retrieving user badges data.
 */
interface UserBadgeService {
    /**
     * Retrieves all badges associated with a specific user.
     *
     * @param userId The ID of the user whose badges are to be retrieved.
     * @return A [BaseResponse] containing the user badges data or an error message.
     */
    suspend fun getUserBadgesByUserId(userId: Int): BaseResponse<Any>
}