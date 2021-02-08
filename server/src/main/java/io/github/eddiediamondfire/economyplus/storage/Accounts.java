package io.github.eddiediamondfire.economyplus.storage;

import io.github.eddiediamondfire.economyplus.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Accounts implements AbstractFile{
    private final Main plugin;
    private final File file;
    private final FileConfiguration bukkitConfig;
    public Accounts(Main plugin){
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder() + "/" + getFileName());
        this.bukkitConfig = new YamlConfiguration();
    }

    @Override
    public void loadFiles() {
        if(!file.exists()){
            file.getParentFile().mkdirs();
            plugin.saveResource(getFileName(), false);
        }

        try{
            bukkitConfig.load(file);
        }catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveFiles() {
        try{
            bukkitConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public String getFileName() {
        return "playerdata.yml";
    }

    @Override
    public FileConfiguration getManager() {
        return bukkitConfig;
    }
}
