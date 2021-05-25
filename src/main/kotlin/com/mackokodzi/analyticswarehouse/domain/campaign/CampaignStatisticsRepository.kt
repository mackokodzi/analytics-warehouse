package com.mackokodzi.analyticswarehouse.domain.campaign

interface CampaignStatisticsRepository {
    fun count() : Int
    fun getStatistics(criteria: CampaignStatisticsCriteria) : List<Any>
    fun saveAll(campaignStatisticsList: List<CampaignStatistics>)
}