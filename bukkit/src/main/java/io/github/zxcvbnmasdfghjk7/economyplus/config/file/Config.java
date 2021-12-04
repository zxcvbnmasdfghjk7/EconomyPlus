package io.github.zxcvbnmasdfghjk7.economyplus.config.file;

import io.github.zxcvbnmasdfghjk7.economyplus.EconomyPlus;
import io.github.zxcvbnmasdfghjk7.economyplus.config.FileManager;
import io.github.zxcvbnmasdfghjk7.economyplus.config.YAML;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config implements YAML
{
    private final EconomyPlus plugin;
    private FileConfiguration fileConfiguration = null;

    public Config(FileManager fileManager){
        this.plugin = fileManager.getPlugin();
    }

    @Override
    public FileConfiguration getBukkitConfig() {
        return fileConfiguration;
    }

    @Override
    public String getFileName() {
        return "config.yml";
    }

    @Override
    public void load() {
        File file = new File(plugin.getDataFolder() + "/" + getFileName());

        if(!file.exists()){
            file.getParentFile().mkdir();
            plugin.saveResource(getFileName(), false);
        }

        fileConfiguration = new YamlConfiguration();

        try{
            fileConfiguration.load(file);
        }catch (IOException | InvalidConfigurationException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void save() {

    }
}
