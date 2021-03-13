package io.github.eddiediamondfire.economyplus;

import io.github.eddiediamondfire.economyplus.account.AccountManager;
import io.github.eddiediamondfire.economyplus.api.EconomyAPI;
import io.github.eddiediamondfire.economyplus.commands.CommandManager;
import io.github.eddiediamondfire.economyplus.core.EconomyCore;
import io.github.eddiediamondfire.economyplus.currency.CurrencyManager;
import io.github.eddiediamondfire.economyplus.dependencies.Dependency;
import io.github.eddiediamondfire.economyplus.dependencies.VaultAPIHook;
import io.github.eddiediamondfire.economyplus.listener.EconomyListener;
import io.github.eddiediamondfire.economyplus.storage.AbstractFile;
import io.github.eddiediamondfire.economyplus.storage.Accounts;
import io.github.eddiediamondfire.economyplus.storage.Configuration;
import io.github.eddiediamondfire.economyplus.storage.Currency;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Main extends JavaPlugin implements EconomyAPI {
    private final List<Dependency> dependencies;
    private final List<AbstractFile> files;
    private final EconomyCore economyCore;
    private final AccountManager accountManager;
    private final CurrencyManager currencyManager;
    private final CommandManager commandManager;
    private final AbstractFile accountsStorage;
    private final AbstractFile currencyStorage;
    private static Main plugin;
    private int backupPeriod;
    private boolean useBackup;

    public Main(){
        dependencies = new ArrayList<>();
        economyCore = new EconomyCore(this);
        dependencies.add(new VaultAPIHook(this));
        accountManager = new AccountManager(this);
        currencyManager = new CurrencyManager(this);
        commandManager = new CommandManager(this);
        accountsStorage = new Accounts(this);
        currencyStorage = new Currency(this);
        files = new ArrayList<>();
        files.add(accountsStorage);
        files.add(new Configuration(this));
        files.add(currencyStorage);
    }

    @Override
    public void onEnable() {
        for (Dependency api : dependencies) {
            api.onLoad();
        }
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Loaded Dependencies");

        loadFiles();

        getServer().getPluginManager().registerEvents(new EconomyListener(this), this);
    }

    private void loadFiles(){
        for(AbstractFile file:files){
            file.loadFiles();

            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Loading " + file.getFileName());
        }
    }

    public static Main getPlugin(){
        return plugin;
    }

    public void setBackupPeriod(int period, boolean useBackup){
        this.backupPeriod = period;
        this.useBackup = useBackup;
    }
}
