plugins {
    id("java")
    id("org.springframework.boot") version "3.5.16"
}

group = "org.aburavov"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.google.code.findbugs:jsr305:3.0.2")

    implementation("org.postgresql:r2dbc-postgresql")
    implementation("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")

    runtimeOnly("org.flywaydb:flyway-database-postgresql")
}

tasks.test {
    useJUnitPlatform()
}
