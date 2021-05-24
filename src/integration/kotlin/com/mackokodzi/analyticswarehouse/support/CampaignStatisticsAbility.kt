package com.mackokodzi.analyticswarehouse.support

import com.mackokodzi.analyticswarehouse.Tables.CAMPAIGN_STATISTICS
import org.jooq.DSLContext
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("integration")
class CampaignStatisticsAbility(
    private val dsl: DSLContext
) {

    fun getAll() = dsl.selectFrom(CAMPAIGN_STATISTICS).fetch()

}