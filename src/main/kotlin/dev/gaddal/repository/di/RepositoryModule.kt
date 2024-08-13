package dev.gaddal.repository.di

import dev.gaddal.repository.badges_and_achievements.UserBadgeRepository
import dev.gaddal.repository.badges_and_achievements.UserBadgeRepositoryImpl
import dev.gaddal.repository.core_donation_management.DonationRecordRepository
import dev.gaddal.repository.core_donation_management.DonationRecordRepositoryImpl
import dev.gaddal.repository.core_donation_management.UserRepository
import dev.gaddal.repository.core_donation_management.UserRepositoryImpl
import dev.gaddal.repository.medical_and_regulatory.MedicalHistoryRepository
import dev.gaddal.repository.medical_and_regulatory.MedicalHistoryRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    // Core donation management
    single<DonationRecordRepository> { DonationRecordRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl() }

    // Medical and regulatory
    single<MedicalHistoryRepository> { MedicalHistoryRepositoryImpl() }

    // Badges and achievements
    single<UserBadgeRepository> { UserBadgeRepositoryImpl() }
}