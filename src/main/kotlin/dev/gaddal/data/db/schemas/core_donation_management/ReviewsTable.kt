package dev.gaddal.data.db.schemas.core_donation_management

import dev.gaddal.data.db.schemas.core_donation_management.ReviewsTable.comment
import dev.gaddal.data.db.schemas.core_donation_management.ReviewsTable.createdAt
import dev.gaddal.data.db.schemas.core_donation_management.ReviewsTable.datetime
import dev.gaddal.data.db.schemas.core_donation_management.ReviewsTable.donation_center_id
import dev.gaddal.data.db.schemas.core_donation_management.ReviewsTable.donor_id
import dev.gaddal.data.db.schemas.core_donation_management.ReviewsTable.id
import dev.gaddal.data.db.schemas.core_donation_management.ReviewsTable.is_anonymous
import dev.gaddal.data.db.schemas.core_donation_management.ReviewsTable.rating
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

/**
 * Represents the reviews table in a PostgresSQL database, storing donor ratings and comments on their donation experience.
 * This table includes information about the donor, the associated donation center, and details about the review such as rating and anonymity.
 *
 * @property id Unique identifier for the review, automatically incremented.
 * @property donor_id Foreign key reference to the donor who gave the review.
 * @property donation_center_id Foreign key reference to the associated donation center.
 * @property is_anonymous Indicates if the review is posted anonymously, defaulting to false.
 * @property rating Rating given by the donor, typically out of 5.
 * @property comment Optional textual comment provided by the donor.
 * @property datetime Date and time when the review was posted.
 * @property createdAt Timestamp of the creation date of the review record.
 */
object ReviewsTable : Table("reviews") {
    val id = integer("id").autoIncrement()
    val donor_id = integer("donor_id").references(UserTable.id)
    val donation_center_id = integer("donation_center_id").references(DonationCenterTable.id)
    val is_anonymous = bool("is_anonymous").clientDefault { false }
    val rating = integer("rating").check { it greaterEq 0; it lessEq 5 }
    val comment = varchar("comment", 1000).nullable()
    val datetime = datetime("date")
    val createdAt = datetime("created_at").defaultExpression(CurrentTimestamp())

    override val primaryKey = PrimaryKey(id)
}
