package com.mackokodzi.analyticswarehouse.domain.campaign

interface CampaignStatisticsRepository {
    fun getStatistics(criteria: CampaignStatisticsCriteria) : List<Any>
    fun saveAll(campaignStatisticsList: List<CampaignStatistics>)
}