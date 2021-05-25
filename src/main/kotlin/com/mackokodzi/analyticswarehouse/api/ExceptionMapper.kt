package com.mackokodzi.analyticswarehouse.api

import com.mackokodzi.analyticswarehouse.api.campaign.SortValidationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionMapper {

    @ExceptionHandler(SortValidationException::class)
    fun handleSortValidationException(e: SortValidationException) : ResponseEntity<*> {
        return ResponseEntity.badRequest().body("Invalid sort")
    }
}