package io.github.eddiediamondfire.economyplus.listener

import io.github.eddiediamondfire.economyplus.Main
import io.github.eddiediamondfire.economyplus.account.AccountManager
import io.github.eddiediamondfire.economyplus.utils.MessageManager
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*
class EconomyListener(pluginClass: Main) : Listener {

    private var plugin: Main = pluginClass
    private var accountManager: AccountManager = plugin.accountManager
    private var messageManager: MessageManager = plugin.messageManager

    @EventHandler
    fun onPlayerJoinEvent(event: PlayerJoinEvent) {
        val player:Player = event.player
        val playerUUID: UUID = player.uniqueId

        if(accountManager.accountExist(playerUUID))
        {
            val playerName: String = player.displayName
            val uuid: String = player.uniqueId.toString()
            messageManager.sendDebugMessage(ChatColor.YELLOW, "Adding player $playerName with an ID of $uuid to the Player Base")
            accountManager.addAccount(playerUUID)
        }
    }

    @EventHandler
    fun onPlayerLeaveEvent(event: PlayerQuitEvent) {
        val player: Player = event.player
    }
}