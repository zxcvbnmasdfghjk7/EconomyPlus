package io.github.eddiediamondfire.economyplus.listener

import io.github.eddiediamondfire.economyplus.Main
import io.github.eddiediamondfire.economyplus.account.AccountManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

class EconomyListener(val plugin: Main) : Listener {

    private val accountManager: AccountManager = plugin.accountManager

    @EventHandler
    fun onPlayerJoinEvent(event: PlayerJoinEvent) {
        val player:Player = event.player
        val playerUUID: UUID = player.uniqueId

        if(accountManager.accountExist(playerUUID))
        {
            accountManager.addAccount(playerUUID)
        }
    }

    @EventHandler
    fun onPlayerLeaveEvent(event: PlayerQuitEvent) {
        val player: Player = event.player
    }
}