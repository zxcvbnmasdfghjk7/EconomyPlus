package io.github.eddiediamondfire.economyplus.utils;

import io.github.eddiediamondfire.economyplus.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class MessageManager {

    private Main plugin = null;
    private boolean debugMode;
    public MessageManager(Main plugin)
    {
        this.plugin = plugin;
    }
    public static void sendMessage(Player player, ChatColor colour, String message){
        player.sendMessage(colour + message);
    }

    public static void sendMessage(ChatColor colour, String message){
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        console.sendMessage(colour + message);
    }

    public static void sendDebugMessage(ChatColor colour, String message){

    }
}