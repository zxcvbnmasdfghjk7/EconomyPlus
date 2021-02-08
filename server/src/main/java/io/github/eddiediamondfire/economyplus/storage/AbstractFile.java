package io.github.eddiediamondfire.economyplus.storage;

import org.bukkit.configuration.file.FileConfiguration;

public interface AbstractFile {

    void loadFiles();
    void saveFiles();

    String getFileName();
    FileConfiguration getManager();
}
