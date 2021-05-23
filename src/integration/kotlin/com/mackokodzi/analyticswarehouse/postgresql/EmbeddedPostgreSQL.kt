package com.mackokodzi.analyticswarehouse.postgresql

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent
import org.springframework.context.ApplicationListener
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres

class EmbeddedPostgreSQL : ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private var embeddedPostgres : EmbeddedPostgres? = null

    fun startup() {
        if (embeddedPostgres == null) {
            var dataDir = System.getProperty("de.flapdoodle.embed.io.tmpdir")
            dataDir += "/data"

            embeddedPostgres = EmbeddedPostgres(ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V9_6, dataDir)
            embeddedPostgres?.start(EMBEDDED_PSQL_HOSTNAME, EMBEDDED_PSQL_PORT, EMBEDDED_PSQL_DATABASE, EMBEDDED_PSQL_USER, EMBEDDED_PSQL_PASSWORD)
        }
    }

    fun shutdown() {
        if (embeddedPostgres != null) {
            embeddedPostgres?.stop()
            embeddedPostgres = null
        }
    }

    override fun onApplicationEvent(event: ApplicationEnvironmentPreparedEvent) {
        startup()
    }
}

const val EMBEDDED_PSQL_USER = "analytics_i"
const val EMBEDDED_PSQL_PASSWORD = "analytics_i_password"
const val EMBEDDED_PSQL_DATABASE = "analytics_i"
const val EMBEDDED_PSQL_HOSTNAME = "localhost"
const val EMBEDDED_PSQL_PORT = 57792