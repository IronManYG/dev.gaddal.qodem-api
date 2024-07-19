package dev.gaddal.plugins

import dev.gaddal.data.db.DatabaseFactory
import io.ktor.server.application.*

/**
 * Initializes the database connections and configurations.
 * This function is responsible for setting up the database by invoking the initialization
 * process in the DatabaseFactory.
 */
fun Application.configureDatabases() {
    DatabaseFactory.init()
}
