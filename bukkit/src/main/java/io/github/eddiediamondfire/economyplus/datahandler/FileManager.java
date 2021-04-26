package io.github.eddiediamondfire.economyplus.datahandler;

import com.moandjiezana.toml.Toml;
import io.github.eddiediamondfire.economyplus.EconomyPlus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileManager {

    private final EconomyPlus plugin;
    public FileManager(EconomyPlus plugin){
        this.plugin = plugin;
    }

    public Toml getTomlFile(String fileName){
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdirs();
        }

        File file = new File(plugin.getDataFolder(), fileName + ".toml");

        if(!file.exists()){
            try{
                Files.copy(plugin.getResource(fileName + ".toml"), file.toPath());
            }catch (IOException ex){
                plugin.getCustomLogger().error("An error occured file getting file", ex);
            }
        }
        return new Toml(new Toml().read(plugin.getResource(fileName + ".toml"))).read(file);
    }
}
