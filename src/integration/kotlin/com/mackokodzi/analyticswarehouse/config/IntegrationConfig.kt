package com.mackokodzi.analyticswarehouse.config

import com.mackokodzi.analyticswarehouse.postgresql.EmbeddedPostgreSQL
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class IntegrationConfig {

    @Bean(initMethod = "startup", destroyMethod = "shutdown")
    fun embeddedPostgreSQL(): EmbeddedPostgreSQL = EmbeddedPostgreSQL()
}