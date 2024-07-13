package dev.gaddal.data.db.dummydata.core_donation_management.operation_info

import dev.gaddal.data.db.dummydata.DummyDataConfig
import dev.gaddal.data.db.dummydata.DummyDataInserter
import dev.gaddal.data.db.schemas.core_donation_management.OperationInfoTable
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import kotlin.io.path.Path
import kotlin.io.path.createFile
import kotlin.io.path.exists
import kotlin.io.path.readText

class OperationInfoDummyDataInserter(
    override val config: OperationInfoDummyDataConfig
) : DummyDataInserter, KoinComponent {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun insert(deleteAll: Boolean) = withContext(Dispatchers.IO) {
        val flagFile = Path(config.dummyDataInsertedFilePath)
        if (!flagFile.exists()) {
            try {
                transaction {
                    if (deleteAll) {
                        OperationInfoTable.deleteAll()
                    }

                    val jsonData = Path(config.jsonFilePath).readText()
                    val operationInfoList = json.decodeFromString<List<OperationInfoDummyData>>(jsonData)

                    operationInfoList.forEach { operationInfo ->
                        insertOperationInfo(operationInfo)
                    }

                    flagFile.createFile()
                    logger.info { "Operation info dummy data inserted successfully" }
                }
            } catch (e: Exception) {
                logger.error(e) { "Error inserting operation info dummy data" }
                throw e
            }
        } else {
            logger.info { "Operation info dummy data already inserted. Flag file: $flagFile" }
        }
    }

    private fun insertOperationInfo(operationInfo: OperationInfoDummyData) {
        OperationInfoTable.insert {
            it[donation_center_id] = operationInfo.donationCenterId
            it[workingHours] = operationInfo.workingHours
            it[workingDays] = operationInfo.workingDays
            it[gapBetweenAppointment] = operationInfo.gapBetweenAppointment
            it[donorLimit] = operationInfo.donorLimit
        }
    }
}

@Serializable
data class OperationInfoDummyData(
    @SerialName("donation_center_id")
    val donationCenterId: Int,

    val workingHours: String,
    val workingDays: String,
    val gapBetweenAppointment: Int,
    val donorLimit: Int
)

class OperationInfoDummyDataConfig : DummyDataConfig {
    override val dummyDataInsertedFilePath: String =
        "src/main/kotlin/dev/gaddal/data/db/dummydata/flags/operation_info_dummy_data_inserted.txt"
    override val jsonFilePath: String =
        "src/main/kotlin/dev/gaddal/data/db/dummydata/core_donation_management/operation_info/operation_info.json"
}