package io.github.eddiediamondfire.economyplus;

import com.moandjiezana.toml.Toml;
import io.github.eddiediamondfire.economyplus.account.AccountManager;
import io.github.eddiediamondfire.economyplus.commands.CommandManager;
import io.github.eddiediamondfire.economyplus.core.EconomyCore;
import io.github.eddiediamondfire.economyplus.currency.CurrencyManager;
import io.github.eddiediamondfire.economyplus.data.Data;
import io.github.eddiediamondfire.economyplus.data.TomlManager;
import io.github.eddiediamondfire.economyplus.data.database.H2Database;
import io.github.eddiediamondfire.economyplus.dependency.EPVaultHook;
import io.github.eddiediamondfire.economyplus.listener.EconomyListener;
import io.github.eddiediamondfire.economyplus.utils.MessageManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private final EconomyCore economyCore;
    private final AccountManager accountManager;
    private final CurrencyManager currencyManager;
    private final CommandManager commandManager;
    private MessageManager messageManager = null;
    private static Main plugin;
    private final TomlManager tomlManager;
    private Data database = null;
    private boolean debugMode;
    private Toml configurationFile = null;
    private final EPVaultHook vaultHook;

    public Main(){
        economyCore = new EconomyCore(this);
        accountManager = new AccountManager(this);
        currencyManager = new CurrencyManager(this);
        commandManager = new CommandManager(this);
        tomlManager = new TomlManager(this);
        vaultHook = new EPVaultHook(this);
    }

    @Override
    public void onEnable() {
        debugMode = false;

        // TODO implement config option
        configurationFile = tomlManager.loadTomlFile("config.toml");
        MessageManager.sendMessage(ChatColor.YELLOW, "Loading configuration files");

        if(tomlManager.isDebugEnabled())
        {
            MessageManager.sendMessage(ChatColor.YELLOW, "Debug Message mode enabled!");
            messageManager = new MessageManager(this, true);
        }

        messageManager = new MessageManager(this, false);

        // Database loading algorithm
        MessageManager.sendMessage(ChatColor.YELLOW, "Loading Database initialisation");
        database = new H2Database(this);
        database.initaliseDatabase();
        MessageManager.sendMessage(ChatColor.GREEN, "Database initialisation complete!");

        // Register EconomyListener
        this.getServer().getPluginManager().registerEvents(new EconomyListener(this), this);

        // Vault Dependency Hook algorithm
        MessageManager.sendMessage(ChatColor.YELLOW, "Loading Vault Dependency Integration");
        vaultHook.hookVault();
    }

    @Override
    public void onDisable() {

        // Vault Dependency Hook algorithm
        MessageManager.sendMessage(ChatColor.GREEN, "Unhooking Vault Dependency Integration");
        vaultHook.unhookVault();
    }


    public static Main getPlugin(){
        return plugin;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public EconomyCore getEconomyCore() {
        return economyCore;
    }

    public CurrencyManager getCurrencyManager() {
        return currencyManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public Data getDatabase(){
        return database;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public Toml getConfigurationFile() {
        return configurationFile;
    }
}
