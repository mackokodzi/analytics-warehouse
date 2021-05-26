package com.mackokodzi.analyticswarehouse.external.campaign.csv

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvConstraintViolationException
import com.opencsv.exceptions.CsvDataTypeMismatchException
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.jvm.Throws

class CustomCsvDateConverter : AbstractBeanField<String, Instant>() {

    @Throws(CsvDataTypeMismatchException::class, CsvConstraintViolationException::class)
    override fun convert(value: String): LocalDate =
        try {
            val formatter = DateTimeFormatter.ofPattern("MM/dd/uu")
            LocalDate.parse(value, formatter)
        } catch (e: RuntimeException) {
            throw CsvDataTypeMismatchException(e.message)
        }
}