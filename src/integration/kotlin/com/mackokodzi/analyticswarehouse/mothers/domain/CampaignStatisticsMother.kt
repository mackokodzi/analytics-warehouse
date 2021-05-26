package com.mackokodzi.analyticswarehouse.mothers.domain

import com.mackokodzi.analyticswarehouse.domain.campaign.CampaignName
import com.mackokodzi.analyticswarehouse.domain.campaign.CampaignStatistics
import com.mackokodzi.analyticswarehouse.domain.campaign.Clicks
import com.mackokodzi.analyticswarehouse.domain.campaign.Datasource
import com.mackokodzi.analyticswarehouse.domain.campaign.Impressions
import com.mackokodzi.analyticswarehouse.domain.campaign.OperationDate
import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import java.time.LocalDate
import kotlin.math.absoluteValue
import kotlin.random.Random

object CampaignStatisticsMother {
    fun build(
        datasource: String = randomAlphanumeric(6),
        campaignName: String = randomAlphanumeric(6),
        operationDate: LocalDate = LocalDate.parse("2020-08-19"),
        clicks: Int = Random.nextInt().absoluteValue,
        impressions: Int = Random.nextInt().absoluteValue
    ) = CampaignStatistics(
        datasource = Datasource(datasource),
        campaignName = CampaignName(campaignName),
        operationDate = OperationDate(operationDate),
        clicks = Clicks(clicks),
        impressions = Impressions(impressions)
    )
}