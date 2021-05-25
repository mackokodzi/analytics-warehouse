package com.mackokodzi.analyticswarehouse.domain.campaign

import mu.KLogging
import org.springframework.stereotype.Component
import java.util.Date

@Component
class CampaignStatisticsRetriever(
    private val campaignStatisticsRepository: CampaignStatisticsRepository
) {
    fun get(criteria: CampaignStatisticsCriteria) =
        logger.info { "Getting campaign statistics for criteria: $criteria" }
            .let { campaignStatisticsRepository.getStatistics(criteria) }

    companion object : KLogging()
}

data class CampaignStatisticsCriteria(
    val metrics: List<Metric>,
    val groups: List<Group>,
    val datasources: List<String>,
    val campaigns: List<String>,
    val dateFrom: Date?,
    val dateTo: Date?,
    val sorts: List<String>
)

enum class Metric {
    /**
     * Total clicks
     */
    CLICKS,
    /**
     * Total impressions
     */
    IMPRESSIONS,
    /**
     * Click per impressions rate
     */
    CTR
}

enum class Group {
    DATASOURCE,
    CAMPAIGN,
    DATE
}