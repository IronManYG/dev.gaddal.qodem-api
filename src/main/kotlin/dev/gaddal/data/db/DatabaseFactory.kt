package dev.gaddal.data.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.gaddal.data.db.schemas.core_donation_management.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Singleton object responsible for managing the database connections and schema setup for the Qodem application.
 * It ensures that all necessary tables are created and pre-populated with dummy data upon initialization.
 */
object DatabaseFactory {

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
                ReviewsTable
            )

            // Inserting dummy data
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