package com.mackokodzi.analyticswarehouse.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.text.SimpleDateFormat

@Configuration
class JacksonConfiguration {

    @Bean
    fun objectMapper() = ObjectMapper()
        .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        .registerModule(JavaTimeModule())
        .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, false)
        .setDateFormat(SimpleDateFormat("yyyy-MM-dd"))
}
