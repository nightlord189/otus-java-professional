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
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    implementation("ch.qos.logback:logback-classic:1.5.18")
    implementation("org.hibernate.orm:hibernate-core:6.6.13.Final")
    implementation("org.postgresql:postgresql:42.7.5")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation("com.h2database:h2:2.3.232")
}

application {
    mainClass.set("org.aburavov.otus.java.professional.hw11.DbServiceDemo")
}

tasks.test {
    useJUnitPlatform()
}