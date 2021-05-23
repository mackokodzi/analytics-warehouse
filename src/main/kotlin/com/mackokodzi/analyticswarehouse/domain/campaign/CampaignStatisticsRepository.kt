package com.mackokodzi.analyticswarehouse.domain.campaign

interface CampaignStatisticsRepository {
    fun getStatistics(campaignStatisticsCriteria: CampaignStatisticsCriteria) : List<Any>
    fun saveAll(campaignStatisticsList: List<CampaignStatistics>)
}