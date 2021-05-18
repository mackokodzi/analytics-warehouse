package com.mackokodzi.analyticswarehouse.domain

import java.time.Instant

data class Datasource(val value: String)
data class Campaign(val value: String)
data class Date(val value: Instant)
data class Clicks(val value: Int)
data class Impressions(val value: Int)
