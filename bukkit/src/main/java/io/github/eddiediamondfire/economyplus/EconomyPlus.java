package io.github.eddiediamondfire.economyplus;

import com.moandjiezana.toml.Toml;
import io.github.eddiediamondfire.economyplus.account.AccountManager;
import io.github.eddiediamondfire.economyplus.datahandler.FileManager;
import io.github.eddiediamondfire.economyplus.datahandler.H2DatabaseHandler;
import io.github.eddiediamondfire.economyplus.listener.EconomyListener;
import io.github.eddiediamondfire.economyplus.vault.EPVaultHook;
import io.github.eddiediamondfire.economyplus.vault.EconomyCore;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EconomyPlus extends JavaPlugin {
    private final Economy economyCore;
    private final FileManager fileManager;
    private Toml config = null;
    private final EPVaultHook vaultHook;
    private final Logger logger;
    private final H2DatabaseHandler databaseHandler;
    private final AccountManager accountManager;

    public EconomyPlus() {
        logger = LoggerFactory.getLogger("EconomyPlus");
        economyCore = new EconomyCore(this);
        fileManager = new FileManager(this);
        vaultHook = new EPVaultHook(this);
        databaseHandler = new H2DatabaseHandler(this);
        accountManager = new AccountManager(this);
    }

    @Override
    public void onEnable(){
        logger.info("Initialising Vault Integration");
        vaultHook.hookVaultIntegration();

        logger.info("Loading Configuration");
        config = fileManager.getTomlFile("config");

        logger.info("Loading Database System");
        databaseHandler.initialiseDatabase();

        logger.info("Loading Account Manager");
        this.getServer().getPluginManager().registerEvents(new EconomyListener(this), this);
    }

    @Override
    public void onDisable() {
        logger.info("Disabling Vault Integration");
        vaultHook.unhookVaultIntegration();
    }

    public Economy getEconomyCore() {
        return economyCore;
    }

    public Logger getCustomLogger() {
        return logger;
    }

    public Toml getTomlConfig() {
        return config;
    }

    public H2DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }
}
