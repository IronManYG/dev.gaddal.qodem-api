package dev.gaddal.data.db.schemas.core_donation_management.enums

/**
 * Represents the purpose of the donation, such as directed or voluntary.
 * @property DIRECTED A donation made for a specific person or purpose.
 * @property VOLUNTARY A donation made without any specific direction.
 */
enum class DonationPurpose {
    DIRECTED,
    VOLUNTARY
}