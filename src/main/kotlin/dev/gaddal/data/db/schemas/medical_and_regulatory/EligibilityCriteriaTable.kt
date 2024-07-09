package dev.gaddal.data.db.schemas.medical_and_regulatory

import dev.gaddal.data.db.schemas.medical_and_regulatory.EligibilityCriteriaTable.createdAt
import dev.gaddal.data.db.schemas.medical_and_regulatory.EligibilityCriteriaTable.criterion_description
import dev.gaddal.data.db.schemas.medical_and_regulatory.EligibilityCriteriaTable.deferral_duration
import dev.gaddal.data.db.schemas.medical_and_regulatory.EligibilityCriteriaTable.deferral_type
import dev.gaddal.data.db.schemas.medical_and_regulatory.EligibilityCriteriaTable.id
import dev.gaddal.data.db.schemas.medical_and_regulatory.enums.DeferralType
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

/**
 * Represents the eligibility_criteria table in a PostgresSQL database, defining rules based on medical factors to determine if a donor is eligible to donate blood.
 * This table includes descriptions of the criteria, the type of deferral (temporary or permanent), and the duration of the deferral if temporary.
 *
 * @property id Unique identifier for the eligibility criteria, automatically incremented.
 * @property criterion_description Detailed description of the eligibility rule.
 * @property deferral_type Type of deferral ('temporary', 'permanent').
 * @property deferral_duration Duration of the deferral period in days, applicable if the deferral is temporary.
 * @property createdAt Timestamp of the creation date of the eligibility criteria record.
 */
object EligibilityCriteriaTable : IntIdTable("eligibility_criteria") {
    val criterion_description = text("criterion_description")
    val deferral_type = enumerationByName("deferral_type", 50, DeferralType::class).default(DeferralType.TEMPORARY)
    val deferral_duration = integer("deferral_duration")  // Considered as days
    val createdAt = timestampWithTimeZone("created_at").defaultExpression(CurrentTimestamp())
}