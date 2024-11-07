rootProject.name = ("spring-anything")

pluginManagement {
  val springBootVersion: String by settings
  val dependencyManagementVersion: String by settings

  plugins {
    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version dependencyManagementVersion
  }
}
