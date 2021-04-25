import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

buildscript {
    val shadowJar_Version by rootProject.extra{
        "6.1.0"
    }

    val slf4j_Version by rootProject.extra{
        "1.7.30"
    }
    repositories{
        mavenCentral()
        maven{
            url = uri("https://plugins.gradle.org/m2/")
        }
    }

    dependencies{
        classpath("com.github.jengelman.gradle.plugins:shadow:${rootProject.extra.get("shadowJar_Version")}")
    }
}

allprojects{
    group = "io.github.eddiediamondfire"
    version = "dev-SNAPSHOT"
}

subprojects{
    repositories{
        mavenCentral()
    }

    apply(plugin = "java")
    apply(plugin = "idea")
    apply(plugin = "com.github.johnrengelman.shadow")

    val implementation by configurations
    dependencies{
        implementation("org.slf4j:slf4j-api:${rootProject.extra.get("slf4j_Version")}")
        implementation("org.slf4j:slf4j-log4j12:${rootProject.extra.get("slf4j_Version")}")
    }

    tasks.named<ShadowJar>("shadowJar"){
        archiveBaseName.set("EconomyPlus")
        archiveVersion.set("dev-SNAPSHOT")
    }
}