package io.github.eddiediamondfire.economyplus.listener

import io.github.eddiediamondfire.economyplus.EconomyPlus
import io.github.eddiediamondfire.economyplus.account.AccountManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class EconomyListener(plugin: EconomyPlus): Listener {

    private val accountManager: AccountManager = plugin.accountManager

    @EventHandler
    fun onPlayerLoginEvent(e: PlayerJoinEvent){
        val player: Player = e.player

        accountManager.addPlayer(player.uniqueId, player.displayName)
    }

    @EventHandler
    fun onPlayerLeaveEvent(event: PlayerQuitEvent){
        val player: Player = event.player

    }
}