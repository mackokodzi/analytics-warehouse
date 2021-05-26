package com.mackokodzi.analyticswarehouse.external.db.campaign

import com.mackokodzi.analyticswarehouse.domain.campaign.CampaignStatistics
import com.mackokodzi.analyticswarehouse.domain.campaign.CampaignStatisticsCriteria
import com.mackokodzi.analyticswarehouse.domain.campaign.CampaignStatisticsRepository
import com.mackokodzi.analyticswarehouse.domain.campaign.Group
import com.mackokodzi.analyticswarehouse.domain.campaign.Metric
import com.mackokodzi.analyticswarehouse.tables.CampaignStatistics.CAMPAIGN_STATISTICS
import com.mackokodzi.analyticswarehouse.tables.records.CampaignStatisticsRecord
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.GroupField
import org.jooq.SortField
import org.jooq.impl.DSL.cast
import org.jooq.impl.DSL.sum
import org.jooq.impl.SQLDataType
import org.springframework.stereotype.Repository

@Repository
class JooqCampaignStatisticsRepository(
    private val dsl: DSLContext
) : CampaignStatisticsRepository {
    override fun count(): Int =
        dsl.fetchCount(CAMPAIGN_STATISTICS)

    override fun getStatistics(criteria: CampaignStatisticsCriteria): List<Any> {
        val selectFields: MutableList<Field<*>> = arrayListOf()
        val groupByFields: MutableList<GroupField> = arrayListOf()
        val sortFields: MutableList<SortField<*>> = arrayListOf()
        val whereConditions: MutableList<Condition> = arrayListOf()

        if (criteria.groups.contains(Group.DATASOURCE)) {
            val datasource = CAMPAIGN_STATISTICS.DATASOURCE
            selectFields.add(datasource)
            groupByFields.add(datasource)
        }
        if (criteria.groups.contains(Group.CAMPAIGN)) {
            selectFields.add(CAMPAIGN_STATISTICS.CAMPAIGN_NAME)
            groupByFields.add(CAMPAIGN_STATISTICS.CAMPAIGN_NAME)
        }
        if (criteria.groups.contains(Group.DATE)) {
            selectFields.add(CAMPAIGN_STATISTICS.DATE)
            groupByFields.add(CAMPAIGN_STATISTICS.DATE)
        }

        if (criteria.metrics.contains(Metric.CLICKS)) {
            val clicks = sum(CAMPAIGN_STATISTICS.CLICKS).`as`("clicks")
            selectFields.add(clicks)
        }
        if (criteria.metrics.contains(Metric.IMPRESSIONS)) {
            selectFields.add(sum(CAMPAIGN_STATISTICS.IMPRESSIONS).`as`("impressions"))
        }
        if (criteria.metrics.contains(Metric.CTR)) {
            selectFields.add(
                cast(
                    sum(CAMPAIGN_STATISTICS.CLICKS),
                    SQLDataType.NUMERIC
                ).divide(cast(sum(CAMPAIGN_STATISTICS.IMPRESSIONS), SQLDataType.NUMERIC)).`as`("ctr")
            )
        }

        if (criteria.dateFrom != null) {
            whereConditions.add(CAMPAIGN_STATISTICS.DATE.greaterOrEqual(criteria.dateFrom))
        }
        if (criteria.dateTo != null) {
            whereConditions.add(CAMPAIGN_STATISTICS.DATE.lessOrEqual(criteria.dateTo))
        }
        if (!criteria.datasources.isNullOrEmpty()) {
            criteria.datasources.forEach {
                whereConditions.add(CAMPAIGN_STATISTICS.DATASOURCE.eq(it))
            }
        }
        if (!criteria.campaigns.isNullOrEmpty()) {
            criteria.campaigns.forEach {
                whereConditions.add(CAMPAIGN_STATISTICS.CAMPAIGN_NAME.eq(it))
            }
        }

        if (!criteria.sorts.isNullOrEmpty()) {
            criteria.sorts.forEach {
                if (SORTS.contains(it.substringBefore("."))) {
                    val field = FIELDS_MAP[it.substringBefore(".")]
                    if (it.contains(".desc")) {
                        sortFields.add(field!!.desc())
                    } else {
                        sortFields.add(field!!.asc())
                    }
                }
            }
        }

        return dsl.select(selectFields)
            .from(CAMPAIGN_STATISTICS)
            .where(whereConditions)
            .groupBy(groupByFields)
            .orderBy(sortFields)
            .fetch()
            .intoMaps()
    }

    override fun saveAll(campaignStatisticsList: List<CampaignStatistics>) {
        dsl.batchInsert(campaignStatisticsList.map { it.toRecord() }).execute()
    }

    private fun CampaignStatistics.toRecord(): CampaignStatisticsRecord =
        let { campStats ->
            dsl.newRecord(CAMPAIGN_STATISTICS).apply {
                this.datasource = campStats.datasource.value
                this.campaignName = campStats.campaignName.value
                this.date = campStats.operationDate.value
                this.clicks = campStats.clicks.value
                this.impressions = campStats.impressions.value
            }
        }

    companion object {
        val SORTS = listOf("datasource", "campaign", "date", "clicks", "impressions", "ctr")
        val FIELDS_MAP = mapOf(
            "datasource" to CAMPAIGN_STATISTICS.DATASOURCE,
            "campaign" to CAMPAIGN_STATISTICS.CAMPAIGN_NAME,
            "date" to CAMPAIGN_STATISTICS.DATE,
            "clicks" to sum(CAMPAIGN_STATISTICS.CLICKS).`as`("clicks"),
            "impressions" to sum(CAMPAIGN_STATISTICS.IMPRESSIONS).`as`("impressions"),
            "ctr" to cast(
                sum(CAMPAIGN_STATISTICS.CLICKS),
                SQLDataType.DOUBLE
            ).divide(cast(sum(CAMPAIGN_STATISTICS.IMPRESSIONS), SQLDataType.DOUBLE)).`as`("ctr")
        )
    }
}


