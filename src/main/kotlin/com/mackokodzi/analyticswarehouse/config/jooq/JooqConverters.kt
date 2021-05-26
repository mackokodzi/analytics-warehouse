package com.mackokodzi.analyticswarehouse.config.jooq

import org.jooq.Converter
import java.sql.Date
import java.time.LocalDate

class LocalDateConverter : Converter<Date, LocalDate> {
    override fun from(databaseObject: Date?) = databaseObject?.toLocalDate()

    override fun to(userObject: LocalDate?) = userObject?.let { Date.valueOf(it) }

    override fun fromType() = Date::class.java

    override fun toType() = LocalDate::class.java
}