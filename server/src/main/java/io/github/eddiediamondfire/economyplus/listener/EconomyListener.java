package io.github.eddiediamondfire.economyplus.listener;

import io.github.eddiediamondfire.economyplus.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EconomyListener implements Listener {
    private final Main plugin;
    public EconomyListener(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if(plugin.getAccountManager().accountExist(player.getUniqueId())){
            plugin.getAccountManager().addAccount(player.getUniqueId());
        }
    }

    @EventHandler
    public void onLeaveEvent(PlayerQuitEvent event){
        Player player = event.getPlayer();
    }

}
