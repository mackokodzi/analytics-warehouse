package com.mackokodzi.analyticswarehouse

//import com.xebialabs.restito.server.StubServer
//import io.restassured.RestAssured
import com.fasterxml.jackson.databind.ObjectMapper
import com.mackokodzi.analyticswarehouse.Tables.CAMPAIGN_STATISTICS
import org.jooq.DSLContext
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql

@Sql("/test-schema.sql")
@ContextConfiguration
@SpringBootTest(
	classes = [AnalyticsWarehouseApplication::class],
	webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@ActiveProfiles("integration")
abstract class BaseIntegrationTests(

) {
	val objectMapper: ObjectMapper = ObjectMapper()
	//lateinit var server: StubServer

	@Autowired
	lateinit var dsl: DSLContext

	@BeforeEach
	fun beforeEach() {
		cleanTables()
		//server = StubServer(7777).run()
		//RestAssured.port = server.port
	}

	@AfterEach
	fun afterEach() {
		//server.stop()
	}

	private fun cleanTables() {
		dsl.deleteFrom(CAMPAIGN_STATISTICS)
	}

	fun localUrl(url: String) = "http://localhost:8080/$url"
}