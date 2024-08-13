package dev.gaddal.service.core_donation_management

import dev.gaddal.data.models.params.UserParams
import dev.gaddal.repository.core_donation_management.UserRepository
import dev.gaddal.utils.BaseResponse
import dev.gaddal.utils.ErrorHandler
import dev.gaddal.utils.MessageBuilder
import dev.gaddal.utils.Result
import io.github.oshai.kotlinlogging.KotlinLogging

/**
 * Implementation of [UserService] that interacts with [UserRepository] to perform user-related operations.
 *
 * This class provides methods for creating, reading, updating, and deleting user data,
 * as well as retrieving paginated lists of users. It handles the conversion of repository
 * results to appropriate [BaseResponse] objects.
 *
 * @property userRepository The repository responsible for user data operations.
 */
class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    companion object {
        private val logger = KotlinLogging.logger {}
        private val messageBuilder = MessageBuilder("User")
    }

    override suspend fun getUserById(id: Int): BaseResponse<Any> {
        return when (val result = userRepository.getUserById(id)) {
            is Result.Success -> BaseResponse.SuccessResponse(data = result.value, message = messageBuilder.success())
            is Result.Error -> ErrorHandler.handleError(
                result.error,
                logger,
                messageBuilder.notFound(),
                messageBuilder.processingError()
            )
        }
    }

    override suspend fun getUsers(): BaseResponse<Any> {
        return when (val result = userRepository.getUsers()) {
            is Result.Success -> BaseResponse.SuccessResponse(data = result.value, message = messageBuilder.success())
            is Result.Error -> ErrorHandler.handleError(
                result.error,
                logger,
                messageBuilder.notFound(),
                messageBuilder.processingError()
            )
        }
    }

    override suspend fun getUsers(page: Int, limit: Int): BaseResponse<Any> {
        return when (val result = userRepository.getUsers(page, limit)) {
            is Result.Success -> BaseResponse.SuccessResponse(data = result.value, message = messageBuilder.success())
            is Result.Error -> ErrorHandler.handleError(
                result.error,
                logger,
                messageBuilder.notFound(),
                messageBuilder.processingError()
            )
        }
    }

    override suspend fun addUser(userParams: UserParams): BaseResponse<Any> {
        return when (val result = userRepository.addUser(userParams)) {
            is Result.Success -> BaseResponse.SuccessResponse(
                data = result.value,
                message = messageBuilder.createdSuccess()
            )
            is Result.Error -> ErrorHandler.handleError(
                result.error,
                logger,
                messageBuilder.notFound(),
                messageBuilder.processingError()
            )
        }
    }

    override suspend fun updateUser(id: Int, userParams: UserParams): BaseResponse<Any> {
        return when (val result = userRepository.updateUser(id, userParams)) {
            is Result.Success -> BaseResponse.SuccessResponse(
                data = result.value,
                message = messageBuilder.updatedSuccess()
            )
            is Result.Error -> ErrorHandler.handleError(
                result.error,
                logger,
                messageBuilder.notFound(),
                messageBuilder.processingError()
            )
        }
    }

    override suspend fun deleteUser(userId: Int): BaseResponse<Any> {
        return when (val result = userRepository.deleteUser(userId)) {
            is Result.Success -> BaseResponse.SuccessResponse(data = null, message = messageBuilder.deletedSuccess())
            is Result.Error -> ErrorHandler.handleError(
                result.error,
                logger,
                messageBuilder.notFound(),
                messageBuilder.processingError()
            )
        }
    }
}