package dev.gaddal.controller.di

import dev.gaddal.controller.auth.AuthController
import dev.gaddal.controller.user_donor_management.UserDonorManagementController
import org.koin.dsl.module

val controllerModule = module {
    // Authentication
    single { AuthController(get()) }

    // Core donation management
    single { UserDonorManagementController(get(),get(),get(),get()) }
}