package io.github.zxcvbnmasdfghjk7.economyplus.events;

import io.github.zxcvbnmasdfghjk7.economyplus.EconomyPlus;
import io.github.zxcvbnmasdfghjk7.economyplus.player.MoneyManager;
import io.github.zxcvbnmasdfghjk7.economyplus.storage.StorageMethod;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

public class ServerListeners implements Listener
{
    private final EconomyPlus plugin;
    private StorageMethod storageMethod = null;
    public ServerListeners(EconomyPlus plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        MoneyManager moneyManager = plugin.getMoneyManager();
        storageMethod = plugin.getDatabaseStorageMethod();

        if(!storageMethod.accountExist(player.getUniqueId())){
            plugin.getLogger().info("Player Account does not exist! \n Creating a new Account for this player " + player.getDisplayName());

            FileConfiguration config = plugin.getConfigFile();
            storageMethod.createAccount(player.getUniqueId(), player.getName(), plugin.getCurrencyManager().getCurrenciesAsList());



        }
        moneyManager.loadPlayerAccount(event.getPlayer().getUniqueId());

        if(!player.getName().equals(moneyManager.getPlayersBank().get(player.getUniqueId()).getUserName())){
            plugin.getLogger().info("Player has changed their name! /n Updating!");

            try{
                PreparedStatement statement = plugin.getDatabase().getConnection().prepareStatement("UPDATE PlayerEconomy SET PLAYER_NAME=? WHERE PLAYER_UUID=?");
                statement.setString(1, player.getName());
                statement.setString(2, player.getUniqueId().toString());
                statement.execute();
            }catch (SQLException ex){
                plugin.getLogger().log(Level.WARNING, "An error occurred while executing a task on the database!", ex);
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        MoneyManager moneyManager = plugin.getMoneyManager();
        storageMethod = plugin.getDatabaseStorageMethod();

        io.github.zxcvbnmasdfghjk7.economyplus.player.Player playerAccount = moneyManager.getPlayerBank(player.getUniqueId());

        try{
            PreparedStatement statement = plugin.getDatabase().getConnection().prepareStatement("UPDATE PlayerEconomy SET BALANCE=? WHERE PLAYER_UUID=?");
            statement.setDouble(1, playerAccount.getBank().get("Dollar"));
            statement.setString(2, player.getUniqueId().toString());
            statement.execute();
        }catch (SQLException ex){
            plugin.getLogger().log(Level.WARNING, "An error occurred while executing a task on the database!", ex);
        }
    }
}
