plugins {
    application
}

group = "hu.bergerdenes.battleship"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":engine"))

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

application {
    applicationName = "battleship"
    mainClass.set("hu.bergerdenes.battleship.Starter")
}
