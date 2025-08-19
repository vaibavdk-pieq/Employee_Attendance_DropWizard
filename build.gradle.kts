plugins {
    kotlin("jvm") version "2.1.21"
    application
}

group = "to.dev.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.dropwizard:dropwizard-core:2.1.4")
    implementation(kotlin("stdlib"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")

    // For testing
    testImplementation(kotlin("test"))

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("EmployeeApplicationKt")
}
tasks.named<JavaExec>("run") {
    // Default program arguments for Dropwizard
    args("server", "src/main/resources/config.yml")

}