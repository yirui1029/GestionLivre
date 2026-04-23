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
		// 🔹 APP
		// =========================
		implementation("org.springframework.boot:spring-boot-starter")
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.springframework.boot:spring-boot-starter-validation")
		implementation("org.springframework.boot:spring-boot-starter-jdbc")

		implementation("org.postgresql:postgresql")
		implementation("org.liquibase:liquibase-core")
		implementation("com.h2database:h2")

		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

		// =========================
		// 🧪 TEST (unit)
		// =========================
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
		testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin")

		// Kotest
		testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
		testImplementation("io.kotest:kotest-assertions-core:5.9.1")
		testImplementation("io.kotest:kotest-property:5.9.1")

		testRuntimeOnly("org.junit.platform:junit-platform-launcher")
		testImplementation("org.testcontainers:postgresql:1.19.1")
		testImplementation("org.testcontainers:junit-jupiter:1.19.1")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
		finalizedBy(tasks.jacocoTestReport)
	}

	tasks.jacocoTestReport {
		dependsOn("test", "testIntegration", "testComponent")

		executionData.setFrom(
			fileTree(layout.buildDirectory).include(
				"jacoco/*.exec"
			)
		)

		reports {
			xml.required.set(true)
			html.required.set(true)
		}
	}

	kotlin {
		compilerOptions {
			freeCompilerArgs.addAll(
				"-Xjsr305=strict",
				"-Xannotation-default-target=param-property"
			)
		}
	}

	//
	// 🔥 TEST SUITES
	//
	testing {
		suites {

			val testIntegration by registering(JvmTestSuite::class) {

				useJUnitJupiter()

				dependencies {

					implementation(project())

					// Spring Test
					implementation("org.springframework.boot:spring-boot-starter-test")
					implementation("org.springframework.boot:spring-boot-starter-web")
					implementation("org.springframework.boot:spring-boot-starter-jdbc")

					// Jackson
					implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

					// Testcontainers
					implementation("org.testcontainers:postgresql:1.19.1")
					implementation("org.testcontainers:junit-jupiter:1.19.1")

					// Kotest
					implementation("io.kotest:kotest-runner-junit5:5.9.1")
					implementation("io.kotest:kotest-assertions-core:5.9.1")
					implementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")

					// Mock
					implementation("com.ninja-squad:springmockk:5.0.1")
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
				targets {
					all {
						testTask.configure {
							finalizedBy(tasks.jacocoTestReport)
						}
					}
				}
			}

			val testComponent by registering(JvmTestSuite::class) {

				useJUnitJupiter()

				dependencies {

					implementation(project())

					// Spring Test
					implementation("org.springframework.boot:spring-boot-starter-test")
					implementation("org.springframework.boot:spring-boot-starter-web")
					implementation("org.springframework.boot:spring-boot-starter-jdbc")

					// Jackson
					implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

					// Cucumber
					implementation("io.cucumber:cucumber-java:7.18.0")
					implementation("io.cucumber:cucumber-spring:7.18.0")
					implementation("io.cucumber:cucumber-junit-platform-engine:7.18.0")
					implementation("org.junit.platform:junit-platform-suite:1.10.0")

					// Mock
					implementation("com.ninja-squad:springmockk:5.0.1")
					implementation("com.h2database:h2")
					// RestAssured
					implementation("io.rest-assured:rest-assured:5.4.0")
					implementation("io.rest-assured:kotlin-extensions:5.4.0")
					implementation("io.kotest:kotest-runner-junit5:5.9.1")
					implementation("io.kotest:kotest-assertions-core:5.9.1")
				}

				sources {
					kotlin {
						setSrcDirs(listOf("src/testComponent/kotlin"))
					}
					resources {
						setSrcDirs(listOf("src/testComponent/resources"))
					}
				}
				targets {
					all {
						testTask.configure {
							finalizedBy(tasks.jacocoTestReport)
						}
					}
				}
			}
		}
	}