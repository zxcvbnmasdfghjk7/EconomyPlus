package io.github.eddiediamondfire.economyplus.commands.subcommands;

import io.github.eddiediamondfire.economyplus.Main;
import io.github.eddiediamondfire.economyplus.commands.SubCommand;
import org.bukkit.entity.Player;

public class Balance implements SubCommand {
    private final Main plugin;
    public Balance(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "balance";
    }

    @Override
    public String getSyntax() {
        return "/economyplus balance <currency> [player]";
    }

    @Override
    public String getDescription() {
        return "Views how much does the player's account have";
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length > 1){

        }
        return false;
    }
}
