package com.mackokodzi.analyticswarehouse.api.campaign

import com.mackokodzi.analyticswarehouse.api.campaign.RequestSortValidator.validate
import com.mackokodzi.analyticswarehouse.domain.campaign.Metric
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class RequestSortValidatorTest {

    @Test
    fun `should validate successfully for empty params`() {
        // when
        validate(
            sorts = emptyList(),
            metrics = emptyList(),
            groups = emptyList()
        )

        // no exception thrown
    }

    @ParameterizedTest
    @ValueSource(strings = ["wrong", "wrong.desc"])
    fun `should validate for wrong sort values`(sort: String) {
        // when //then
        assertThrows<SortValidationException> {
            validate(
                sorts = listOf(sort),
                metrics = listOf(Metric.CLICKS),
                groups = emptyList()
            )
        }
    }

    // TODO: add more tests for validation
}