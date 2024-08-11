package dev.gaddal.service.core_donation_management

import dev.gaddal.repository.core_donation_management.DonationRecordRepository
import dev.gaddal.utils.BaseResponse
import dev.gaddal.utils.ErrorHandler
import dev.gaddal.utils.MessageBuilder
import dev.gaddal.utils.Result
import io.github.oshai.kotlinlogging.KotlinLogging

/**
 * Implementation of [DonationRecordService] that interacts with [DonationRecordRepository] to perform donation record-related operations.
 *
 * This class provides methods for retrieving donation records for a specific user. It handles the conversion of repository
 * results to appropriate [BaseResponse] objects.
 *
 * @property donationRecordRepository The repository responsible for donation record data operations.
 */
class DonationRecordServiceImpl(
    private val donationRecordRepository: DonationRecordRepository
) : DonationRecordService {
    companion object {
        private val logger = KotlinLogging.logger {}
        private val messageBuilder = MessageBuilder("Donation Record")
    }

    override suspend fun getDonationRecordsByUserId(userId: Int): BaseResponse<Any> {
        return when (val result = donationRecordRepository.getDonationRecordsByUserId(userId)) {
            is Result.Success -> BaseResponse.SuccessResponse(data = result.value, message = messageBuilder.success())
            is Result.Error -> ErrorHandler.handleError(
                result.error,
                logger,
                messageBuilder.notFound(),
                messageBuilder.processingError()
            )
        }
    }

    override suspend fun getDonationRecordsByUserId(userId: Int, page: Int, limit: Int): BaseResponse<Any> {
        return when (val result = donationRecordRepository.getDonationRecordsByUserId(userId, page, limit)) {
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