package io.github.eddiediamondfire.economyplus.commands;

import org.bukkit.entity.Player;

public interface SubCommand
{
    String getName();

    String getSyntax();

    String getDescription();

    boolean onCommand(Player player, String[] args);

}
