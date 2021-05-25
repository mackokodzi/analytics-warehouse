import nu.studer.gradle.jooq.JooqEdition
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.meta.jaxb.ForcedType

plugins {
	id("org.springframework.boot") version "2.4.5"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.32"
	kotlin("plugin.spring") version "1.4.32"
	kotlin("plugin.jpa") version "1.4.32"
	id("org.jetbrains.kotlin.plugin.allopen") version "1.5.0"
	id("org.jetbrains.kotlin.plugin.noarg") version "1.5.0"
	id("nu.studer.jooq") version "5.2.1"
}

group = "com.mackokodzi"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.hibernate:hibernate-core:5.4.28.Final")
	implementation("io.github.microutils:kotlin-logging:1.6.22")
	implementation("com.opencsv:opencsv:5.2")
	implementation("org.postgresql:postgresql:42.2.14")
	implementation("ru.yandex.qatools.embed:postgresql-embedded:2.10")
	jooqGenerator("org.postgresql:postgresql:42.2.14")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.rest-assured:rest-assured:4.2.0")
	testImplementation("io.rest-assured:xml-path:4.2.0")
	testImplementation("io.rest-assured:json-path:4.2.0")
	testImplementation("io.rest-assured:kotlin-extensions:4.2.0")
	testImplementation("org.junit.jupiter:junit-jupiter-params:5.7.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

sourceSets {
	create("integration") {
		withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
			kotlin.srcDir("src/integration/kotlin")
			resources.srcDir("src/integration/resources")
			compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
			runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
		}
	}
}

val integration = task<Test>("integration") {
	description = "Runs the integration tests"
	group = "verification"
	testClassesDirs = sourceSets["integration"].output.classesDirs
	classpath = sourceSets["integration"].runtimeClasspath
	mustRunAfter(tasks["test"])
}

tasks.check {
	dependsOn(integration)
}

jooq {
	version.set("3.14.7")  // the default (can be omitted)
	edition.set(JooqEdition.OSS)  // the default (can be omitted)

	configurations {
		create("main") {
			jooqConfiguration.apply {
				logging = org.jooq.meta.jaxb.Logging.WARN
				jdbc.apply {
					driver = "org.postgresql.Driver"
					url = "jdbc:postgresql://ec2-54-74-35-87.eu-west-1.compute.amazonaws.com:5432/dcdg9is8nfbj71"
					user = "uzeppdjtffyacq"
					password = "75c449688c8f44fc6bf64e24f4ea1243d874317bfdf27b08069cbe2a4f85cfcf"
				}
				generator.apply {
					name = "org.jooq.codegen.DefaultGenerator"
					database.apply {
						name = "org.jooq.meta.postgres.PostgresDatabase"
						inputSchema = "public"
						forcedTypes.addAll(arrayOf(
							ForcedType()
								.withUserType("java.time.Instant")
								.withConverter("com.mackokodzi.analyticswarehouse.config.jooq.InstantConverter")
								.withIncludeExpression("DATE")
								.withIncludeTypes(".*")
						).toList())
					}
					generate.apply {
						isDeprecated = false
						isRecords = true
					}
					target.apply {
						packageName = "com.mackokodzi.analyticswarehouse"
						directory = "src/main/db"
					}
					strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
				}
			}
		}
	}
}

buildscript {
	configurations["classpath"].resolutionStrategy.eachDependency {
		if (requested.group == "org.jooq") {
			useVersion("3.12.4")
		}
	}
}

tasks.named<nu.studer.gradle.jooq.JooqGenerate>("generateJooq") {
	enabled = false
}
