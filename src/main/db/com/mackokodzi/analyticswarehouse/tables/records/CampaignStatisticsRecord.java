/*
 * This file is generated by jOOQ.
 */
package com.mackokodzi.analyticswarehouse.tables.records;

import com.mackokodzi.analyticswarehouse.tables.CampaignStatistics;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;

import java.sql.Date;

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
public class CampaignStatisticsRecord extends UpdatableRecordImpl<CampaignStatisticsRecord> implements Record6<Integer, String, String, Date, Integer, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.campaign_statistics.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.campaign_statistics.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.campaign_statistics.datasource</code>.
     */
    public void setDatasource(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.campaign_statistics.datasource</code>.
     */
    public String getDatasource() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.campaign_statistics.campaign_name</code>.
     */
    public void setCampaignName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.campaign_statistics.campaign_name</code>.
     */
    public String getCampaignName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.campaign_statistics.date</code>.
     */
    public void setDate(Date value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.campaign_statistics.date</code>.
     */
    public Date getDate() {
        return (Date) get(3);
    }

    /**
     * Setter for <code>public.campaign_statistics.clicks</code>.
     */
    public void setClicks(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.campaign_statistics.clicks</code>.
     */
    public Integer getClicks() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>public.campaign_statistics.impressions</code>.
     */
    public void setImpressions(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.campaign_statistics.impressions</code>.
     */
    public Integer getImpressions() {
        return (Integer) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Integer, String, String, Date, Integer, Integer> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Integer, String, String, Date, Integer, Integer> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return CampaignStatistics.CAMPAIGN_STATISTICS.ID;
    }

    @Override
    public Field<String> field2() {
        return CampaignStatistics.CAMPAIGN_STATISTICS.DATASOURCE;
    }

    @Override
    public Field<String> field3() {
        return CampaignStatistics.CAMPAIGN_STATISTICS.CAMPAIGN_NAME;
    }

    @Override
    public Field<Date> field4() {
        return CampaignStatistics.CAMPAIGN_STATISTICS.DATE;
    }

    @Override
    public Field<Integer> field5() {
        return CampaignStatistics.CAMPAIGN_STATISTICS.CLICKS;
    }

    @Override
    public Field<Integer> field6() {
        return CampaignStatistics.CAMPAIGN_STATISTICS.IMPRESSIONS;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getDatasource();
    }

    @Override
    public String component3() {
        return getCampaignName();
    }

    @Override
    public Date component4() {
        return getDate();
    }

    @Override
    public Integer component5() {
        return getClicks();
    }

    @Override
    public Integer component6() {
        return getImpressions();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getDatasource();
    }

    @Override
    public String value3() {
        return getCampaignName();
    }

    @Override
    public Date value4() {
        return getDate();
    }

    @Override
    public Integer value5() {
        return getClicks();
    }

    @Override
    public Integer value6() {
        return getImpressions();
    }

    @Override
    public CampaignStatisticsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public CampaignStatisticsRecord value2(String value) {
        setDatasource(value);
        return this;
    }

    @Override
    public CampaignStatisticsRecord value3(String value) {
        setCampaignName(value);
        return this;
    }

    @Override
    public CampaignStatisticsRecord value4(Date value) {
        setDate(value);
        return this;
    }

    @Override
    public CampaignStatisticsRecord value5(Integer value) {
        setClicks(value);
        return this;
    }

    @Override
    public CampaignStatisticsRecord value6(Integer value) {
        setImpressions(value);
        return this;
    }

    @Override
    public CampaignStatisticsRecord values(Integer value1, String value2, String value3, Date value4, Integer value5, Integer value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CampaignStatisticsRecord
     */
    public CampaignStatisticsRecord() {
        super(CampaignStatistics.CAMPAIGN_STATISTICS);
    }

    /**
     * Create a detached, initialised CampaignStatisticsRecord
     */
    public CampaignStatisticsRecord(Integer id, String datasource, String campaignName, Date date, Integer clicks, Integer impressions) {
        super(CampaignStatistics.CAMPAIGN_STATISTICS);

        setId(id);
        setDatasource(datasource);
        setCampaignName(campaignName);
        setDate(date);
        setClicks(clicks);
        setImpressions(impressions);
    }
}
