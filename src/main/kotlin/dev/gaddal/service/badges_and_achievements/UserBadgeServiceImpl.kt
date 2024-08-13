package dev.gaddal.service.badges_and_achievements

import dev.gaddal.repository.badges_and_achievements.UserBadgeRepository
import dev.gaddal.utils.BaseResponse
import dev.gaddal.utils.ErrorHandler
import dev.gaddal.utils.MessageBuilder
import dev.gaddal.utils.Result
import io.github.oshai.kotlinlogging.KotlinLogging

/**
 * Implementation of [UserBadgeService] that interacts with [UserBadgeRepository] to perform user badge operations.
 *
 * This class provides methods for retrieving user badges data. It handles the conversion of repository
 * results to appropriate [BaseResponse] objects.
 *
 * @property userBadgeRepository The repository responsible for user badge data operations.
 */
class UserBadgeServiceImpl(
    private val userBadgeRepository: UserBadgeRepository
) : UserBadgeService {
    companion object {
        private val logger = KotlinLogging.logger {}
        private val messageBuilder = MessageBuilder("User Badge")
    }

    override suspend fun getUserBadgesByUserId(userId: Int): BaseResponse<Any> {
        return when (val result = userBadgeRepository.getUserBadgesByUserId(userId)) {
            is Result.Success -> BaseResponse.SuccessResponse(data = result.value, message = messageBuilder.success())
            is Result.Error -> ErrorHandler.handleError(
                result.error,
                logger,
                messageBuilder.notFound(),
                messageBuilder.processingError()
            )
        }
    }
}