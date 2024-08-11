package dev.gaddal

import dev.gaddal.controller.di.controllerModule
import dev.gaddal.data.db.dummydata.di.dummyDataModule
import dev.gaddal.plugins.*
import dev.gaddal.repository.di.repositoryModule
import dev.gaddal.service.di.serviceModule
import dev.gaddal.utils.configureStatusPages
import io.ktor.server.application.*
import org.koin.core.context.startKoin

/**
 * Main entry point of the application.
 * Initializes Koin for dependency injection and starts the Ktor server.
 *
 * @param args Command line arguments passed to the application.
 */
fun main(args: Array<String>) {
    startKoin {
        modules(
            dummyDataModule,
            repositoryModule,
            serviceModule,
            controllerModule,
        )
    }

    io.ktor.server.tomcat.EngineMain.main(args)
}

/**
 * Configures the Ktor application module.
 * This function sets up various features and plugins for the Ktor server.
 */
fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureHTTP()
    configureSecurity()
    configureStatusPages()
    configureRouting()
}