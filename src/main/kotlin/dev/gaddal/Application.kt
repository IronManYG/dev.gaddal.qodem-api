package dev.gaddal

import dev.gaddal.controller.di.controllerModule
import dev.gaddal.data.db.dummydata.di.dummyDataModule
import dev.gaddal.plugins.*
import dev.gaddal.repository.di.repositoryModule
import dev.gaddal.security.di.securityModule
import dev.gaddal.service.di.serviceModule
import dev.gaddal.utils.configureStatusPages
import io.ktor.server.application.*
import io.ktor.server.tomcat.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

/**
 * Entry point for running the Ktor application with Tomcat as the server engine.
 * This function is automatically invoked by the Ktor framework using the specified configuration settings in application.conf.
 *
 * @param args Command line arguments passed to the application.
 */
fun main(args: Array<String>): Unit = EngineMain.main(args)

/**
 * Application module configuration.
 * This function sets up the Ktor application, including Koin for dependency injection
 * and various Ktor features.
 */
fun Application.module() {
    // Retrieve the application configuration
    val appConfig = environment.config

    // Install and configure Koin for dependency injection
    install(Koin) {
        // Define Koin modules
        modules(
            // Existing modules
            dummyDataModule,
            repositoryModule,
            serviceModule,
            controllerModule,
            securityModule,
            // Additional module to provide ApplicationConfig to other modules
            module {
                single { appConfig }
            }
        )
    }

    // Configure various application features
    configureSerialization()  // Set up JSON serialization
    configureDatabases()      // Set up database connections
    configureHTTP()           // Configure HTTP-related settings
    configureSecurity()       // Set up authentication and authorization
    configureStatusPages()    // Configure custom error pages
    configureRouting()        // Set up application routing
}