package com.mackokodzi.analyticswarehouse.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestEndpoint {

    @GetMapping(produces = ["application/json"])
    fun helloWorld() = ResponseEntity.ok("Hello World")
}