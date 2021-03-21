package io.github.eddiediamondfire.economyplus.dependency

import com.google.common.util.concurrent.ServiceManager
import io.github.eddiediamondfire.economyplus.Main
import io.github.eddiediamondfire.economyplus.utils.MessageManager
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.RegisteredServiceProvider
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.ServicesManager

class EPVaultHook(pluginClass: Main) {

    private val plugin: Main = pluginClass
    private val economyProvider: Economy = plugin.economyCore
    private val serviceManager: ServicesManager = Bukkit.getServicesManager()

    fun hookVault()
    {
        if(!vaultExist())
        {
            Bukkit.getPluginManager().disablePlugin(plugin)
            MessageManager.sendMessage(ChatColor.YELLOW, "Vault plugin is not found, disabling plugin!")
        }
        serviceManager.register(Economy::class.java, economyProvider, plugin, ServicePriority.Normal)
        MessageManager.sendMessage(ChatColor.GREEN, "Vault API is hooked into EconomyPlus")
    }

    fun unhookVault()
    {
        serviceManager.unregister(Economy::class.java, economyProvider)
        MessageManager.sendMessage(ChatColor.GREEN, "Vault API is unhooked from EconomyPlus")
    }
    private fun vaultExist(): Boolean{
        return plugin.server.pluginManager.getPlugin("Vault") != null
    }


}
