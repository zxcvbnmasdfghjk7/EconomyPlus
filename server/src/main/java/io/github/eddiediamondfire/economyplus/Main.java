package io.github.eddiediamondfire.economyplus;

import io.github.eddiediamondfire.economyplus.account.AccountManager;
import io.github.eddiediamondfire.economyplus.commands.CommandManager;
import io.github.eddiediamondfire.economyplus.core.EconomyCore;
import io.github.eddiediamondfire.economyplus.currency.CurrencyManager;
import io.github.eddiediamondfire.economyplus.data.Data;
import io.github.eddiediamondfire.economyplus.data.database.H2Database;
import io.github.eddiediamondfire.economyplus.data.TomlManager;
import io.github.eddiediamondfire.economyplus.listener.EconomyListener;
import io.github.eddiediamondfire.economyplus.utils.MessageManager;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Main extends JavaPlugin {
    private EconomyCore economyCore;
    private final AccountManager accountManager;
    private final CurrencyManager currencyManager;
    private final CommandManager commandManager;
    private final MessageManager messageManager;
    private static Main plugin;
    private final TomlManager tomlManager;
    private Data database = null;

    public Main(){
        economyCore = new EconomyCore(this);
        accountManager = new AccountManager(this);
        currencyManager = new CurrencyManager(this);
        commandManager = new CommandManager(this);
        tomlManager = new TomlManager(this);
        messageManager = new MessageManager(this);
    }

    @Override
    public void onEnable() {

        // Database loading algorithm
        MessageManager.sendMessage(ChatColor.YELLOW, "Loading Database initialisation");
        database = new H2Database(this);
        database.initaliseDatabase();
        MessageManager.sendMessage(ChatColor.GREEN, "Database initialisation complete!");

        // Vault Dependency Hook algorithm
        MessageManager.sendMessage(ChatColor.YELLOW, "Loading Vault Dependency Integration");
        if(!loadVaultEconomy()){
            MessageManager.sendMessage(ChatColor.RED, "Vault is not found, disabling plugin");
            getServer().getPluginManager().disablePlugin(this);
        }
        MessageManager.sendMessage(ChatColor.GREEN, "Loaded Vault Dependency Integration");

        // Register EconomyListener
        this.getServer().getPluginManager().registerEvents(new EconomyListener(this), this);
    }

    private boolean loadVaultEconomy(){
        if(getServer().getPluginManager().getPlugin("Vault") == null)
        {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

        if(rsp == null)
        {
            return false;
        }

        economyCore = (EconomyCore) rsp.getProvider();

        return true;

    }

    public static Main getPlugin(){
        return plugin;
    }
}
