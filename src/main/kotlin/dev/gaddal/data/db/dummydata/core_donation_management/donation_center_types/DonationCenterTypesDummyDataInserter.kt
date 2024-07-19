package dev.gaddal.data.db.dummydata.core_donation_management.donation_center_types

import dev.gaddal.data.db.dummydata.DummyDataConfig
import dev.gaddal.data.db.dummydata.DummyDataInserter
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTypeTable
import dev.gaddal.data.db.schemas.core_donation_management.enums.LocationType
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

class DonationCenterTypesDummyDataInserter(
    override val config: DonationCenterTypesDummyDataConfig
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
                        DonationCenterTypeTable.deleteAll()
                    }

                    val jsonData = Path(config.jsonFilePath).readText()
                    val donationCenterTypes = json.decodeFromString<List<DonationCenterTypeDummyData>>(jsonData)

                    donationCenterTypes.forEach { type ->
                        insertDonationCenterType(type)
                    }

                    flagFile.createFile()
                    logger.info { "Donation center types dummy data inserted successfully" }
                }
            } catch (e: Exception) {
                logger.error(e) { "Error inserting donation center types dummy data" }
                throw e
            }
        } else {
            logger.info { "Donation center types dummy data already inserted. Flag file: $flagFile" }
        }
    }

    private fun insertDonationCenterType(type: DonationCenterTypeDummyData) {
        DonationCenterTypeTable.insert {
            it[type_name] = type.typeName
            it[location_type] = LocationType.valueOf( type.locationType)
            it[appointment_required] = type.appointmentRequired
            it[short_description_en] = type.shortDescriptionEn
            it[short_description_ar] = type.shortDescriptionAr
        }
    }
}

@Serializable
data class DonationCenterTypeDummyData(
    @SerialName("type_name")
    val typeName: String,
    
    @SerialName("location_type")
    val locationType: String,
    
    @SerialName("appointment_required")
    val appointmentRequired: Boolean,
    
    @SerialName("short_description_en")
    val shortDescriptionEn: String,
    
    @SerialName("short_description_ar")
    val shortDescriptionAr: String
)

class DonationCenterTypesDummyDataConfig : DummyDataConfig {
    override val dummyDataInsertedFilePath: String = "src/main/kotlin/dev/gaddal/data/db/dummydata/flags/donation_center_types_dummy_data_inserted.txt"
    override val jsonFilePath: String =
        "src/main/kotlin/dev/gaddal/data/db/dummydata/core_donation_management/donation_center_types/donation_center_types.json"
}