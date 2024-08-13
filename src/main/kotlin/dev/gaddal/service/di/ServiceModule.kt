package dev.gaddal.service.di

import dev.gaddal.service.badges_and_achievements.UserBadgeService
import dev.gaddal.service.badges_and_achievements.UserBadgeServiceImpl
import dev.gaddal.service.core_donation_management.DonationRecordService
import dev.gaddal.service.core_donation_management.DonationRecordServiceImpl
import dev.gaddal.service.core_donation_management.UserService
import dev.gaddal.service.core_donation_management.UserServiceImpl
import dev.gaddal.service.medical_and_regulatory.MedicalHistoryService
import dev.gaddal.service.medical_and_regulatory.MedicalHistoryServiceImpl
import org.koin.dsl.module

val serviceModule = module {
    // Core donation management
    single<DonationRecordService> { DonationRecordServiceImpl(get()) }
    single<UserService> { UserServiceImpl(get()) }

    // Medical and regulatory
    single<MedicalHistoryService> { MedicalHistoryServiceImpl(get()) }

    // Badges and achievements
    single<UserBadgeService> { UserBadgeServiceImpl(get()) }
}