package io.github.eddiediamondfire.economyplus.account;

import io.github.eddiediamondfire.economyplus.EconomyPlus;
import io.github.eddiediamondfire.economyplus.datahandler.H2DatabaseHandler;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountManager {
    private final EconomyPlus plugin;
    private final Map<UUID, PlayerAccount> playerAccounts;
    private final H2DatabaseHandler databaseHandler;
    private Connection connection = null;
    public AccountManager(EconomyPlus plugin) {
        this.plugin = plugin;
        playerAccounts = new HashMap<>();
        databaseHandler = plugin.getDatabaseHandler();

    }

    public PlayerAccount getAccount(UUID playerUUID){
        if(playerAccounts.containsKey(playerUUID)){
            return playerAccounts.get(playerUUID);
        }
        return null;
    }

    public void addPlayer(UUID playerUUID, String username){
        final Logger logger = plugin.getCustomLogger();
        if(!playerExists(playerUUID)){
            connection = databaseHandler.getConnection();
            try{
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM PlayerAccounts WHERE PlayerUUID=?");
                statement.setString(1, playerUUID.toString());
                ResultSet results = statement.executeQuery();
                results.next();

                String usernameInDatabase = results.getString("Username");
                if(!usernameInDatabase.equals(username)){
                    logger.info(username + " looks like they changed their username, updating database");

                    PreparedStatement updateUsername = connection.prepareStatement("UPDATE PlayerAccounts SET Username=? WHERE PlayerUUID=?");
                    updateUsername.setString(1, username);
                    updateUsername.setString(2, playerUUID.toString());
                    updateUsername.executeUpdate();
                }

                double accountAmount = results.getDouble("Amount");

                playerAccounts.put(playerUUID, new PlayerAccount(playerUUID, username, accountAmount));
            }catch (SQLException ex){
                plugin.getCustomLogger().error("An error occurred while executing a action on the database", ex);
            }
        }
    }

    public void removePlayer(UUID playerUUID){
        if(playerExists(playerUUID)){

        }
    }

    public boolean playerExists(UUID playerUUID){
        if(playerAccounts.containsKey(playerUUID)){
            return true;
        }else{
            connection = databaseHandler.getConnection();
            try{
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM PlayerAccounts WHERE UUID=?");
                statement.setString(1, playerUUID.toString());

                ResultSet results = statement.executeQuery();
                if(results.next()){
                    return true;
                }
            }catch (SQLException ex){
                plugin.getCustomLogger().error("An error occured while executing a action on the database",  ex);
            }
            return false;
        }
    }

    public Map<UUID, PlayerAccount> getPlayerAccounts() {
        return playerAccounts;
    }
}
