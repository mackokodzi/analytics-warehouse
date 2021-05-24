package com.mackokodzi.analyticswarehouse.config.jooq

import org.jooq.Converter
import java.sql.Timestamp
import java.time.Instant

class InstantConverter : Converter<Timestamp, Instant> {
    override fun from(databaseObject: Timestamp?) = databaseObject?.toInstant()

    override fun to(userObject: Instant?) = userObject?.let { Timestamp.from(it) }

    override fun fromType() = Timestamp::class.java

    override fun toType() = Instant::class.java
}