plugins{
    java
    id("com.github.johnrengelman.shadow")
    kotlin("jvm") apply false
    idea
}

allprojects{
    group = "io.github.zxcvbnmasdfghjk7"
    version = "dev-SNAPSHOT"

    repositories{
        mavenCentral()
        flatDir {
            dirs("libraries")
        }
    }
}

subprojects{
    apply(plugin="java")
}

apply(plugin="java")
val kotlinVersion = project.properties["kotlinVersion"]
dependencies{
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${kotlinVersion}")
    implementation(project(":bukkit"))
    implementation(project(":api"))
}

java{
    toolchain{
        languageVersion.set(JavaLanguageVersion.of(16))
    }
}

val projectName: String = rootProject.name
val projectVersion: String = project.version as String

tasks{
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar"){
        archiveBaseName.set(projectName)
        archiveVersion.set(projectVersion)
    }
}