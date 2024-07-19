package dev.gaddal.data.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.gaddal.data.db.dummydata.DummyDataInserter
import dev.gaddal.data.db.schemas.appointments_and_scheduling.AppointmentRemindersTable
import dev.gaddal.data.db.schemas.appointments_and_scheduling.AppointmentsTable
import dev.gaddal.data.db.schemas.appointments_and_scheduling.AvailableTimeslotsTable
import dev.gaddal.data.db.schemas.appointments_and_scheduling.WaitlistTable
import dev.gaddal.data.db.schemas.badges_and_achievements.BadgeTable
import dev.gaddal.data.db.schemas.badges_and_achievements.UserBadgeTable
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodInventoryTable
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodProcessingTable
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodTestingTable
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.Transfusions
import dev.gaddal.data.db.schemas.communities_and_campaigns.CampaignInfoTable
import dev.gaddal.data.db.schemas.communities_and_campaigns.CommunityMembersTable
import dev.gaddal.data.db.schemas.communities_and_campaigns.CommunityModeratorTable
import dev.gaddal.data.db.schemas.communities_and_campaigns.CommunityTable
import dev.gaddal.data.db.schemas.core_donation_management.*
import dev.gaddal.data.db.schemas.medical_and_regulatory.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

/**
 * Singleton object responsible for managing the database connections and schema setup for the Qodem application.
 * It ensures that all necessary tables are created and pre-populated with dummy data upon initialization.
 */
object DatabaseFactory : KoinComponent {

    /**
     * Initializes the database by setting up a connection pool, creating tables, and inserting dummy data.
     * Tables are created based on the defined Exposed schemas and are populated in a transaction.
     */
    fun init() {
        Database.connect(hikari())
        transaction {
            // Creating tables
            SchemaUtils.create(
                // Core Donation Management
                DonationCenterTable,
                DonationCenterTypeTable,
                NameInfoTable,
                ContactDetailTable,
                CenterSocialLinkTable,
                UserTable,
                UserNameTable,
                IdentificationTable,
                OperationInfoTable,
                DonationRecordsTable,
                DonationTrackingTable,
                ReviewsTable,
                // Medical and Regulatory
                MedicalHistoryTable,
                PastIllnessesTable,
                SurgeriesTable,
                AllergiesTable,
                EligibilityCriteriaTable,
                // Blood Unit Lifecycle
                BloodInventoryTable,
                BloodTestingTable,
                BloodProcessingTable,
                Transfusions,
                // Appointments and Scheduling
                AppointmentsTable,
                AvailableTimeslotsTable,
                AppointmentRemindersTable,
                WaitlistTable,

                // Communities and Campaigns
                CommunityTable,
                CommunityMembersTable,
                CommunityModeratorTable,
                CampaignInfoTable,

                // Badges and Achievements
                BadgeTable,
                UserBadgeTable
            )

            // Inserting dummy data
            runBlocking {
                val donationCenterTypeInserter: DummyDataInserter by inject(qualifier = named("donationCenterType"))
                donationCenterTypeInserter.insert()

                val donationCenterInserter: DummyDataInserter by inject(qualifier = named("donationCenter"))
                donationCenterInserter.insert()

                val nameInfoInserter: DummyDataInserter by inject(qualifier = named("nameInfo"))
                nameInfoInserter.insert()

                val contactDetailsInserter: DummyDataInserter by inject(qualifier = named("contactDetails"))
                contactDetailsInserter.insert()

                val operationInfoInserter: DummyDataInserter by inject(qualifier = named("operationInfo"))
                operationInfoInserter.insert()
            }
        }
    }

    /**
     * Configures and returns a HikariCP data source.
     * HikariCP is a high-performance JDBC connection pool.
     * @return Configured HikariDataSource ready to be used by Exposed.
     */
    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = "jdbc:postgresql:Qodem?user=postgres&password=123456"
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    /**
     * Executes a database query in a non-blocking manner by switching to the IO Dispatcher.
     * @param block The block of code to execute within a transaction.
     * @return Returns the result of the block execution.
     */
    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}