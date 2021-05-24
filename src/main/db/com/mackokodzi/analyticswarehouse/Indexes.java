/*
 * This file is generated by jOOQ.
 */
package com.mackokodzi.analyticswarehouse;

import com.mackokodzi.analyticswarehouse.tables.CampaignStatistics;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

import javax.annotation.processing.Generated;


/**
 * A class modelling indexes of tables in public.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.14.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index IDX_UNIQUE_CAMPAIGN = Internal.createIndex(DSL.name("idx_unique_campaign"), CampaignStatistics.CAMPAIGN_STATISTICS, new OrderField[] { CampaignStatistics.CAMPAIGN_STATISTICS.DATASOURCE, CampaignStatistics.CAMPAIGN_STATISTICS.CAMPAIGN_NAME, CampaignStatistics.CAMPAIGN_STATISTICS.DATE }, true);
}