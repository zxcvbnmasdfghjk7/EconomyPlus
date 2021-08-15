package io.github.eddiediamondfire.economyplus.storage.database;

import io.github.eddiediamondfire.economyplus.EconomyPlus;
import io.github.eddiediamondfire.economyplus.storage.Storage;
import io.github.eddiediamondfire.economyplus.storage.StorageMethod;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;

public class H2Database implements Storage, StorageMethod {

    private final EconomyPlus plugin;

    public H2Database(EconomyPlus plugin){
        this.plugin = plugin;
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;

        try{
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(EconomyPlus.connectionUrl);
        }catch (SQLException | ClassNotFoundException ex){
            plugin.getLogger().log(Level.WARNING, "An error occured while attempting to establish connection to the database", ex);
        }
        plugin.getLogger().info("Connection established successfully");
        return connection;
    }

    @Override
    public void initialiseDatabase() {
        Connection connection = getConnection();
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS PlayerEconomy(PLAYER_UUID varchar NOT NULL, PLAYER_NAME varchar, BALANCE double, PRIMARY KEY(PLAYER_UUID))");
            statement.execute();

            connection.close();
        }catch (SQLException ex){
            plugin.getLogger().log(Level.WARNING, "An error occured while attempting to establish connection to the database", ex);
        }
        plugin.getLogger().info("Task executed successfully");
    }

    @Override
    public void update(UUID playerUUID, double amount) {
        try{
            PreparedStatement statement = getConnection().prepareStatement("UPDATE PlayerEconomy SET BALANCE=? WHERE PLAYER_UUID=?");
            statement.setDouble(1, amount);
            statement.setString(2, playerUUID.toString());
            statement.execute();
            plugin.getLogger().info("Updated Player's Bank");
        }catch (SQLException ex){
            plugin.getLogger().log(Level.WARNING, "An error occurred while executing a task", ex);
        }
    }

    @Override
    public void update(String userName, double amount) {
        Player player = Bukkit.getPlayer(userName);
        try{
            PreparedStatement statement = getConnection().prepareStatement("UPDATE PlayerEconomy SET BALANCE=? WHERE PLAYER_UUID=?");
            statement.setDouble(1, amount);
            statement.setString(2, player.getUniqueId().toString());
            statement.execute();
            plugin.getLogger().info("Updated Player's Bank");
        }catch (SQLException ex){
            plugin.getLogger().log(Level.WARNING, "An error occurred while executing a task", ex);
        }
    }

    @Override
    public double getBalance(UUID playerUUID) {
        try{
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM PlayerEconomy WHERE PLAYER_UUID=?");
            statement.setString(1, playerUUID.toString());

            ResultSet results = statement.executeQuery();
            results.next();

            return results.getDouble("BALANCE");
        }catch (SQLException ex){
            plugin.getLogger().log(Level.WARNING, "An error occurred while executing a task", ex);
        }
        return 0;
    }

    @Override
    public double getBalance(String userName) {
        Player player = Bukkit.getPlayer(userName);
        try{
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM PlayerEconomy WHERE PLAYER_UUID=?");
            statement.setString(1, player.getUniqueId().toString());

            ResultSet results = statement.executeQuery();
            results.next();

            return results.getDouble("BALANCE");
        }catch (SQLException ex){
            plugin.getLogger().log(Level.WARNING, "An error occurred while executing a task", ex);
        }
        return 0;
    }

    @Override
    public boolean accountExist(UUID playerUUID) {
        try{
           PreparedStatement statement = plugin.getDatabase().getConnection().prepareStatement("SELECT * FROM PlayerEconomy WHERE PLAYER_UUID=?");
           statement.setString(1, playerUUID.toString());

           ResultSet results = statement.executeQuery();
           if(results.next()){
               return true;
           }
        }catch (SQLException ex){
            plugin.getLogger().log(Level.WARNING, "An error occurred while executing a task", ex);
        }
        return false;
    }

    @Override
    public boolean accountExist(String userName) {
        Player player = Bukkit.getPlayer(userName);
        try{
            PreparedStatement statement = plugin.getDatabase().getConnection().prepareStatement("SELECT * FROM PlayerEconomy WHERE PLAYER_UUID=?");
            statement.setString(1, player.getUniqueId().toString());

            ResultSet results = statement.executeQuery();
            if(results.next()){
                return true;
            }
        }catch (SQLException ex){
            plugin.getLogger().log(Level.WARNING, "An error occurred while executing a task", ex);
        }
        return false;
    }


    @Override
    public void createAccount(UUID playerUUID, String userName, double amount) {
        PreparedStatement statement = null;

        try{
            statement = plugin.getDatabase().getConnection().prepareStatement("INSERT INTO PlayerEconomy (PLAYER_UUID,PLAYER_NAME,BALANCE) VALUES (?,?,?)");
            statement.setString(1, playerUUID.toString());
            statement.setString(2, userName);
            statement.setDouble(3, amount);
            statement.execute();
        }catch (SQLException ex){
            plugin.getLogger().log(Level.WARNING, "An error occurred while executing a task", ex);
        }
    }

    @Override
    public UUID getPlayerAccountUUID(String username) {
        PreparedStatement statement = null;
        UUID accountUUID = null;
        try{
            statement = plugin.getDatabase().getConnection().prepareStatement("SELECT * FROM PlayerEconomy WHERE PLAYER_NAME=?");
            statement.setString(1, username);
            ResultSet results = statement.executeQuery();

            if(!results.next()){
                throw new SQLException("NO RESULTS!");
            }

            accountUUID = UUID.fromString(results.getString("PLAYER_UUID"));
            return accountUUID;
        }catch (SQLException ex){
            plugin.getLogger().log(Level.WARNING, "An error occurred while executing a task", ex);
        }
        return null;
    }
}
