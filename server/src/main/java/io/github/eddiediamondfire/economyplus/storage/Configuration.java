package io.github.eddiediamondfire.economyplus.storage;

import io.github.eddiediamondfire.economyplus.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Configuration implements AbstractFile{
    private final Main plugin;
    private final File file;
    private final FileConfiguration manager;
    public Configuration(Main plugin){
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder() + "/" + getFileName());
        this.manager = new YamlConfiguration();
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

        // set backup period
        if(!(manager.getInt("backupPeriod") <= 0)) {
            plugin.setBackupPeriod(manager.getInt("backupPeriod"), true);
        }else{
            plugin.setBackupPeriod(0, false);
        }




    }

    @Override
    public void saveFiles() {
        try{
            manager.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public String getFileName() {
        return "config.yml";
    }

    @Override
    public FileConfiguration getManager() {
        return manager;
    }
}
