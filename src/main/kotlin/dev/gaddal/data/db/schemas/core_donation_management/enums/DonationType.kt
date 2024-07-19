package dev.gaddal.data.db.schemas.core_donation_management.enums

/**
 * Represents the type of donation, such as whole blood, plasma, or platelets.
 * @property WHOLE_BLOOD The standard type of blood donation.
 * @property PLASMA A type of blood donation that focuses on plasma.
 * @property PLATELETS A type of blood donation that focuses on platelets.
 */
enum class DonationType {
    WHOLE_BLOOD,
    PLASMA,
    PLATELETS
}