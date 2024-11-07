val queryDslVersion: String by extra("5.1.0")

plugins {
  id("org.springframework.boot")
  id("io.spring.dependency-management")
  java
}

group = "com"
version = "0.0.1-SNAPSHOT"

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
  maven("https://repo.spring.io/milestone")
  maven("https://repo.spring.io/snapshot")
  maven("https://plugins.gradle.org/m2/")
}

val snippetsDir = file("build/generated-snippets")

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-aop")
  implementation("org.springframework.boot:spring-boot-starter-mail")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-batch")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
  implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")
  implementation("com.atomikos:transactions-spring-boot3-starter:6.0.0")

  implementation("com.google.guava:guava:33.2.1-jre")

  implementation("software.amazon.awssdk:s3:2.27.17")
  implementation("software.amazon.awssdk:sts:2.27.17")

  implementation("org.bytedeco:javacv-platform:1.5.10")

  // implementation("org.flywaydb:flyway-core:9.4.0")

  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")
  implementation("org.bouncycastle:bcprov-jdk15to18:1.78.1")

  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.hibernate:hibernate-envers:6.2.6.Final")

  implementation("org.redisson:redisson-spring-boot-starter:3.37.0")
  // implementation("redis.clients:jedis:5.1.2")
  // implementation("org.apache.commons:commons-pool2:2.11.1")

  implementation("org.springframework.session:spring-session-core")
  implementation("org.springframework.session:spring-session-data-redis")
  implementation("org.springframework.boot:spring-boot-starter-data-redis")
  implementation("net.javacrumbs.shedlock:shedlock-spring:5.2.0")
  implementation("net.javacrumbs.shedlock:shedlock-provider-redis-spring:5.2.0")

  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
  implementation("com.fasterxml.jackson.core:jackson-databind")

  implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")

  implementation("de.mkammerer.snowflake-id:snowflake-id:0.0.2")
  implementation("com.fasterxml.uuid:java-uuid-generator:4.1.0")
  implementation("com.github.f4b6a3:uuid-creator:5.3.2")
  implementation("com.github.f4b6a3:tsid-creator:5.2.4")

  implementation("com.sksamuel.scrimage:scrimage-core:4.2.0")
  implementation("com.sksamuel.scrimage:scrimage-webp:4.2.0")

  implementation("commons-codec:commons-codec")
  implementation("commons-io:commons-io:2.17.0")
  implementation("org.apache.commons:commons-text:1.10.0")
  implementation("org.apache.commons:commons-lang3:3.12.0")
  implementation("org.apache.commons:commons-collections4:4.4")
  implementation("org.apache.commons:commons-configuration2:2.10.1")
  implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")

  implementation("io.projectreactor.addons:reactor-pool:1.0.1")
  implementation("io.micrometer:micrometer-core")
  implementation(group = "io.netty", name = "netty-resolver-dns-native-macos", classifier = "osx-aarch_64")

  implementation("com.mysql:mysql-connector-j")

  compileOnly("org.projectlombok:lombok")
  developmentOnly("org.springframework.boot:spring-boot-devtools")

  annotationProcessor("org.projectlombok:lombok")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

  implementation("com.querydsl:querydsl-jpa:${queryDslVersion}:jakarta")
  implementation("com.querydsl:querydsl-apt:${queryDslVersion}:jakarta")
  annotationProcessor("jakarta.annotation:jakarta.annotation-api")
  annotationProcessor("jakarta.persistence:jakarta.persistence-api")
  annotationProcessor("com.querydsl:querydsl-apt:${queryDslVersion}:jakarta")

  runtimeOnly("com.h2database:h2")
  runtimeOnly("com.mysql:mysql-connector-j")
  testRuntimeOnly("com.mysql:mysql-connector-j")

  testImplementation("org.openjdk.jmh:jmh-core:1.37")
  testImplementation("org.openjdk.jmh:jmh-generator-annprocess:1.37")
  testAnnotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.37")

  testCompileOnly("org.projectlombok:lombok")
  testAnnotationProcessor("org.projectlombok:lombok")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.3")
}

tasks.register<Delete>("cleanGeneratedDir") {
  delete(file("src/main/generated"))
}

tasks.test {
  useJUnitPlatform()
}
