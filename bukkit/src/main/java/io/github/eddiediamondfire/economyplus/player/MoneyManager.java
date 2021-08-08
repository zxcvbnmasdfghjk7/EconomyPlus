package io.github.eddiediamondfire.economyplus.player;

import io.github.eddiediamondfire.economyplus.EconomyPlus;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class MoneyManager {
    private final EconomyPlus plugin;
    private final HashMap<UUID, Player> playersBank;

    public MoneyManager(EconomyPlus plugin){
        this.plugin = plugin;
        playersBank = new HashMap<>();
    }

    public Player getPlayerBank(UUID playerUUID){
        if(playerBankExist(playerUUID)){
            return playersBank.get(playerUUID);
        }
        return null;
    }

    public void savePlayerBanks(){
        Connection connection = plugin.getDatabase().getConnection();
        PreparedStatement statement = null;
        for(Player player: playersBank.values()){
            UUID playerUUID = player.getPlayerUUID();
            double amount = player.getBank().get("Dollar");

            try{
                statement = connection.prepareStatement("UPDATE PlayerEconomy SET BALANCE=? WHERE PLAYER_UUID=?");
                statement.setDouble(1, amount);
                statement.setString(2, playerUUID.toString());
                statement.execute();
            }catch (SQLException ex){
                plugin.getLogger().log(Level.WARNING, "An error occurred while executing a task on the database!", ex);
            }
        }
    }

    public boolean playerBankExist(UUID playerUUID){
        return playersBank.containsKey(playerUUID);
    }

    public void loadPlayerAccount(UUID playerUUID){
        try{
            PreparedStatement statement = plugin.getDatabase().getConnection().prepareStatement("SELECT * FROM PlayerEconomy WHERE PLAYER_UUID=?");
            statement.setString(1, playerUUID.toString());
            ResultSet results = statement.executeQuery();
            results.next();

            double amount = results.getDouble("BALANCE");

            Player player = new Player(playerUUID, Bukkit.getPlayer(playerUUID).getName());
            player.getBank().put("Dollar", amount);

            this.playersBank.put(playerUUID, player);
            plugin.getLogger().info("Loaded Player Account!");
        }catch (SQLException ex){
            plugin.getLogger().log(Level.WARNING, "An error occurred while executing a task on the database!", ex);
        }
    }

    public EconomyPlus getPlugin() {
        return plugin;
    }

    public Map<UUID, Player> getPlayersBank() {
        return playersBank;
    }
}
