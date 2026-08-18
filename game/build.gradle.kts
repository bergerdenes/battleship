plugins {
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.beryx.jlink") version "4.1.1"
}

group = "hu.bergerdenes.battleship"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    constraints {
        implementation("com.fasterxml.jackson.core:jackson-core:2.22.1")
    }

    implementation("org.controlsfx:controlsfx:11.2.1")
    implementation(project(":engine"))
    implementation("com.github.almasb:fxgl:25.0.1") {
        exclude(group = "org.openjfx")
    }

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

javafx {
    version = "26.0.2"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.media")

}

application {
    applicationName = "battleship"
    mainClass.set("hu.bergerdenes.battleship.Launcher")
    mainModule.set("battleship.game")
}

jlink {
    options = listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
    launcher {
        name = "battleship-game"
        jvmArgs = listOf("--enable-native-access=javafx.graphics,javafx.media")
        noConsole = true
    }
}