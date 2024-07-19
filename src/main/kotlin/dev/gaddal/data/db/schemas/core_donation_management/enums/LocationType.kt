package dev.gaddal.data.db.schemas.core_donation_management.enums

/**
 * Representing the different types of donation center locations.
 * @property MOBILE Represents a mobile donation center that can move from place to place.
 * @property FIXED Represents a fixed donation center with a permanent location.
 * @property OTHER Represents any other location types that do not fall under the predefined categories.
 */
enum class LocationType {
    MOBILE,
    FIXED,
    OTHER
}
