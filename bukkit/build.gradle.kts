repositories{
    maven{
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    }
    maven{
        url = uri("https://jitpack.io")
    }
}

val implementation by configurations
dependencies{
    implementation("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    implementation("com.github.MilkBowl:VaultAPI:1.7")
    implementation("com.moandjiezana.toml:toml4j:0.7.1")
    implementation("com.h2database:h2:1.4.200")
}