package com.mackokodzi.analyticswarehouse.external.campaign.db

import com.mackokodzi.analyticswarehouse.BaseIntegrationTests
import com.mackokodzi.analyticswarehouse.domain.campaign.Metric
import com.mackokodzi.analyticswarehouse.external.db.campaign.JooqCampaignStatisticsRepository
import com.mackokodzi.analyticswarehouse.mothers.domain.CampaignStatisticsCriteriaMother
import com.mackokodzi.analyticswarehouse.mothers.domain.CampaignStatisticsMother
import com.mackokodzi.analyticswarehouse.support.CampaignStatisticsAbility
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import java.math.BigDecimal
import java.time.Instant

class JooqCampaignStatisticsRepositoryTest(
    @Autowired private val jooqCampaignStatisticsRepository: JooqCampaignStatisticsRepository,
    @Autowired private val campaignStatisticsAbility: CampaignStatisticsAbility
) : BaseIntegrationTests() {

    @Test
    fun `should get campaign statistics for total clicks`() {
        // given
        val campaignStatisticsList = listOf(
            CampaignStatisticsMother.build(clicks = 10),
            CampaignStatisticsMother.build(clicks = 15)
        )
        jooqCampaignStatisticsRepository.saveAll(campaignStatisticsList)

        val criteria = CampaignStatisticsCriteriaMother.build(
            metrics = listOf(Metric.CLICKS)
        )

        // when
        val result = jooqCampaignStatisticsRepository.getStatistics(criteria)

        // then
        assert(result.size == 1)
        assert((result[0] as Map<String, BigDecimal>)["clicks"] == BigDecimal(25))
    }

    @Test
    fun `should get campaign statistics for total impressions`() {
        // given
        val campaignStatisticsList = listOf(
            CampaignStatisticsMother.build(impressions = 10),
            CampaignStatisticsMother.build(impressions = 15)
        )
        jooqCampaignStatisticsRepository.saveAll(campaignStatisticsList)

        val criteria = CampaignStatisticsCriteriaMother.build(
            metrics = listOf(Metric.IMPRESSIONS)
        )

        // when
        val result = jooqCampaignStatisticsRepository.getStatistics(criteria)

        // then
        assert(result.size == 1)
        assert((result[0] as Map<String, BigDecimal>)["impressions"] == BigDecimal(25))
    }

    @Test
    fun `should get campaign statistics for total ctr`() {
        // given
        val campaignStatisticsList = listOf(
            CampaignStatisticsMother.build(clicks = 10, impressions = 20),
            CampaignStatisticsMother.build(clicks = 10, impressions = 15)
        )
        jooqCampaignStatisticsRepository.saveAll(campaignStatisticsList)

        val criteria = CampaignStatisticsCriteriaMother.build(
            metrics = listOf(Metric.CTR)
        )

        // when
        val result = jooqCampaignStatisticsRepository.getStatistics(criteria)

        // then
        assert(result.size == 1)
        assert((result[0] as Map<String, BigDecimal>)["ctr"] == BigDecimal("0.57142857142857142857"))
    }

    //TODO: add more tests for retrieving campaign statistics

    @Test
    fun `should save some campaign statistics`() {
        // given
        val campaignStatisticsList = listOf(
            CampaignStatisticsMother.build(),
            CampaignStatisticsMother.build()
        )

        // when
        jooqCampaignStatisticsRepository.saveAll(campaignStatisticsList)

        // then
        val savedEntites = campaignStatisticsAbility.getAll()
        assert(savedEntites.size == 2)
        assert(savedEntites.first().datasource == campaignStatisticsList.first().datasource.value)
        assert(savedEntites.first().campaignName == campaignStatisticsList.first().campaignName.value)
        assert(savedEntites.first().date == campaignStatisticsList.first().operationDate.value)
        assert(savedEntites.first().clicks == campaignStatisticsList.first().clicks.value)
        assert(savedEntites.first().impressions == campaignStatisticsList.first().impressions.value)
    }

    @Test
    fun `should throw exception while storing unique values`() {
        // given
        val campaignStatisticsList = listOf(
            CampaignStatisticsMother.build(
                datasource = "Google Ads",
                campaignName = "Google Campaign",
                operationDate = Instant.parse("2020-01-01T00:00:00Z")
            ),
            CampaignStatisticsMother.build(
                datasource = "Google Ads",
                campaignName = "Google Campaign",
                operationDate = Instant.parse("2020-01-01T00:00:00Z")
            )
        )

        // when then
        assertThrows<DataIntegrityViolationException> { jooqCampaignStatisticsRepository.saveAll(campaignStatisticsList) }
    }
}