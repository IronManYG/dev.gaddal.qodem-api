package dev.gaddal.data.db.dummydata.core_donation_management.contact_details

import dev.gaddal.data.db.dummydata.DummyDataConfig
import dev.gaddal.data.db.dummydata.DummyDataInserter
import dev.gaddal.data.db.schemas.core_donation_management.ContactDetailTable
import dev.gaddal.data.db.schemas.core_donation_management.enums.ContactType
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

class ContactDetailsDummyDataInserter(
    override val config: ContactDetailsDummyDataConfig
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
                        ContactDetailTable.deleteAll()
                    }

                    val jsonData = Path(config.jsonFilePath).readText()
                    val contactDetailsList = json.decodeFromString<List<ContactDetailsDummyData>>(jsonData)

                    contactDetailsList.forEach { contactDetails ->
                        insertContactDetails(contactDetails)
                    }

                    flagFile.createFile()
                    logger.info { "Contact details dummy data inserted successfully" }
                }
            } catch (e: Exception) {
                logger.error(e) { "Error inserting contact details dummy data" }
                throw e
            }
        } else {
            logger.info { "Contact details dummy data already inserted. Flag file: $flagFile" }
        }
    }

    private fun insertContactDetails(contactDetails: ContactDetailsDummyData) {
        ContactDetailTable.insert {
            it[donation_center_id] = contactDetails.donationCenterId
            it[contact_type] = ContactType.valueOf(contactDetails.contactType)
            it[value] = contactDetails.value
        }
    }
}

@Serializable
data class ContactDetailsDummyData(
    @SerialName("donation_center_id")
    val donationCenterId: Int,

    @SerialName("contact_type")
    val contactType: String,

    val value: String
)

class ContactDetailsDummyDataConfig : DummyDataConfig {
    override val dummyDataInsertedFilePath: String = "src/main/kotlin/dev/gaddal/data/db/dummydata/flags/contact_details_dummy_data_inserted.txt"
    override val jsonFilePath: String =
        "src/main/kotlin/dev/gaddal/data/db/dummydata/core_donation_management/contact_details/contact_details.json"
}