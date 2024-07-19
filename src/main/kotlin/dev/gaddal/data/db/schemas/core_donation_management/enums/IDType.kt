package dev.gaddal.data.db.schemas.core_donation_management.enums

/**
 * Represents the types of identification documents that a user can provide.
 *
 * This enum class is used to specify the type of identification document that a user has provided.
 * It can be used in various parts of the application where user identification is required.
 *
 * @property Passport Represents a passport document.
 * @property NationalID Represents a national identification card.
 * @property DriverLicense Represents a driver's license.
 * @property UNKNOWN Represents an unknown identification document.
 */
enum class IDType {
    /**
     * Represents a passport document.
     *
     * A passport is a travel document, usually issued by a country's government to its citizens,
     * that certifies the identity and nationality of its holder primarily for the purpose of international travel.
     */
    Passport,

    /**
     * Represents a national identification card.
     *
     * A national identification card is an identity document issued by a government to its citizens
     * for the purpose of verifying the holder's identity within the country.
     */
    NationalID,

    /**
     * Represents a driver's license.
     *
     * A driver's license is an official document that permits an individual to operate one or more types of motorized vehicles.
     */
    DriverLicense,

    /**
     * Represents an unknown identification document.
     *
     * This is used to prevent application crashes due to invalid data.
     */
    UNKNOWN
}