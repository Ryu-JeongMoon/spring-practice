rootProject.name = "config-server"

pluginManagement {
  val kotlinVersion: String by settings
  val springBootVersion: String by settings
  val springDependencyManagement: String by settings

  plugins {
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion

    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion

    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springDependencyManagement
  }
}
