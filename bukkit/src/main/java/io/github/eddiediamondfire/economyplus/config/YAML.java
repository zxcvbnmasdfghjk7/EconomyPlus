package io.github.eddiediamondfire.economyplus.config;

import org.bukkit.configuration.file.FileConfiguration;

public interface YAML
{
    FileConfiguration getBukkitConfig();

    String getFileName();
    void load();

    void save();


}
