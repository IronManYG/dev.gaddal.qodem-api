package dev.gaddal.data.models.entities

import com.fasterxml.jackson.annotation.JsonInclude
import dev.gaddal.data.db.schemas.core_donation_management.UserNameTable
import dev.gaddal.data.db.schemas.core_donation_management.UserTable
import org.jetbrains.exposed.sql.ResultRow

/**
 * Represents aUser in the application.
 *
 * This data class is used to model the data from the `UserTable` in the database.
 * Each property in the class corresponds to a column in the `UserTable`.
 *
 * @see dev.gaddal.data.db.schemas.core_donation_management.UserTable
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class User(
    val id: Int,
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
    val createdAt: String
)

fun ResultRow?.toUser(): User? {
    return if (this == null) null
    else User(
        id = this[UserTable.id].value,
        firstName = this[UserNameTable.first_name],
        middleName = this[UserNameTable.middle_name],
        lastName = this[UserNameTable.last_name],
        birthDate = this[UserTable.birth_date].toString(),
        gender = this[UserTable.gender].toString(),
        postalCode = this[UserTable.postal_code],
        street = this[UserTable.street],
        city = this[UserTable.city],
        state = this[UserTable.state],
        country = this[UserTable.country],
        phoneNumber = this[UserTable.phone_number],
        email = this[UserTable.email],
        emergencyContact = this[UserTable.emergency_contact],
        imageUrl = this[UserTable.image_url],
        bloodType = this[UserTable.blood_type].toString(),
        weight = this[UserTable.weight],
        height = this[UserTable.height],
        eligibilityStatus = this[UserTable.eligibility_status],
        numberOfDonations = this[UserTable.number_of_donations],
        donationPoints = this[UserTable.donation_points],
        lastDonationDate = this[UserTable.last_donation_date]?.toString(),
        createdAt = this[UserTable.createdAt].toString()
    )
}
