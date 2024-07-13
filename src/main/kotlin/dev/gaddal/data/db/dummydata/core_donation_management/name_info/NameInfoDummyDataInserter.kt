// NameInfoDummyDataInserter.kt
package dev.gaddal.data.db.dummydata.core_donation_management.name_info

import dev.gaddal.data.db.dummydata.DummyDataConfig
import dev.gaddal.data.db.dummydata.DummyDataInserter
import dev.gaddal.data.db.schemas.core_donation_management.NameInfoTable
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

class NameInfoDummyDataInserter(
    override val config: NameInfoDummyDataConfig
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
                        NameInfoTable.deleteAll()
                    }

                    val jsonData = Path(config.jsonFilePath).readText()
                    val nameInfoList = json.decodeFromString<List<NameInfoDummyData>>(jsonData)

                    nameInfoList.forEach { nameInfo ->
                        insertNameInfo(nameInfo)
                    }

                    flagFile.createFile()
                    logger.info { "Name info dummy data inserted successfully" }
                }
            } catch (e: Exception) {
                logger.error(e) { "Error inserting name info dummy data" }
                throw e
            }
        } else {
            logger.info { "Name info dummy data already inserted. Flag file: $flagFile" }
        }
    }

    private fun insertNameInfo(nameInfo: NameInfoDummyData) {
        NameInfoTable.insert {
            it[donation_center_id] = nameInfo.donationCenterId
            it[arabic] = nameInfo.arabic
            it[english] = nameInfo.english
        }
    }
}

@Serializable
data class NameInfoDummyData(
    @SerialName("donation_center_id")
    val donationCenterId: Int,
    val arabic: String,
    val english: String
)

class NameInfoDummyDataConfig : DummyDataConfig {
    override val dummyDataInsertedFilePath: String = "src/main/kotlin/dev/gaddal/data/db/dummydata/flags/name_info_dummy_data_inserted.txt"
    override val jsonFilePath: String =
        "src/main/kotlin/dev/gaddal/data/db/dummydata/core_donation_management/name_info/name_info.json"
}