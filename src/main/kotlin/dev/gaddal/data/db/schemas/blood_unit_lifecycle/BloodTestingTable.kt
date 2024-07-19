package dev.gaddal.data.db.schemas.blood_unit_lifecycle

import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodTestingTable.createdAt
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodTestingTable.donation_tracking_id
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodTestingTable.id
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodTestingTable.test_datetime
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodTestingTable.test_result
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.BloodTestingTable.test_type
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.enums.TestResult
import dev.gaddal.data.db.schemas.blood_unit_lifecycle.enums.TestType
import dev.gaddal.data.db.schemas.core_donation_management.DonationTrackingTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

/**
 * Represents the blood_testing table in a PostgresSQL database, storing the results of various tests performed on each donated blood unit.
 * This table tracks the type of tests, their results, and the date and time they were performed, crucial for ensuring the safety of the blood supply.
 *
 * @property id Unique identifier for the blood test record, automatically incremented.
 * @property donation_tracking_id Foreign key reference to the associated donation tracking record.
 * @property test_type Type of test performed (e.g., 'HIV', 'Hepatitis B', 'CMV').
 * @property test_result Outcome of the test ('positive', 'negative', 'inconclusive').
 * @property test_datetime Timestamp for when the test was performed.
 * @property createdAt Timestamp of the creation date of the blood testing record.
 */
object BloodTestingTable : IntIdTable("blood_testing") {
    val donation_tracking_id = integer("donation_tracking_id").references(DonationTrackingTable.id)
    val test_type = enumerationByName("test_type", 50, TestType::class)
    val test_result = enumerationByName("test_result", 50, TestResult::class)
    val test_datetime = timestampWithTimeZone("test_datetime")
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestamp())
}