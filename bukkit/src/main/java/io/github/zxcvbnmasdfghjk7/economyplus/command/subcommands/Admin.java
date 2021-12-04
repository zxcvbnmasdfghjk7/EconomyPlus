package io.github.zxcvbnmasdfghjk7.economyplus.command.subcommands;

import io.github.zxcvbnmasdfghjk7.economyplus.EconomyPlus;
import io.github.zxcvbnmasdfghjk7.economyplus.command.CommandManager;
import io.github.zxcvbnmasdfghjk7.economyplus.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Admin implements SubCommand {

    private EconomyPlus plugin;
    public Admin(CommandManager commandManager){
        this.plugin = commandManager.getPlugin();
    }

    @Override
    public String getName() {
        return "admin";
    }

    @Override
    public String getDescription() {
        return "Contains a lost of admin subcommands.";
    }

    @Override
    public String getSyntax() {
        return "/economyplus admin [set, add, remove]";
    }

    @Override
    public boolean action(Player player, String[] args) {
        return true;
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        if(args.length == 0){
            List<String> onTab = new ArrayList<>();
            onTab.add("set");
            onTab.add("add");
            onTab.add("remove");
            return onTab;
        }
        if(args[1].equalsIgnoreCase("set")){
            List<String> playersInServer = new ArrayList<>();

            for(Player players: Bukkit.getOnlinePlayers()){
                playersInServer.add(players.getName());
            }
            return playersInServer;
        }
        return null;
    }
}
