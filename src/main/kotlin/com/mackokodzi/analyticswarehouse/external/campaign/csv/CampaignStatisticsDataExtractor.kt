package com.mackokodzi.analyticswarehouse.external.campaign.csv

import com.opencsv.bean.CsvToBeanBuilder
import com.opencsv.enums.CSVReaderNullFieldIndicator
import mu.KLogging
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStreamReader

@Component
class CampaignStatisticsDataExtractor {

    fun extractCsvData(file: File) : List<CampaignStatisticsCsvReportRow> {
        return parseCsv(file.readBytes())
    }

    private fun parseCsv(byteArray: ByteArray): List<CampaignStatisticsCsvReportRow> =
        CsvToBeanBuilder<CampaignStatisticsCsvReportRow>(InputStreamReader(ByteArrayInputStream(byteArray)))
            .withType(CampaignStatisticsCsvReportRow::class.java)
            .withIgnoreLeadingWhiteSpace(true)
            .withSeparator(","[0]) // This zero converts single string to char
            .withFieldAsNull(CSVReaderNullFieldIndicator.NEITHER)
            .withSkipLines(1)
            .build()
            .parse()

    companion object : KLogging()
}