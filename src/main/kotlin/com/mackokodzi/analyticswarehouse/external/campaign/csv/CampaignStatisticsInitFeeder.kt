package com.mackokodzi.analyticswarehouse.external.campaign.csv

import com.mackokodzi.analyticswarehouse.domain.campaign.CampaignName
import com.mackokodzi.analyticswarehouse.domain.campaign.CampaignStatistics
import com.mackokodzi.analyticswarehouse.domain.campaign.CampaignStatisticsRepository
import com.mackokodzi.analyticswarehouse.domain.campaign.Clicks
import com.mackokodzi.analyticswarehouse.domain.campaign.Datasource
import com.mackokodzi.analyticswarehouse.domain.campaign.Impressions
import com.mackokodzi.analyticswarehouse.domain.campaign.OperationDate
import kotlinx.coroutines.runBlocking
import mu.KLogging
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.io.File
import javax.annotation.PostConstruct

@Profile("!integration")
@Component
class CampaignStatisticsInitFeeder(
    private val campaignStatisticsRepository: CampaignStatisticsRepository,
    private val campaignStatisticsDataExtractor: CampaignStatisticsDataExtractor,
    private val campaignStatisticsDataClient: CampaignStatisticsDataClient
) {

   //TODO: add tests

    @PostConstruct
    fun feedDataFromCsv() {
        if (campaignStatisticsRepository.count() == 0) {
            val file = File.createTempFile("files", "index")
            runBlocking { campaignStatisticsDataClient.downloadCsvData(file) }
            campaignStatisticsDataExtractor.extractCsvData(file)
                .let { it.map { row -> row.toDomain() } }
                .subList(0, 8000) //TODO: postgre constraints on heroku server
                .also { logger.info { "Saving ${it.size} entries" } }
                .let { campaignStatisticsRepository.saveAll(it) }
                .also { logger.info { "Saved entries successfully" } }
        } else {
            logger.info { "Entries already in db" }
        }
    }

    private fun CampaignStatisticsCsvReportRow.toDomain() =
        CampaignStatistics(
            datasource = Datasource(datasource),
            campaignName = CampaignName(campaignName),
            operationDate = OperationDate(date),
            clicks = Clicks(clicks),
            impressions = Impressions(impressions)
        )

    companion object : KLogging()
}