plugins {
    id("java")
    id("application")
}

group = "org.aburavov"
version = "unspecified"

repositories {
    mavenCentral()
}

application {
    mainClass.set("org.aburavov.otus.java.professional.hw05.Main")
}

tasks.test {
    useJUnitPlatform()
}
