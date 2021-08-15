package io.github.eddiediamondfire.economyplus.command.subcommands;

import io.github.eddiediamondfire.economyplus.EconomyPlus;
import io.github.eddiediamondfire.economyplus.command.CommandManager;
import io.github.eddiediamondfire.economyplus.command.SubCommand;
import io.github.eddiediamondfire.economyplus.player.MoneyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
        if(args.length > 1){
            if(args[1].equalsIgnoreCase("set")){
                if(args.length > 2){
                    String username = args[2];
                    double amount = Double.parseDouble(args[3]);

                    MoneyManager moneyManager = plugin.getMoneyManager();

                    if(!moneyManager.playerBankExist(username)){
                        player.sendMessage(ChatColor.RED + "ERROR: Player " + username + " does not exist!");
                        player.sendMessage(ChatColor.RED + "Key: Optional -> []");
                        player.sendMessage(ChatColor.RED + "     Compulsory -> <>");
                        player.sendMessage(ChatColor.RED + "Usage: /economyplus admin set <Player> <Amount>");
                    }

                    moneyManager.update(username, amount);
                    player.sendMessage(ChatColor.GREEN + "SUCCESS: Set Player " + username + "'s balance to " + amount);
                }else{
                    player.sendMessage(ChatColor.RED + "ERROR: Not enough arguments");
                    player.sendMessage(ChatColor.RED + "Key: Optional -> []");
                    player.sendMessage(ChatColor.RED + "     Compulsory -> <>");
                    player.sendMessage(ChatColor.RED + "Usage: /economyplus admin set <Player> <Amount>");
                }
            }else if(args[1].equalsIgnoreCase("add")){
                if(args.length > 2){
                    String username = args[2];
                    double amount = Double.parseDouble(args[3]);

                    MoneyManager moneyManager = plugin.getMoneyManager();
                    if(!moneyManager.playerBankExist(username)){
                        player.sendMessage(ChatColor.RED + "ERROR: Player " + username + " does not exist!");
                        player.sendMessage(ChatColor.RED + "Key: Optional -> []");
                        player.sendMessage(ChatColor.RED + "     Compulsory -> <>");
                        player.sendMessage(ChatColor.RED + "Usage: /economyplus admin add <Player> <Amount>");
                    }

                    double oldAmount, newAmount;

                    oldAmount = moneyManager.getBalance(username);

                    newAmount = oldAmount + amount;
                    moneyManager.update(username, newAmount);
                    player.sendMessage(ChatColor.GREEN + "SUCCESS: Added " + newAmount + " to " + username + "'s account!");
                }else{
                    player.sendMessage(ChatColor.RED + "ERROR: Not enough arguments");
                    player.sendMessage(ChatColor.RED + "Key: Optional -> []");
                    player.sendMessage(ChatColor.RED + "     Compulsory -> <>");
                    player.sendMessage(ChatColor.RED + "Usage: /economyplus admin add <Player> <Amount>");
                }
            }else if(args[1].equalsIgnoreCase("remove")){
                if(args.length > 2){
                    String username = args[2];
                    double amount = Double.parseDouble(args[3]);

                    MoneyManager moneyManager = plugin.getMoneyManager();
                    if(!moneyManager.playerBankExist(username)){
                        player.sendMessage(ChatColor.RED + "ERROR: Player " + username + " does not exist!");
                        player.sendMessage(ChatColor.RED + "Key: Optional -> []");
                        player.sendMessage(ChatColor.RED + "     Compulsory -> <>");
                        player.sendMessage(ChatColor.RED + "Usage: /economyplus admin remove <Player> <Amount>");
                        return true;
                    }

                    double oldAmount, newAmount;

                    oldAmount = moneyManager.getBalance(username);

                    if(oldAmount < amount){
                        player.sendMessage(ChatColor.RED + "ERRPR: Player has " + amount + " more than " + oldAmount);
                        return true;
                    }
                    newAmount = oldAmount - amount;
                    moneyManager.update(username, newAmount);
                    player.sendMessage(ChatColor.GREEN + "SUCCESS: Removed " + newAmount + " to " + username + "'s account!");
                }else{
                    player.sendMessage(ChatColor.RED + "ERROR: Not enough arguments");
                    player.sendMessage(ChatColor.RED + "Key: Optional -> []");
                    player.sendMessage(ChatColor.RED + "     Compulsory -> <>");
                    player.sendMessage(ChatColor.RED + "Usage: /economyplus admin remove <Player> <Amount>");
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return null;
    }
}
