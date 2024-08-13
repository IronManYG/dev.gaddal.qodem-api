package dev.gaddal.service.medical_and_regulatory

import dev.gaddal.data.models.params.MedicalHistoryParams
import dev.gaddal.repository.medical_and_regulatory.MedicalHistoryRepository
import dev.gaddal.utils.BaseResponse
import dev.gaddal.utils.ErrorHandler.handleError
import dev.gaddal.utils.MessageBuilder
import dev.gaddal.utils.Result
import io.github.oshai.kotlinlogging.KotlinLogging

/**
 * Implementation of [MedicalHistoryService] that interacts with [MedicalHistoryRepository] to perform medical history-related operations.
 *
 * This class provides methods for retrieving and updating medical history data. It handles the conversion of repository
 * results to appropriate [BaseResponse] objects.
 *
 * @property medicalHistoryRepository The repository responsible for medical history data operations.
 */
class MedicalHistoryServiceImpl(
    private val medicalHistoryRepository: MedicalHistoryRepository
) : MedicalHistoryService {
    companion object {
        private val logger = KotlinLogging.logger {}
        private val messageBuilder = MessageBuilder("Medical History")
    }

    override suspend fun getMedicalHistoryByUserId(userId: Int): BaseResponse<Any> {
        return when (val result = medicalHistoryRepository.getMedicalHistoryByUserId(userId)) {
            is Result.Success -> BaseResponse.SuccessResponse(data = result.value, message = messageBuilder.success())
            is Result.Error -> handleError(
                result.error,
                logger,
                messageBuilder.notFound(),
                messageBuilder.processingError()
            )
        }
    }

    override suspend fun updateMedicalHistory(
        userId: Int,
        medicalHistoryParams: MedicalHistoryParams
    ): BaseResponse<Any> {
        return when (val result = medicalHistoryRepository.updateMedicalHistory(userId, medicalHistoryParams)) {
            is Result.Success -> BaseResponse.SuccessResponse(data = result.value, message = messageBuilder.success())
            is Result.Error -> handleError(
                result.error,
                logger,
                messageBuilder.notFound(),
                messageBuilder.processingError()
            )
        }
    }
}