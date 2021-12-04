package io.github.zxcvbnmasdfghjk7.economyplus.config;

import io.github.zxcvbnmasdfghjk7.economyplus.EconomyPlus;
import io.github.zxcvbnmasdfghjk7.economyplus.config.file.Config;
import io.github.zxcvbnmasdfghjk7.economyplus.config.file.PluginMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileManager {
    private final EconomyPlus plugin;
    private List<YAML> yamlFiles = null;

    public FileManager(EconomyPlus plugin){
        this.plugin = plugin;
        yamlFiles = new ArrayList<>();
        yamlFiles.add(new Config(this));
        yamlFiles.add(new PluginMessage(this));
    }

    public void onLoad(){
       for(YAML file: yamlFiles){
           file.load();
       }
    }

    public void onDisable(){
        for(YAML file: yamlFiles){
            file.save();
        }
    }

    public EconomyPlus getPlugin() {
        return plugin;
    }

    public List<YAML> getYamlFiles() {
        return yamlFiles;
    }

    public YAML getFile(String fileName){
        for(YAML file: getYamlFiles()){
            if(Objects.equals(file.getFileName(), fileName)){
                return file;
            }
        }
        return null;
    }
}
