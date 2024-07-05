package dev.gaddal.data.db.schemas.communities_and_campaigns

import dev.gaddal.data.db.schemas.communities_and_campaigns.CampaignInfoTable.createdAt
import dev.gaddal.data.db.schemas.communities_and_campaigns.CampaignInfoTable.description
import dev.gaddal.data.db.schemas.communities_and_campaigns.CampaignInfoTable.donation_center_id
import dev.gaddal.data.db.schemas.communities_and_campaigns.CampaignInfoTable.end_timestamp
import dev.gaddal.data.db.schemas.communities_and_campaigns.CampaignInfoTable.id
import dev.gaddal.data.db.schemas.communities_and_campaigns.CampaignInfoTable.image_url
import dev.gaddal.data.db.schemas.communities_and_campaigns.CampaignInfoTable.name
import dev.gaddal.data.db.schemas.communities_and_campaigns.CampaignInfoTable.start_timestamp
import dev.gaddal.data.db.schemas.communities_and_campaigns.CampaignInfoTable.target_goal
import dev.gaddal.data.db.schemas.communities_and_campaigns.CampaignInfoTable.theme
import dev.gaddal.data.db.schemas.core_donation_management.DonationCenterTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

/**
 * Represents the campaign_info table in a PostgresSQL database, storing core information and details about blood donation campaigns.
 * This table tracks each campaign's associated donation center, title, description, start and end times, and additional optional details like goals and themes.
 *
 * @property id Unique identifier for the campaign, automatically incremented.
 * @property donation_center_id Foreign key reference to the donation center running the campaign.
 * @property name Title of the campaign.
 * @property description Detailed explanation of the campaign's aims and goals.
 * @property start_timestamp Start date and time of the campaign.
 * @property end_timestamp End date and time of the campaign.
 * @property target_goal Optional desired outcome of the campaign, such as number of donations or volume of blood.
 * @property theme Optional slogan or central message for the campaign.
 * @property image_url Optional URL link to a banner, logo, or image representing the campaign.
 * @property createdAt Timestamp of the creation date of the campaign record.
 */
object CampaignInfoTable : Table("campaign_info") {
    val id = integer("id").autoIncrement()
    val donation_center_id = integer("donation_center_id").references(DonationCenterTable.id)
    val name = varchar("name", 255)
    val description = text("description")
    val start_timestamp = long("start_timestamp")  // Using long to store UNIX timestamp
    val end_timestamp = long("end_timestamp")  // Using long to store UNIX timestamp
    val target_goal = integer("target_goal").nullable()
    val theme = varchar("theme", 255).nullable()
    val image_url = varchar("image_url", 255).nullable()
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }

    override val primaryKey = PrimaryKey(id)
}
