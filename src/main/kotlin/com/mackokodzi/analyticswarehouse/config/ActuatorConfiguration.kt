package com.mackokodzi.analyticswarehouse.config

import org.springframework.boot.actuate.trace.http.HttpTraceRepository
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ActuatorConfiguration {

    @Bean
    fun httpTraceRepository(): HttpTraceRepository = InMemoryHttpTraceRepository()

}