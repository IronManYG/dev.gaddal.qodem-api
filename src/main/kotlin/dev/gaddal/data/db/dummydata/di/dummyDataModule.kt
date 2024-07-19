package dev.gaddal.data.db.dummydata.di

import dev.gaddal.data.db.dummydata.DummyDataInserter
import dev.gaddal.data.db.dummydata.core_donation_management.contact_details.ContactDetailsDummyDataConfig
import dev.gaddal.data.db.dummydata.core_donation_management.contact_details.ContactDetailsDummyDataInserter
import dev.gaddal.data.db.dummydata.core_donation_management.donation_center_types.DonationCenterTypesDummyDataConfig
import dev.gaddal.data.db.dummydata.core_donation_management.donation_center_types.DonationCenterTypesDummyDataInserter
import dev.gaddal.data.db.dummydata.core_donation_management.donation_centers.DonationCenterDummyDataConfig
import dev.gaddal.data.db.dummydata.core_donation_management.donation_centers.DonationCenterDummyDataInserter
import dev.gaddal.data.db.dummydata.core_donation_management.name_info.NameInfoDummyDataConfig
import dev.gaddal.data.db.dummydata.core_donation_management.name_info.NameInfoDummyDataInserter
import dev.gaddal.data.db.dummydata.core_donation_management.operation_info.OperationInfoDummyDataConfig
import dev.gaddal.data.db.dummydata.core_donation_management.operation_info.OperationInfoDummyDataInserter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dummyDataModule = module {
    single { DonationCenterTypesDummyDataConfig() }
    single { DonationCenterDummyDataConfig() }
    single { NameInfoDummyDataConfig() }
    single { ContactDetailsDummyDataConfig() }
    single { OperationInfoDummyDataConfig() }

    single<DummyDataInserter>(qualifier = named("donationCenterType")) {
        DonationCenterTypesDummyDataInserter(get<DonationCenterTypesDummyDataConfig>())
    }
    single<DummyDataInserter>(qualifier = named("donationCenter")) {
        DonationCenterDummyDataInserter(get<DonationCenterDummyDataConfig>())
    }
    single<DummyDataInserter>(qualifier = named("nameInfo")) {
        NameInfoDummyDataInserter(get<NameInfoDummyDataConfig>())
    }
    single<DummyDataInserter>(qualifier = named("contactDetails")) {
        ContactDetailsDummyDataInserter(get<ContactDetailsDummyDataConfig>())
    }
    single<DummyDataInserter>(qualifier = named("operationInfo")) {
        OperationInfoDummyDataInserter(get<OperationInfoDummyDataConfig>())
    }
}