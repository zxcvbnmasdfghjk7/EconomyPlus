package io.github.zxcvbnmasdfghjk7.economyplus.command.subcommands;

import io.github.zxcvbnmasdfghjk7.economyplus.EconomyPlus;
import io.github.zxcvbnmasdfghjk7.economyplus.command.CommandManager;
import io.github.zxcvbnmasdfghjk7.economyplus.command.SubCommand;
import org.bukkit.entity.Player;

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
            String playerName = args[1];
            io.github.zxcvbnmasdfghjk7.economyplus.player.Player playerAccount = plugin.getMoneyManager().getPlayer(player.getName());

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return null;
    }
}
