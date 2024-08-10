package dev.gaddal.data.models.params

/**
 * Data class that encapsulates all the necessary parameters for managing users in the system.
 * This class is used primarily for passing user data between the client and the server
 * during creation or update operations.
 *
 * @see dev.gaddal.data.db.schemas.core_donation_management.UserTable
 */
data class UserParams(
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val birthDate: String,
    val gender: String,
    val postalCode: String?,
    val street: String?,
    val city: String,
    val state: String,
    val country: String,
    val phoneNumber: String,
    val email: String,
    val emergencyContact: String?,
    val imageUrl: String?,
    val bloodType: String,
    val weight: Float,
    val height: Float?,
    val eligibilityStatus: Boolean,
    val numberOfDonations: Int,
    val donationPoints: Int,
    val lastDonationDate: String?,
)
