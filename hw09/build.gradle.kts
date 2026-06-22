plugins {
    id("java")
    id("application")
}

group = "org.aburavov"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.5.18")
    implementation("org.flywaydb:flyway-core:11.7.2")
    implementation("com.zaxxer:HikariCP:6.3.0")
    implementation("org.postgresql:postgresql:42.7.5")

    runtimeOnly("org.flywaydb:flyway-database-postgresql:11.7.2")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.assertj:assertj-core:3.27.3")
}

application {
    mainClass.set("org.aburavov.otus.java.professional.hw09.HomeWork")
}

tasks.test {
    useJUnitPlatform()
}
