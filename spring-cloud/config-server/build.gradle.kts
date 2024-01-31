plugins {
  kotlin("jvm")
  kotlin("kapt")

  kotlin("plugin.spring")
  kotlin("plugin.jpa")

  id("org.springframework.boot")
  id("io.spring.dependency-management")
}

group = "com.cloud.config.server"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_21

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.cloud:spring-cloud-config-server")
  implementation("org.springframework.cloud:spring-cloud-starter-config")

  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
  imports {
    val springCloudVersion: String by project
    mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
  }
}

kotlin {
  jvmToolchain(21)
}
