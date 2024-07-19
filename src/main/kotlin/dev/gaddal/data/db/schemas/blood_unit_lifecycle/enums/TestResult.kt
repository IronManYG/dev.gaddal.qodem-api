package dev.gaddal.data.db.schemas.blood_unit_lifecycle.enums

/**
 * Enum class representing the possible results of blood tests.
 */
enum class TestResult {
    POSITIVE,
    NEGATIVE,
    INCONCLUSIVE,
    BORDERLINE,
    LOW,
    NORMAL,
    HIGH,
    ERROR_PROCEDURAL,
    ERROR_SAMPLE,
    PENDING
}