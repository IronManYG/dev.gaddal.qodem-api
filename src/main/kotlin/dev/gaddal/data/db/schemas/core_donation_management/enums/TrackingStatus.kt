package dev.gaddal.data.db.schemas.core_donation_management.enums

/**
 * Enum class representing the possible statuses of a blood donation throughout its journey.
 * 
 * @property COLLECTED Blood has been collected from the donor.
 * @property PROCESSED Blood has been processed and prepared for testing.
 * @property TESTS_PASSED Blood has passed all required tests.
 * @property READY_FOR_DISPATCH Blood is ready to be dispatched to a hospital.
 * @property DISPATCHED Blood has been dispatched to a hospital.
 * @property TRANSFUSED Blood has been successfully transfused to a patient.
 * @property DISCARDED Blood has been discarded due to various reasons.
 */
enum class TrackingStatus {
    COLLECTED,
    PROCESSED,
    TESTS_PASSED,
    READY_FOR_DISPATCH,
    DISPATCHED,
    TRANSFUSED,
    DISCARDED
}