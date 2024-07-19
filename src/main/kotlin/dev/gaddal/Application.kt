package dev.gaddal

import dev.gaddal.data.db.dummydata.di.dummyDataModule
import dev.gaddal.plugins.*
import io.ktor.server.application.*
import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    startKoin {
        modules(dummyDataModule)
    }

    io.ktor.server.tomcat.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureHTTP()
    configureSecurity()
    configureRouting()
}
