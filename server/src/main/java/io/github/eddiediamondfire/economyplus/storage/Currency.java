package io.github.eddiediamondfire.economyplus.storage;

import io.github.eddiediamondfire.economyplus.Main;
import io.github.eddiediamondfire.economyplus.currency.CurrencyManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Currency implements AbstractFile{
    private final Main plugin;
    private final File file;
    private final FileConfiguration manager;
    private final CurrencyManager currencyManager;
    public Currency(Main plugin){
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder() + "/" + getFileName());
        this.manager = new YamlConfiguration();
        this.currencyManager = plugin.getCurrencyManager();
    }

    @Override
    public void loadFiles() {
        if(!file.exists()){
            file.getParentFile().mkdirs();
            plugin.saveResource(getFileName(), false);
        }

        try{
            manager.load(file);
        }catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }

        //

        ConfigurationSection section = manager.getConfigurationSection("currencies");

        for(String key : section.getKeys(false)){
            UUID keyUUID = UUID.fromString(key);
            currencyManager.loadCurrencies(
                    keyUUID,
                    manager.getString("currencies." + key + ".plural"),
                    manager.getString("currencies." + key + ".singular"),
                    ChatColor.valueOf(manager.getString("currencies." + key + ".currencyColour")),
                    manager.getBoolean("currencies." + key + ".isDecimal"),
                    manager.getBoolean("currencies." + key + ".isExchangable"),
                    manager.getBoolean("currencies." + key + ".isDefault"),
                    manager.getDouble("currencies." + key + ".startBalance"),
                    manager.getString("currencies." + key + ".symbol").charAt(1));

            plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Loading currency " + manager.getString("currencies." + key + ".singular"));

            if(key == null){
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No currency is found, creating a default currency");

                currencyManager.createCurrency("Dollar", "Dollars");
                break;
            }
        }

    }

    @Override
    public void saveFiles() {

        for(io.github.eddiediamondfire.economyplus.currency.Currency cu: plugin.getCurrencyManager().getCurrencies()){
            manager.set("currencies", cu.getId().toString());
            manager.set("currencies." + cu.getId().toString(), cu.getPlural());
            manager.set("currencies." + cu.getId().toString(), cu.getSingular());
            manager.set("currencies." + cu.getId().toString(), cu.isDecimal());
            manager.set("currencies." + cu.getId().toString(), cu.isExchangeAble());
            manager.set("currencies." + cu.getId().toString(), cu.isDefault());
            manager.set("currencies." + cu.getId().toString(), cu.getStartBalance());
            manager.set("currencies." + cu.getId().toString(), cu.getSymbol());
            manager.set("currencies." + cu.getId().toString(), cu.getCurrencyColour().toString());

            plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Saved Currency " + cu.getSingular());
        }
        try{
            manager.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public String getFileName() {
        return "currency.yml";
    }

    @Override
    public FileConfiguration getManager() {
        return manager;
    }
}
