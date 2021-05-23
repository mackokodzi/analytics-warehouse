package com.mackokodzi.analyticswarehouse.external.campaign.csv

import com.opencsv.bean.CsvBindByPosition
import com.opencsv.bean.CsvCustomBindByPosition
import java.time.Instant

data class CampaignStatisticsCsvReportRow(
    @CsvBindByPosition(position = 0) val datasource: String = "",
    @CsvBindByPosition(position = 1) val campaignName: String = "",
    @CsvCustomBindByPosition(position = 2, converter = CustomCsvDateConverter::class) val date: Instant = Instant.now(),
    @CsvBindByPosition(position = 3) val clicks: Int = 0,
    @CsvBindByPosition(position = 4) val impressions: Int = 0
)