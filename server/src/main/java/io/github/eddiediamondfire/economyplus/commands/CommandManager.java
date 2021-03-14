package io.github.eddiediamondfire.economyplus.commands;

import io.github.eddiediamondfire.economyplus.Main;
import io.github.eddiediamondfire.economyplus.commands.subcommands.Balance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor {
    private final List<SubCommand> subCommands;
    private final Main plugin;
    public CommandManager(Main plugin){
        this.subCommands = new ArrayList<>();
        this.plugin = plugin;
        subCommands.add(new Balance(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if(args.length > 0){
            for(int i = 0; i < subCommands.size(); i++){
                if(args[0].equalsIgnoreCase(subCommands.get(i).getName())){
                    subCommands.get(i).onCommand(player, args);
                }
            }
        }
        return false;
    }
}
