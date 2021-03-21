package io.github.eddiediamondfire.economyplus.utils

import io.github.eddiediamondfire.economyplus.Main
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

class MessageManager constructor(pluginClass: Main, debugMode : Boolean) {

    private val plugin : Main = pluginClass

    private val debug : Boolean = debugMode

    companion object {
        @JvmStatic
        fun sendMessage(player: Player, colour: ChatColor, message: String){
            player.sendMessage("$colour $message");
        }

        @JvmStatic
        fun sendMessage(colour : ChatColor, message: String){
            val console: ConsoleCommandSender = Bukkit.getConsoleSender()
            console.sendMessage("$colour $message")
        }
    }

    fun sendDebugMessage(player: Player, colour: ChatColor, message: String)
    {
        if(debug){
            player.sendMessage("$colour $message")
        }
    }

    fun sendDebugMessage(colour: ChatColor, message: String)
    {
        val console: ConsoleCommandSender = Bukkit.getConsoleSender()
        if(debug){
            console.sendMessage("$colour $message")
        }
    }

}