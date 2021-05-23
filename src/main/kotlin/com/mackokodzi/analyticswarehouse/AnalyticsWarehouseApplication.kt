package com.mackokodzi.analyticswarehouse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class AnalyticsWarehouseApplication

fun main(args: Array<String>) {
	runApplication<AnalyticsWarehouseApplication>(*args)
}
