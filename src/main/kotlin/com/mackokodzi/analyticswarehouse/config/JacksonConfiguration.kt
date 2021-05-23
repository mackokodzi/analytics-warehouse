package com.mackokodzi.analyticswarehouse.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.text.SimpleDateFormat

@Configuration
class JacksonConfiguration {

    @Bean
    fun objectMapper() = ObjectMapper()
        .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        .setDateFormat(SimpleDateFormat("yyyy-MM-dd"))
}