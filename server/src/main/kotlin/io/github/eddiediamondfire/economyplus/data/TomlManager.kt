package io.github.eddiediamondfire.economyplus.data

import com.moandjiezana.toml.Toml
import io.github.eddiediamondfire.economyplus.Main
import java.io.File
import java.io.IOException
import java.nio.file.Files

class TomlManager(pluginClass: Main) {
    private val plugin: Main = pluginClass

    @SuppressWarnings("ResultOfMethodCallIgnored")
    fun loadTomlFile(configName: String) : Toml
    {
        val dataFolder: File = plugin.dataFolder

        if(!dataFolder.exists())
        {
            dataFolder.mkdirs()
        }
        val file: File = File(dataFolder, configName)

        if(!file.exists())
        {
            try {
                Files.copy(plugin.getResource(configName), file.toPath())
            }catch (ex: IOException)
            {
            }
        }
        return Toml().read(plugin.getResource(configName)).read(file)
    }

    fun isDebugEnabled(): Boolean {
        val config: Toml = plugin.configurationFile
        val developerSettings: Toml = config.getTable("developer")

        return developerSettings.getBoolean("debug_mode")
    }


}