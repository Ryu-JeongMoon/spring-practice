import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "2.6.7"
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  kotlin("jvm") version "1.6.21"
  kotlin("plugin.spring") version "1.6.21"
  kotlin("plugin.jpa") version "1.6.21"
  kotlin("kapt") version "1.6.10"
}

group = "com"
version = "1.0"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  developmentOnly("org.springframework.boot:spring-boot-devtools")
  runtimeOnly("com.h2database:h2")

  implementation("org.mapstruct:mapstruct:1.4.2.Final")
  annotationProcessor("org.mapstruct:mapstruct-processor:1.4.2.Final")
  kapt("org.mapstruct:mapstruct-processor:1.4.2.Final")
  implementation("com.github.pozo:mapstruct-kotlin:1.4.0.0")
  kapt("com.github.pozo:mapstruct-kotlin-processor:1.4.0.0")

  implementation("com.querydsl:querydsl-jpa")
  kapt(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")
  kotlin.sourceSets.main {
    setBuildDir("$buildDir")
  }

  testImplementation("org.mapstruct:mapstruct-processor:1.4.2.Final")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "17"
  }
}

kotlin {
  kotlinDaemonJvmArgs = listOf("--illegal-access=permit")
}

tasks.withType<Test> {
  useJUnitPlatform()
}
