package dev.gaddal.data.db.schemas.core_donation_management

import dev.gaddal.data.db.schemas.blood_unit_lifecycle.enums.BloodType
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.birth_date
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.blood_type
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.city
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.country
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.createdAt
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.donation_points
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.eligibility_status
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.email
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.emergency_contact
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.gender
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.height
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.id
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.image_url
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.last_donation_date
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.name_id
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.number_of_donations
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.phone_number
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.postal_code
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.state
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.street
import dev.gaddal.data.db.schemas.core_donation_management.UserTable.weight
import dev.gaddal.data.db.schemas.core_donation_management.enums.Gender
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

/**
 * Represents the user table in a PostgresSQL database, capturing essential information about donors.
 * This table includes demographics, medical history, contact information, and donation-related data.
 *
 * @property id Unique identifier for the user, automatically incremented.
 * @property name_id Foreign key reference to the names table, identifying the user's name entry.
 * @property birth_date User's birthdate.
 * @property gender User's gender.
 * @property postal_code Optional postal code of the user's address.
 * @property street Optional street name of the user's address.
 * @property city City of the user's residence.
 * @property state State of the user's residence.
 * @property country Country of the user's residence.
 * @property phone_number User's phone number.
 * @property email User's email address.
 * @property emergency_contact Optional emergency contact number.
 * @property image_url Optional URL or name of the user's profile image.
 * @property blood_type User's blood type.
 * @property weight User's weight in kilograms.
 * @property height Optional height of the user in centimeters.
 * @property eligibility_status Indicates if the user is currently eligible to donate; default is true.
 * @property number_of_donations Total number of times the user has donated.
 * @property donation_points Points earned by the user for donations.
 * @property last_donation_date Optional date of the user's last donation.
 * @property createdAt Timestamp of the creation date of the user record.
 */
object UserTable : IntIdTable("users") {
    val name_id = integer("name_id").references(UserNameTable.id)
    val birth_date = datetime("birth_date")
    val gender = enumerationByName("gender", 20, Gender::class)
    val postal_code = varchar("postal_code", 20).nullable()
    val street = varchar("street", 255).nullable()
    val city = varchar("city", 255)
    val state = varchar("state", 255)
    val country = varchar("country", 255)
    val phone_number = varchar("phone_number", 50)
    val email = varchar("email", 255)
    val emergency_contact = varchar("emergency_contact", 50).nullable()
    val image_url = varchar("image_url", 255).nullable()
    val blood_type = enumerationByName("blood_type", 15, BloodType::class)
    val weight = float("weight")
    val height = float("height").nullable()
    val eligibility_status = bool("eligibility_status").clientDefault { true }
    val number_of_donations = integer("number_of_donations").clientDefault { 0 }
    val donation_points = integer("donation_points").clientDefault { 0 }
    val last_donation_date = timestampWithTimeZone("last_donation_date").nullable()
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestamp())
}
