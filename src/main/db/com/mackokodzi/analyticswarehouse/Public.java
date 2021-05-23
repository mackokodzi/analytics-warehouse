/*
 * This file is generated by jOOQ.
 */
package com.mackokodzi.analyticswarehouse;

import com.mackokodzi.analyticswarehouse.tables.CampaignStatistics;

import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.14.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.campaign_statistics</code>.
     */
    public final CampaignStatistics CAMPAIGN_STATISTICS = CampaignStatistics.CAMPAIGN_STATISTICS;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        return Arrays.<Sequence<?>>asList(
            Sequences.CAMPAIGN_STATISTICS_ID_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            CampaignStatistics.CAMPAIGN_STATISTICS);
    }
}