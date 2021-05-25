package com.mackokodzi.analyticswarehouse.external.campaign.csv

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.get
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.jvm.javaio.copyTo
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Repository
import java.io.File

@Repository
class CampaignStatisticsDataClient(
    private val campaignStatisticsHttpClient: HttpClient,
    @Value("\${client.campaign-statistics-data.url}") private val url: String
) {
    suspend fun downloadCsvData(file: File) {
        val channel = campaignStatisticsHttpClient.get<ByteReadChannel>(url)
        file.outputStream().use { fileStream ->
            channel.copyTo(fileStream)
        }
    }
}

@Configuration
class CampaignStatisticsDataClientConfig {
    @Bean
    fun campaignStatisticsHttpClient() = HttpClient(Apache)
}