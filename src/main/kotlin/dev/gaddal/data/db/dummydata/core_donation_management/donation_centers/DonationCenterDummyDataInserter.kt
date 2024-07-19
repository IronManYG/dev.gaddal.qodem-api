package dev.gaddal.data.db.dummydata.core_donation_management.donation_centers

import dev.gaddal.data.db.dummydata.DummyDataConfig
import dev.gaddal.data.db.dummydata.DummyDataInserter
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTable
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

class DonationCenterDummyDataInserter(
    override val config: DonationCenterDummyDataConfig
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
                        DonationCenterTable.deleteAll()
                    }

                    val jsonData = Path(config.jsonFilePath).readText()
                    val donationCenters = json.decodeFromString<List<DonationCenterDummyData>>(jsonData)

                    donationCenters.forEach { center ->
                        insertDonationCenter(center)
                    }

                    flagFile.createFile()
                    logger.info { "Donation center dummy data inserted successfully" }
                }
            } catch (e: Exception) {
                logger.error(e) { "Error inserting donation center dummy data" }
                throw e
            }
        } else {
            logger.info { "Donation center dummy data already inserted. Flag file: $flagFile" }
        }
    }

    private fun insertDonationCenter(center: DonationCenterDummyData) {
        DonationCenterTable.insert {
            it[donation_center_type_id] = center.donationCenterTypeId
            it[city] = center.city
            it[state] = center.state
            it[country] = center.country
            it[lat] = center.lat
            it[long] = center.long
            it[street_address] = center.streetAddress
            it[address_line2] = center.addressLine2 ?: ""
            it[zip_code] = center.zipCode
        }
    }
}

@Serializable
data class DonationCenterDummyData(
    @SerialName("donation_center_type_id")
    val donationCenterTypeId: Int,

    val city: String,
    val state: String,
    val country: String,
    val lat: Float,
    val long: Float,

    @SerialName("street_address")
    val streetAddress: String,

    @SerialName("address_line2")
    val addressLine2: String?,

    @SerialName("zip_code")
    val zipCode: String
)

class DonationCenterDummyDataConfig : DummyDataConfig {
    override val dummyDataInsertedFilePath: String = "src/main/kotlin/dev/gaddal/data/db/dummydata/flags/donation_center_dummy_data_inserted.txt"
    override val jsonFilePath: String =
        "src/main/kotlin/dev/gaddal/data/db/dummydata/core_donation_management/donation_centers/donation_centers.json"
}
