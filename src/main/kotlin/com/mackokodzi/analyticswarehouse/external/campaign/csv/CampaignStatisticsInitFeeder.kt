package com.mackokodzi.analyticswarehouse.external.campaign.csv

import com.mackokodzi.analyticswarehouse.domain.campaign.CampaignName
import com.mackokodzi.analyticswarehouse.domain.campaign.CampaignStatistics
import com.mackokodzi.analyticswarehouse.domain.campaign.CampaignStatisticsRepository
import com.mackokodzi.analyticswarehouse.domain.campaign.Clicks
import com.mackokodzi.analyticswarehouse.domain.campaign.Datasource
import com.mackokodzi.analyticswarehouse.domain.campaign.Impressions
import com.mackokodzi.analyticswarehouse.domain.campaign.OperationDate
import mu.KLogging
import javax.annotation.PostConstruct

//@Component
class CampaignStatisticsInitFeeder(
    private val campaignStatisticsRepository: CampaignStatisticsRepository,
    private val campaignStatisticsDataExtractor: CampaignStatisticsDataExtractor
) {

    @PostConstruct
    fun feedDataFromCsv() {
        campaignStatisticsDataExtractor.extractCsvData()
            .let { it.map { row -> row.toDomain() } }
            .dropLast(15000) //TODO: postgre constraints on heroku server
            .also { logger.info { "Saving ${it.size} entries"} }
            .let { campaignStatisticsRepository.saveAll(it) }
            .also { logger.info { "Saved entries successfully" } }
    }

    fun CampaignStatisticsCsvReportRow.toDomain() =
        CampaignStatistics(
            datasource = Datasource(datasource),
            campaignName = CampaignName(campaignName),
            operationDate = OperationDate(date),
            clicks = Clicks(clicks),
            impressions = Impressions(impressions)
        )

    companion object : KLogging()
}