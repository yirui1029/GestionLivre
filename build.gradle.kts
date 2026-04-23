plugins {
	kotlin("jvm") version "2.1.21"
	kotlin("plugin.spring") version "2.1.21"
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.7"
	id("jacoco")
	id("jvm-test-suite")
}

group = "com.yirui"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

jacoco {
	toolVersion = "0.8.14"
}

dependencies {

	// =========================
	// APP
	// =========================
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")

	implementation("com.h2database:h2")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// =========================
	// UNIT TEST
	// =========================
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

	// =========================
	// TEST CONTAINERS (optionnel mais gardé)
	// =========================
	testImplementation("org.testcontainers:junit-jupiter:1.19.1")
}

//
// =========================
// GLOBAL TEST CONFIG
// =========================
tasks.withType<Test> {
	useJUnitPlatform()

	reports {
		junitXml.required.set(true)
		html.required.set(true)
	}
}

//
// =========================
// JACoCo
// =========================
tasks.jacocoTestReport {
	dependsOn("test", "testIntegration", "testComponent")

	reports {
		xml.required.set(true)
		html.required.set(true)
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll(
			"-Xjsr305=strict"
		)
	}
}

//
// =========================
// TEST SUITES
// =========================
testing {
	suites {

		// -------------------------
		// INTEGRATION TESTS
		// -------------------------
		val testIntegration by registering(JvmTestSuite::class) {

			useJUnitJupiter()

			dependencies {
				implementation(project())
				implementation("org.springframework.boot:spring-boot-starter-test")
				implementation("org.springframework.boot:spring-boot-starter-web")
				implementation("org.springframework.boot:spring-boot-starter-jdbc")
				implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
				implementation("com.h2database:h2")
			}

			sources {
				kotlin {
					setSrcDirs(listOf("src/testIntegration/kotlin"))
				}
				resources {
					setSrcDirs(listOf("src/testIntegration/resources"))
				}
			}
		}

		// -------------------------
		// COMPONENT TESTS (CUCUMBER)
		// -------------------------
		val testComponent by registering(JvmTestSuite::class) {

			useJUnitJupiter()

			dependencies {
				implementation(project())

				implementation("org.springframework.boot:spring-boot-starter-test")
				implementation("org.springframework.boot:spring-boot-starter-web")
				implementation("org.springframework.boot:spring-boot-starter-jdbc")

				// Cucumber
				implementation("io.cucumber:cucumber-java:7.18.0")
				implementation("io.cucumber:cucumber-spring:7.18.0")
				implementation("io.cucumber:cucumber-junit-platform-engine:7.18.0")
				implementation("org.junit.platform:junit-platform-suite:1.10.0")

				// REST
				implementation("io.rest-assured:rest-assured:5.4.0")

				// Kotlin assertions
				implementation("io.kotest:kotest-assertions-core:5.9.1")

				// H2 (important pour éviter Docker/Postgres bugs)
				implementation("com.h2database:h2")
			}

			sources {
				kotlin {
					setSrcDirs(listOf("src/testComponent/kotlin"))
				}
				resources {
					setSrcDirs(listOf("src/testComponent/resources"))
				}
			}
		}
	}
}