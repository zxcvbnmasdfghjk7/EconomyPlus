package io.github.eddiediamondfire.economyplus.vault;

import io.github.eddiediamondfire.economyplus.EconomyPlus;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EPVaultHook {

    private final EconomyPlus plugin;
    private ServicesManager servicesManager = null;
    private final Logger logger;
    private Economy economyProvider = null;
    public EPVaultHook(EconomyPlus plugin){
        this.plugin = plugin;
        logger = plugin.getCustomLogger();
    }

    public void hookVaultIntegration(){
        if(economyProvider == null){
            economyProvider = new EconomyCore(plugin);
        }

        servicesManager = plugin.getServer().getServicesManager();
        servicesManager.register(Economy.class, economyProvider, plugin, ServicePriority.Normal);

        logger.info("Fully hooked into VaultAPI");
    }

    public void unhookVaultIntegration(){
        if(economyProvider != null){
            servicesManager = plugin.getServer().getServicesManager();
            servicesManager.unregister(Economy.class, economyProvider);

            logger.info("Unhooked from VaultAPI");
        }
    }
}
