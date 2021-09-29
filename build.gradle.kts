import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.5"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
}

group = "com.demo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	//SPRING BOOT
	implementation("org.springframework.boot:spring-boot-starter-cache:2.5.5")
	implementation("org.springframework.boot:spring-boot-starter-webflux:2.5.5")
	testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.5")
    //implementation("org.springframework.boot:spring-boot-starter-web")

	//JACKSON
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.5")

	//KOTLIN
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.5.2-native-mt")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2-native-mt")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.5.2-native-mt")

	//GUAVA FOR CACHE
	implementation("com.google.guava:guava:30.1.1-jre")
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