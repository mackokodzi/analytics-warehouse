package com.mackokodzi.analyticswarehouse.mothers.domain

import com.mackokodzi.analyticswarehouse.domain.campaign.CampaignStatisticsCriteria
import com.mackokodzi.analyticswarehouse.domain.campaign.Group
import com.mackokodzi.analyticswarehouse.domain.campaign.Metric
import java.util.Date

object CampaignStatisticsCriteriaMother {
    fun build(
        metrics : List<Metric> = listOf(Metric.CLICKS, Metric.IMPRESSIONS, Metric.CTR),
        groups: List<Group> = emptyList(),
        datasources: List<String> = emptyList(),
        campaigns: List<String> = emptyList(),
        dateFrom: Date? = null,
        dateTo: Date? = null,
        sorts: List<String> = emptyList()
    ) = CampaignStatisticsCriteria(
        metrics = metrics,
        groups = groups,
        datasources = datasources,
        campaigns = campaigns,
        dateFrom = dateFrom,
        dateTo = dateTo,
        sorts = sorts
    )
}