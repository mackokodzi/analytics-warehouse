package com.mackokodzi.analyticswarehouse.external.campaign.csv

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvConstraintViolationException
import com.opencsv.exceptions.CsvDataTypeMismatchException
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class CustomCsvDateConverter : AbstractBeanField<String, Instant>() {

    @Throws(CsvDataTypeMismatchException::class, CsvConstraintViolationException::class)
    override fun convert(value: String): Instant =
        try {
            val formatter = DateTimeFormatter.ofPattern("MM/dd/uu")
            val date = LocalDate.parse(value, formatter)
            date.atStartOfDay().toInstant(ZoneOffset.UTC)
        } catch (e: RuntimeException) {
            throw CsvDataTypeMismatchException(e.message)
        }
}