package io.github.zxcvbnmasdfghjk7.economyplus.config;

import org.bukkit.configuration.file.FileConfiguration;

public interface YAML
{
    FileConfiguration getBukkitConfig();

    String getFileName();
    void load();

    void save();


}
