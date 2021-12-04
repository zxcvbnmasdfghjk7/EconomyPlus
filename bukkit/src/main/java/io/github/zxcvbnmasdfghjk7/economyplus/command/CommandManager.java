package io.github.zxcvbnmasdfghjk7.economyplus.command;

import io.github.zxcvbnmasdfghjk7.economyplus.EconomyPlus;
import io.github.zxcvbnmasdfghjk7.economyplus.command.subcommands.Admin;
import io.github.zxcvbnmasdfghjk7.economyplus.command.subcommands.Balance;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements TabExecutor {

    private final EconomyPlus plugin;
    private final List<SubCommand> subCommands;
    public CommandManager(EconomyPlus plugin){
        this.plugin = plugin;
        this.subCommands = new ArrayList<>();
        this.subCommands.add(new Balance(this));
        this.subCommands.add(new Admin(this));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){

            if(args.length > 0){
                for(SubCommand subCommand: getSubCommands()){
                    if(args[0].equalsIgnoreCase(subCommand.getName())){
                        return subCommand.action(player, args);
                    }
                }
            }else{
                player.sendMessage(ChatColor.YELLOW + "Help:");
                player.sendMessage(ChatColor.YELLOW + "     Optional -> []");
                player.sendMessage(ChatColor.YELLOW + "     Mandatory -> <>");
                player.sendMessage(" ");
                for(SubCommand subCommand: subCommands){
                    player.sendMessage(ChatColor.YELLOW + " -> " + subCommand.getName() + " -> " + subCommand.getSyntax());
                }
                return true;
            }
        }else{
            sender.sendMessage(ChatColor.RED + "ERROR: Player command only!!");
            return false;
        }
        return false;
    }

    public EconomyPlus getPlugin() {
        return plugin;
    }

    public List<SubCommand> getSubCommands() {
        return subCommands;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(args.length == 1){
            List<String> argsInOnTab = new ArrayList<>();

            for (SubCommand subCommand : subCommands) {
                argsInOnTab.add(subCommand.getName());
            }
            return argsInOnTab;
        }else if(args.length >= 2){
            for(SubCommand subCommand: subCommands){
                if(args[0].equalsIgnoreCase(subCommand.getName())){
                    Player player = (Player) sender;
                    return subCommand.onTabComplete(player, args);
                }
            }
        }
        return null;
    }

}
