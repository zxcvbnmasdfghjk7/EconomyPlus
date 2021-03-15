package io.github.eddiediamondfire.economyplus.commands

import org.bukkit.entity.Player
import org.jetbrains.annotations.NotNull

interface SubCommand {

    val name: String

    val syntax: String

    val description: String

    fun onCommand(player: Player, args: Array<String>): Boolean
}