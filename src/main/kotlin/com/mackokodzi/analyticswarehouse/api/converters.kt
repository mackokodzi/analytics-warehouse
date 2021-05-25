package com.mackokodzi.analyticswarehouse.api

import com.mackokodzi.analyticswarehouse.domain.campaign.Group
import com.mackokodzi.analyticswarehouse.domain.campaign.Metric
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.format.FormatterRegistry

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(StringToMetricConverter())
        registry.addConverter(StringToGroupConverter())
    }
}

class StringToMetricConverter : Converter<String, Metric> {
    override fun convert(source: String): Metric {
        return Metric.valueOf(source.toUpperCase())
    }
}

class StringToGroupConverter : Converter<String, Group> {
    override fun convert(source: String): Group {
        return Group.valueOf(source.toUpperCase())
    }
}

