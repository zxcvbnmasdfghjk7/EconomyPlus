 import org.jetbrains.kotlin.psi2ir.generators.unwrapCallableDescriptorAndTypeArguments

plugins{
    kotlin("jvm")
    id("net.minecrell.plugin-yml.bukkit")
}
repositories{
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    maven("https://jitpack.io")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
}
dependencies{
    compileOnly("org.spigotmc:spigot-api:1.17-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    implementation("com.h2database:h2:1.4.200")
    implementation("org.python:jython:2.7.2")

    val slf4jVersion: String = project.properties["slf4jVersion"] as String
    implementation("org.slf4j:slf4j-api:${slf4jVersion}")
    implementation("org.slf4j:slf4j-log4j12:${slf4jVersion}")
    implementation(project(":api"))
}

bukkit{
    name = "EconomyPlus"
    main = "io.github.eddiediamondfire.economyplus.EconomyPlus"
    apiVersion = "1.17"
    authors = listOf("EddieDiamondFire (ScxLore1216)")
    depend = listOf("Vault")
    defaultPermission = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
    load = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.PluginLoadOrder.STARTUP

    commands{
        register("economyplus"){
            description = "Main plugin"
            aliases = listOf("ep", "economy", "eco")
        }
    }

    permissions{
        register("economyplus.*"){
            children = listOf("economyplus.admin.*")
        }
        register("economyplus.admin.*"){
            children = listOf("economyplus.admin.set", "economyplus.admin.add", "economyplus.admin.remove")
            childrenMap = mapOf("economyplus.admin.set" to true)
            childrenMap = mapOf("economyplus.admin.add" to true)
            childrenMap = mapOf("economyplus.admin.remove" to true)
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("economyplus.admin.set"){
            description = "Admin SubCommand for setting player's account"
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("economyplus.admin.add"){
            description = "Admin SubCommand for adding amount into player's account"
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("economyplus.admin.remove"){
            description = "Admin SubCommand for subtracting amount from player's account"
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
    }
}