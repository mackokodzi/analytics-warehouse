package com.mackokodzi.analyticswarehouse.api.campaign

import com.mackokodzi.analyticswarehouse.domain.campaign.CampaignStatisticsCriteria
import com.mackokodzi.analyticswarehouse.domain.campaign.CampaignStatisticsRetriever
import com.mackokodzi.analyticswarehouse.domain.campaign.Group
import com.mackokodzi.analyticswarehouse.domain.campaign.Metric
import mu.KLogging
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

typealias Sort = String

@RestController
@RequestMapping("/campaign-statistics")
class CampaignStatisticsEndpoint(
    private val campaignStatisticsRetriever: CampaignStatisticsRetriever
) {

    @GetMapping(produces = ["application/json"])
    fun get(
        /**
         * Metric(s) to be returned.
         * Allowed are:
         * clicks - total clicks
         * impressions - total impressions
         * ctr - Click-Through Rate
         */
        @RequestParam(required = false, defaultValue = "clicks, impressions, ctr") metrics: List<Metric>,
        /**
         * Dimensions to be grouped.
         * Allowed are: datasource, campaign, date.
         */
        @RequestParam(required = false, defaultValue = "") groups: List<Group>,
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
        dateFrom: LocalDate?,
        /**
         * "Date to" to be filtered
         */
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        dateTo: LocalDate?,
        /**
         * Fields to be sorted
         */
        @RequestParam(required = false, defaultValue = "") sorts: List<Sort>
    ) =
        RequestSortValidator.validate(sorts, metrics, groups)
            .let {
                campaignStatisticsRetriever
                    .get(
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
            }

    companion object : KLogging()
}

object RequestSortValidator {
    fun validate(sorts: List<Sort>, metrics: List<Metric>, groups: List<Group>) {
        if (sorts.isNotEmpty()) {
            sorts.all {
                val sort = it.substringBefore(".").toUpperCase()
                val metricFromSort = try { Metric.valueOf(sort) } catch (e: Exception) { false }
                val groupFromSort = try { Group.valueOf(sort) } catch (e: Exception) { false }
                groups.contains(groupFromSort) || metrics.contains(metricFromSort)
            }
                .takeIf { !it }
                ?.let { throw SortValidationException() }
        }
    }
}

class SortValidationException : IllegalArgumentException()