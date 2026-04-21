plugins {
    id("java")
}

group = "org.aburavov"
version = "unspecified"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}