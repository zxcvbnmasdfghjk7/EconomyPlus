package io.github.eddiediamondfire.economyplus.dependencies;

import io.github.eddiediamondfire.economyplus.Main;
import io.github.eddiediamondfire.economyplus.core.EconomyCore;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultAPIHook implements Dependency{
    private final Main plugin;
    private EconomyCore core = null;
    public VaultAPIHook(Main pl){
        this.plugin = pl;
        core = plugin.getEconomyCore();
    }

    @Override
    public String getName() {
        return "Vault";
    }

    @Override
    public void onLoad() {
        if(!loadEconomy()){
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Dependency Vault is not detected, disabling plugin");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    @Override
    public void onUnload() {

    }

    private boolean loadEconomy(){
        if(plugin.getServer().getPluginManager().getPlugin("Vault") == null){
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if(rsp == null){
            return false;
        }

        core = (EconomyCore) rsp.getProvider();
        return core != null;
    }
}
