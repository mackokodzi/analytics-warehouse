package com.mackokodzi.analyticswarehouse.external.campaign.csv

import com.opencsv.bean.CsvBindByPosition
import com.opencsv.bean.CsvCustomBindByPosition
import java.time.LocalDate

data class CampaignStatisticsCsvReportRow(
    @CsvBindByPosition(position = 0) val datasource: String = "",
    @CsvBindByPosition(position = 1) val campaignName: String = "",
    @CsvCustomBindByPosition(position = 2, converter = CustomCsvDateConverter::class) val date: LocalDate = LocalDate.now(),
    @CsvBindByPosition(position = 3) val clicks: Int = 0,
    @CsvBindByPosition(position = 4) val impressions: Int = 0
)