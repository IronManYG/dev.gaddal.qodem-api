package dev.gaddal.data.models.entities

import com.fasterxml.jackson.annotation.JsonInclude
import dev.gaddal.data.db.schemas.core_donation_management.DonationRecordsTable
import org.jetbrains.exposed.sql.ResultRow

/**
 * Represents a DonationRecord in the application.
 *
 * This data class is used to model the data from the `DonationRecordsTable` in the database.
 * Each property in the class corresponds
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class DonationRecord(
    val id: Int,
    val donorId: Int,
    val donationCenterId: Int,
    val bloodType: String,
    val donationType: String,
    val donationPurpose: String,
    val amount: Float,
    val volume: Float,
    val donationTimestamp: String,
    val isActive: Boolean,
    val isAuthenticated: Boolean,
    val createdAt: String
)

fun ResultRow?.toDonationRecord(): DonationRecord? {
    return if (this == null) null
    else DonationRecord(
        id = this[DonationRecordsTable.id].value,
        donorId = this[DonationRecordsTable.donor_id],
        donationCenterId = this[DonationRecordsTable.donation_center_id],
        bloodType = this[DonationRecordsTable.blood_type].toString(),
        donationType = this[DonationRecordsTable.donation_type].toString(),
        donationPurpose = this[DonationRecordsTable.donation_purpose].toString(),
        amount = this[DonationRecordsTable.amount],
        volume = this[DonationRecordsTable.volume],
        donationTimestamp = this[DonationRecordsTable.donation_timestamp].toString(),
        isActive = this[DonationRecordsTable.is_active],
        isAuthenticated = this[DonationRecordsTable.is_authenticated],
        createdAt = this[DonationRecordsTable.createdAt].toString()
    )
}