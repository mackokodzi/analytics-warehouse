package com.mackokodzi.analyticswarehouse.api.campaign

import com.mackokodzi.analyticswarehouse.BaseIntegrationTests
import com.mackokodzi.analyticswarehouse.external.db.campaign.JooqCampaignStatisticsRepository
import com.mackokodzi.analyticswarehouse.mothers.domain.CampaignStatisticsMother
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import java.time.Instant

class CampaignStatisticsEndpointTest(
    @Autowired private val jooqCampaignStatisticsRepository: JooqCampaignStatisticsRepository
) : BaseIntegrationTests() {

    @Test
    fun `should get total clicks`() {
        // given
        jooqCampaignStatisticsRepository.saveAll(listOf(
            CampaignStatisticsMother.build(clicks = 10),
            CampaignStatisticsMother.build(clicks = 5)
        ))

        // when
        val response = given()
            .get(localUrl("campaign-statistics?metrics=clicks"))

        // then
        response.then().statusCode(HttpStatus.OK.value())
            .body("[0].clicks", equalTo(15))
    }

    @Test
    fun `should get total clicks for a given Datasource for a given Date range`() {
        // given
        jooqCampaignStatisticsRepository.saveAll(listOf(
            CampaignStatisticsMother.build( //should not be counted
                datasource = "Google Ads",
                operationDate = Instant.parse("2020-01-02T00:00:00Z"),
                clicks = 10
            ),
            CampaignStatisticsMother.build(
                datasource = "Google Ads",
                operationDate = Instant.parse("2020-01-03T00:00:00Z"),
                clicks = 20
            ),
            CampaignStatisticsMother.build(
                datasource = "Google Ads",
                operationDate = Instant.parse("2020-01-04T00:00:00Z"),
                clicks = 35
            ),
            CampaignStatisticsMother.build( //should not be counted
                datasource = "Twitter Ads",
                operationDate = Instant.parse("2020-01-04T00:00:00Z"),
                clicks = 40
            )
        ))

        // when
        val response = given()
            .get(localUrl("campaign-statistics?metrics=clicks" +
                    "&groups=datasource" +
                    "&datasources=Google Ads" +
                    "&dateFrom=2020-01-03" +
                    "&dateTo=2020-01-04"))

        // then
        response.then().statusCode(HttpStatus.OK.value())
            .body("[0].clicks", equalTo(55))
            .body("[0].datasource", equalTo("Google Ads"))
    }

    @Test
    fun `should get Click-Through Rate (CTR) per Datasource and Campaign sorted by CTR in descending order`() {
        // given
        jooqCampaignStatisticsRepository.saveAll(listOf(
            CampaignStatisticsMother.build(
                datasource = "Google Ads",
                campaignName = "CampaignGoogle1",
                operationDate = Instant.parse("2020-01-02T00:00:00Z"),
                clicks = 10,
                impressions = 100
            ),
            CampaignStatisticsMother.build(
                datasource = "Google Ads",
                campaignName = "CampaignGoogle1",
                operationDate = Instant.parse("2020-01-03T00:00:00Z"),
                clicks = 20,
                impressions = 90
            ),
            CampaignStatisticsMother.build(
                datasource = "Google Ads",
                campaignName = "CampaignGoogle2",
                operationDate = Instant.parse("2020-01-03T00:00:00Z"),
                clicks = 10,
                impressions = 100
            ),
            CampaignStatisticsMother.build(
                datasource = "Google Ads",
                campaignName = "CampaignGoogle3",
                operationDate = Instant.parse("2020-01-03T00:00:00Z"),
                clicks = 20,
                impressions = 90
            ),
            CampaignStatisticsMother.build(
                datasource = "Twitter Ads",
                campaignName = "CampaignTwitter1",
                operationDate = Instant.parse("2020-01-04T00:00:00Z"),
                clicks = 35,
                impressions = 80
            ),
            CampaignStatisticsMother.build(
                datasource = "Twitter Ads",
                campaignName = "CampaignTwitter1",
                operationDate = Instant.parse("2020-01-05T00:00:00Z"),
                clicks = 40,
                impressions = 85
            )
        ))

        // when
        val response = given()
            .get(localUrl("campaign-statistics?metrics=ctr" +
                    "&groups=datasource,campaign" +
                    "&sorts=ctr.desc"))

        // then
        response.then().statusCode(HttpStatus.OK.value())
            .body("[0].datasource", equalTo("Twitter Ads"))
            .body("[0].campaign_name", equalTo("CampaignTwitter1"))
            .body("[0].ctr", equalTo(0.45454545454545454545f))
            .body("[1].datasource", equalTo("Google Ads"))
            .body("[1].campaign_name", equalTo("CampaignGoogle3"))
            .body("[1].ctr", equalTo(0.2222222222222222f))
            .body("[2].datasource", equalTo("Google Ads"))
            .body("[2].campaign_name", equalTo("CampaignGoogle1"))
            .body("[2].ctr", equalTo(0.15789473684210525f))
            .body("[3].datasource", equalTo("Google Ads"))
            .body("[3].campaign_name", equalTo("CampaignGoogle2"))
            .body("[3].ctr", equalTo(0.1f))
    }

    @Test
    fun `should get Impressions over time (daily) sorted by Date ascending`() {
        // given
        jooqCampaignStatisticsRepository.saveAll(listOf(
            CampaignStatisticsMother.build(
                datasource = "Google Ads",
                operationDate = Instant.parse("2020-01-02T00:00:00Z"),
                impressions = 100
            ),
            CampaignStatisticsMother.build(
                datasource = "Google Ads",
                operationDate = Instant.parse("2020-01-03T00:00:00Z"),
                impressions = 90
            ),
            CampaignStatisticsMother.build(
                datasource = "Twitter Ads",
                operationDate = Instant.parse("2020-01-02T00:00:00Z"),
                impressions = 100
            ),
            CampaignStatisticsMother.build(
                datasource = "Twitter Ads",
                operationDate = Instant.parse("2020-01-03T00:00:00Z"),
                clicks = 20,
                impressions = 90
            ),
            CampaignStatisticsMother.build(
                datasource = "Facebooks Ads",
                operationDate = Instant.parse("2020-01-04T00:00:00Z"),
                clicks = 20,
                impressions = 90
            )
        ))

        // when
        val response = given()
            .get(localUrl("campaign-statistics?metrics=impressions" +
                    "&groups=date" +
                    "&sorts=date"))

        // then
        response.then().statusCode(HttpStatus.OK.value())
            .body("[0].date", equalTo("2020-01-02T00:00:00Z"))
            .body("[0].impressions", equalTo(200))
            .body("[1].date", equalTo("2020-01-03T00:00:00Z"))
            .body("[1].impressions", equalTo(180))
            .body("[2].date", equalTo("2020-01-04T00:00:00Z"))
            .body("[2].impressions", equalTo(90))
    }
}