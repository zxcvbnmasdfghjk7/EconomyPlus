package io.github.eddiediamondfire.economyplus;

import io.github.eddiediamondfire.economyplus.api.EconomyPlusAPI;
import io.github.eddiediamondfire.economyplus.api.EconomyPlusManager;
import io.github.eddiediamondfire.economyplus.api.account.Account;
import io.github.eddiediamondfire.economyplus.command.CommandManager;
import io.github.eddiediamondfire.economyplus.config.FileManager;
import io.github.eddiediamondfire.economyplus.events.ServerListeners;
import io.github.eddiediamondfire.economyplus.player.MoneyManager;
import io.github.eddiediamondfire.economyplus.player.Player;
import io.github.eddiediamondfire.economyplus.storage.Storage;
import io.github.eddiediamondfire.economyplus.storage.StorageMethod;
import io.github.eddiediamondfire.economyplus.storage.database.H2Database;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class EconomyPlus extends JavaPlugin {

    public static String connectionUrl;
    private final FileManager fileManager;
    private final MoneyManager moneyManager;
    private Storage database = null;
    private FileConfiguration configFile;
    private Economy economy = null;
    private StorageMethod databaseStorageMethod = null;
    private EconomyPlusAPI api = null;

    private List<Account> accounts = null;

    public EconomyPlus(){
        this.fileManager = new FileManager(this);
        moneyManager = new MoneyManager(this);
        economy = new io.github.eddiediamondfire.economyplus.vault.Economy(this);
    }

    @Override
    public void onLoad(){
        if(this.getServer().getPluginManager().getPlugin("Vault") == null){
            this.getLogger().info("Vault is not found! /n Vault is required for economy plugin /n Disabling plugin....");
            this.getServer().getPluginManager().disablePlugin(this);
        }else{
            Bukkit.getServicesManager().register(Economy.class, economy, this, ServicePriority.Normal);
            getLogger().info("Successfully established Vault Hook!");
        }
    }

    @Override
    public void onEnable() {
        getLogger().info("Loading Database");
        connectionUrl = "jdbc:h2:" + getDataFolder().getAbsolutePath() + "/data/economy";

        getLogger().info("Loading File System");
        fileManager.onLoad();

        configFile = fileManager.getFile("config.yml").getBukkitConfig();

        if(configFile.getString("storage_settings.storage_method").equalsIgnoreCase("h2")){
            getLogger().info("Set the storage method to H2");
            database = new H2Database(this);
            databaseStorageMethod = new H2Database(this);

            database.initialiseDatabase();
        }else if(configFile.getString("storage_settings.storage_method").equalsIgnoreCase("mysql")){
            getLogger().info("Set the storage method to MySQL");
        }else{
            getLogger().info("Incorrect storage method set, defaulting to use H2");
            database = new H2Database(this);
            databaseStorageMethod = new H2Database(this);

            database.initialiseDatabase();
        }

        getLogger().info("Setting up Listeners");
        getServer().getPluginManager().registerEvents(new ServerListeners(this), this);
        getCommand("economyplus").setExecutor(new CommandManager(this));

        getLogger().info("Setting up EconomyPlus's API");
        api = new EconomyPlusManager(this);
        accounts = new ArrayList<>();
    }

    @Override
    public void onDisable(){
        getLogger().info("Saving PlayerData");
        moneyManager.savePlayerBanks();
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public Storage getDatabase() {
        return database;
    }

    public MoneyManager getMoneyManager() {
        return moneyManager;
    }

    public FileConfiguration getConfigFile() {
        return configFile;
    }

    public StorageMethod getDatabaseStorageMethod() {
        return databaseStorageMethod;
    }

    public EconomyPlusAPI getApi() {
        return api;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}