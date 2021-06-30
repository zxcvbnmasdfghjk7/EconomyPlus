plugins{
    kotlin("jvm")
    id("net.minecrell.plugin-yml.bukkit")
}
repositories{
    maven{
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    }
    maven{
        url = uri("https://jitpack.io")
    }
}
dependencies{
    implementation("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    implementation("com.github.MilkBowl:VaultAPI:1.7")
    implementation("com.moandjiezana.toml:toml4j:0.7.1")
    implementation("com.h2database:h2:1.4.200")

    val slf4jVersion: String = project.properties["slf4jVersion"] as String
    implementation("org.slf4j:slf4j-api:${slf4jVersion}")
    implementation("org.slf4j:slf4j-log4j12:${slf4jVersion}")
}

bukkit{
    main = "io.github.eddiediamondfire.economyplus.EconomyPlus"

    apiVersion = "1.16"
}