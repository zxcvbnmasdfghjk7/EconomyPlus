package io.github.eddiediamondfire.economyplus.command;

import org.bukkit.entity.Player;

import java.util.List;

public interface SubCommand {

    String getName();
    String getDescription();
    String getSyntax();

    boolean action(Player player, String[] args);

    List<String> onTabComplete(Player player, String[] args);
}
