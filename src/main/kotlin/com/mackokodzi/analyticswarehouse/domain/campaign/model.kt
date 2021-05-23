package com.mackokodzi.analyticswarehouse.domain.campaign

import java.time.Instant

data class CampaignStatistics(
    val datasource: Datasource,
    val campaignName: CampaignName,
    val operationDate: OperationDate,
    val clicks: Clicks,
    val impressions: Impressions
)

data class Datasource(val value: String)
data class CampaignName(val value: String)
data class OperationDate(val value: Instant)
data class Clicks(val value: Int)
data class Impressions(val value: Int)
