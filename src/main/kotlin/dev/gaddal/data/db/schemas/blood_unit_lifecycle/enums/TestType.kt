package dev.gaddal.data.db.schemas.blood_unit_lifecycle.enums

/**
 * Enum class representing the types of tests that can be performed on donated blood units.
 */
enum class TestType {
    BLOOD_TYPING,         // ABO and Rh blood typing
    INFECTIOUS_DISEASE,   // Screening for various infectious diseases (e.g., HIV, Hepatitis B and C, Syphilis)
    ANTIBODY_SCREEN,      // Screening for unexpected antibodies
    HEMATOLOGY,           // Tests for blood components (e.g., Hemoglobin, Platelet count)
    NUCLEIC_ACID,         // Nucleic Acid Testing (NAT) for viral detection
    PARASITIC,            // Screening for parasitic diseases (e.g., Malaria, Chagas disease)
    BACTERIAL,            // Testing for bacterial contamination
    COMPATIBILITY,        // Crossmatching and other compatibility tests
    SPECIAL_TESTS,        // Special tests like CMV, West Nile Virus for specific recipients or regions
    OTHER                 // Any other tests not covered by the above categories
}