package com.mackokodzi.analyticswarehouse.api.campaign

import com.mackokodzi.analyticswarehouse.domain.campaign.CampaignStatisticsCriteria
import com.mackokodzi.analyticswarehouse.domain.campaign.CampaignStatisticsRetriever
import mu.KLogging
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Date

@RestController
@RequestMapping("/campaign-statistics")
class CampaignStatisticsEndpoint(
    private val campaignStatisticsRetriever: CampaignStatisticsRetriever
) {

    @GetMapping(produces = ["application/json"])
    fun get(
        /**
         * Metric(s) to be returned.
         * Allowed are: clicks, impressions, ctr
         */
        @RequestParam(required = false, defaultValue = "clicks, impressions, ctr") metrics: List<String>,
        /**
         * Dimensions to be grouped.
         * Allowed are: datasource, campaign, date.
         */
        @RequestParam(required = false, defaultValue = "") groups: List<String>,
        /**
         * Datasources to be filtered
         */
        @RequestParam(required = false, defaultValue = "") datasources: List<String>,
        /**
         * Campaign names to be filtered
         */
        @RequestParam(required = false, defaultValue = "") campaigns: List<String>,
        /**
         * "Date from" to be filtered
         */
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        dateFrom: Date?,
        /**
         * "Date to" to be filtered
         */
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        dateTo: Date?,
        /**
         * Fields to be sorted
         */
        @RequestParam(required = false, defaultValue = "") sorts: List<String>
    ) = campaignStatisticsRetriever.get(
        CampaignStatisticsCriteria(
            metrics = metrics,
            groups = groups,
            datasources = datasources,
            campaigns = campaigns,
            dateFrom = dateFrom,
            dateTo = dateTo,
            sorts = sorts
        )
    ).also { logger.info { "Retrieved campaign statistics with result: $it" } }

    companion object : KLogging()
}