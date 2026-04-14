plugins {
	kotlin("jvm") version "2.2.21"
	kotlin("plugin.spring") version "2.2.21"
	id("org.springframework.boot") version "4.0.5"
	id("io.spring.dependency-management") version "1.1.7"
	id("jacoco")

}

group = "com.yirui"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

jacoco {
	toolVersion = "0.8.14"

}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
	testImplementation("io.kotest:kotest-assertions-core:5.9.1")
	testImplementation("io.kotest:kotest-property:5.9.1")
	implementation("org.springframework.boot:spring-boot-starter-validation")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)

	reports {
		xml.required.set(true)
		html.required.set(true)
	}
}

testing {
	suites {
		val testIntegration by registering(JvmTestSuite::class) {

			useJUnitJupiter()

			dependencies {
				implementation(project())

				implementation("org.springframework.boot:spring-boot-starter-test") {
					exclude(module = "mockito-core")
				}
				implementation("io.mockk:mockk:1.13.8")
				implementation("io.kotest:kotest-runner-junit5:5.9.1")
				implementation("io.kotest:kotest-assertions-core:5.9.1")
				implementation("io.kotest.extensions:kotest-extensions-spring:1.3.0")
				implementation("com.ninja-squad:springmockk:4.0.2")
			}

			sources {
				kotlin {
					setSrcDirs(listOf("src/testIntegration/kotlin"))
				}
			}

			targets {
				all {
					testTask.configure {
						shouldRunAfter(tasks.test)
					}
				}
			}
		}
	}
}



