package io.github.eddiediamondfire.economyplus.command.subcommands;

import io.github.eddiediamondfire.economyplus.EconomyPlus;
import io.github.eddiediamondfire.economyplus.command.CommandManager;
import io.github.eddiediamondfire.economyplus.command.SubCommand;
import io.github.eddiediamondfire.economyplus.player.MoneyManager;
import io.github.eddiediamondfire.economyplus.vault.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.h2.command.Command;

import java.util.List;

public class Balance implements SubCommand {

    private final EconomyPlus plugin;
    public Balance(CommandManager commandManager){
        this.plugin = commandManager.getPlugin();
    }

    @Override
    public String getName() {
        return "balance";
    }

    @Override
    public String getDescription() {
        return "Displays the player's balance";
    }

    @Override
    public String getSyntax() {
        return "/economyplus balance [Player]";
    }

    @Override
    public boolean action(Player player, String[] args) {
        if(args.length > 1){
            Player target = Bukkit.getPlayer(args[1]);

            if(target == null){
                player.sendMessage(ChatColor.RED + "ERROR: Player " + args[1] + " does not exist!");
                player.sendMessage(ChatColor.RED + "Key: Optional -> []");
                player.sendMessage(ChatColor.RED + "     Compulsory -> <>");
                player.sendMessage(ChatColor.RED + "Usage: /economyplus balance [Player]");
                return false;
            }

            MoneyManager moneyManager = plugin.getMoneyManager();

            double balance = moneyManager.getPlayerBank(target.getUniqueId()).getBank().get("Dollar");
            player.sendMessage(ChatColor.GREEN + target.getName() + " has " + balance);
        }else{
            MoneyManager moneyManager = plugin.getMoneyManager();

            double balance = moneyManager.getPlayerBank(player.getUniqueId()).getBank().get("Dollar");
            player.sendMessage(ChatColor.GREEN + player.getName() + " has " + balance);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return null;
    }
}
