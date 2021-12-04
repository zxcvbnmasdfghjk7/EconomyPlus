package io.github.zxcvbnmasdfghjk7.economyplus;

import io.github.zxcvbnmasdfghjk7.economyplus.api.EconomyPlusAPI;
import io.github.zxcvbnmasdfghjk7.economyplus.api.implementation.EconomyAPIImplementation;
import io.github.zxcvbnmasdfghjk7.economyplus.command.CommandManager;
import io.github.zxcvbnmasdfghjk7.economyplus.config.FileManager;
import io.github.zxcvbnmasdfghjk7.economyplus.currency.CurrencyManager;
import io.github.zxcvbnmasdfghjk7.economyplus.events.ServerListeners;
import io.github.zxcvbnmasdfghjk7.economyplus.player.MoneyManager;
import io.github.zxcvbnmasdfghjk7.economyplus.storage.Storage;
import io.github.zxcvbnmasdfghjk7.economyplus.storage.StorageMethod;
import io.github.zxcvbnmasdfghjk7.economyplus.storage.database.H2Database;
import io.github.zxcvbnmasdfghjk7.economyplus.utils.PythonExecution;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class EconomyPlus extends JavaPlugin {

    public static String connectionUrl;
    private final FileManager fileManager;
    private final MoneyManager moneyManager;
    private Storage database = null;
    private FileConfiguration configFile;
    private final Economy economy;
    private StorageMethod databaseStorageMethod = null;
    private final CurrencyManager currencyManager;
    private static EconomyPlus plugin = null;
    private final PythonExecution python;
    private EconomyPlusAPI api = null;

    public EconomyPlus(){
        python = new PythonExecution(this);
        this.fileManager = new FileManager(this);
        moneyManager = new MoneyManager(this);
        economy = new io.github.zxcvbnmasdfghjk7.economyplus.vault.Economy(this);
        currencyManager = new CurrencyManager(this);
    }

    public static EconomyPlus getPlugin() {
        return plugin;
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
        plugin = this;
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

        getLogger().info("Loading Currencies");
        currencyManager.loadCurrencies();

        getLogger().info("Setting up Listeners");
        getServer().getPluginManager().registerEvents(new ServerListeners(this), this);
        getCommand("economyplus").setExecutor(new CommandManager(this));

        getLogger().info("Setting up EconomyPlus's API");
        api = new EconomyAPIImplementation(this);
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

    public CurrencyManager getCurrencyManager() {
        return currencyManager;
    }

    public EconomyPlusAPI getApplicationProgrammingInterface() {
        return api;
    }

    public PythonExecution getPython() {
        return python;
    }
}