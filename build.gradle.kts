plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring Boot core dependencies
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Kafka dependencies
	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.apache.kafka:kafka-streams")

	// MongoDB dependencies
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

	// Jackson for reading JSON files
	implementation("com.fasterxml.jackson.core:jackson-databind")

	// Hyperscan (VectorScan) for high-performance regex matching
//	implementation("com.gliwka.hyperscan:hyperscan-java-wrapper:1.0.6")
//	implementation("com.gliwka.hyperscan:hyperscan:5.4.11-3.0.0")


	// Testing dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}


tasks.withType<Test> {
	useJUnitPlatform()
}
