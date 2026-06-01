plugins {
    id("java")
}

group = "org.aburavov"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("ch.qos.logback:logback-classic:1.5.18")
}

tasks.test {
    useJUnitPlatform()
}