package io.github.zxcvbnmasdfghjk7.economyplus.storage.database;

import io.github.zxcvbnmasdfghjk7.economyplus.EconomyPlus;
import io.github.zxcvbnmasdfghjk7.economyplus.currency.Currency;
import io.github.zxcvbnmasdfghjk7.economyplus.storage.Storage;
import io.github.zxcvbnmasdfghjk7.economyplus.storage.StorageMethod;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.sql.*;
import java.util.List;
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

    // TODO fix SQL maybe.
    @Override
    public void initialiseDatabase() {
        Connection connection = getConnection();
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS PlayerEconomy" +
                    "(PLAYER_UUID varchar NOT NULL, " +
                    "PLAYER_NAME varchar, " +
                    "CURRENCY_ID varchar, " +
                    "BALANCE double, " +
                    "PRIMARY KEY(PLAYER_UUID))");
            statement.execute();

            PreparedStatement currencyCreateStatement =
                    connection.prepareStatement("CREATE TABLE IF NOT EXISTS Currency" +
                            "(CURRENCY_ID varchar NOT NULL, " +
                            "CURRENCY_NAME varchar, " +
                            "CURRENCY_SYMBOL varchar, " +
                            "CURRENCY_STARTING_BALANCE double, " +
                            "DEFAULT_CURRENCY boolean, " +
                            "CURRENCY_COLOUR varchar, " +
                            "PRIMARY KEY(CURRENCY_ID))");

            currencyCreateStatement.execute();
            connection.close();
        }catch (SQLException ex){
            plugin.getLogger().log(Level.WARNING, "An error occured while attempting to establish connection to the database", ex);
        }
        plugin.getLogger().info("Task executed successfully");
    }

    @Override
    public void update(UUID playerUUID, double amount, Currency currency) {
        try{
            PreparedStatement statement = getConnection().prepareStatement("UPDATE PlayerEconomy SET BALANCE=? WHERE PLAYER_UUID=? AND CURRENCY_ID=?");
            statement.setDouble(1, amount);
            statement.setString(2, playerUUID.toString());
            statement.setString(3, currency.getCurrencyID().toString());
            statement.execute();
            plugin.getLogger().info("Updated Player's Bank");
        }catch (SQLException ex){
            plugin.getLogger().log(Level.WARNING, "An error occurred while executing a task", ex);
        }
    }

    @Override
    public void update(String userName, double amount, Currency currency) {
        Player player = Bukkit.getPlayer(userName);
        try{
            PreparedStatement statement = getConnection().prepareStatement("UPDATE PlayerEconomy SET BALANCE=? WHERE PLAYER_UUID=? AND CURRENCY_ID=?");
            statement.setDouble(1, amount);
            statement.setString(2, player.getUniqueId().toString());
            statement.setString(3, currency.getCurrencyID().toString());
            statement.execute();
            plugin.getLogger().info("Updated Player's Bank");
        }catch (SQLException ex){
            plugin.getLogger().log(Level.WARNING, "An error occurred while executing a task", ex);
        }
    }

    @Override
    public double getBalance(UUID playerUUID, Currency currency) {
        try{
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM PlayerEconomy WHERE PLAYER_UUID=? AND CURRENCY_ID=?");
            statement.setString(1, playerUUID.toString());
            statement.setString(2, currency.getCurrencyID().toString());
            ResultSet results = statement.executeQuery();
            results.next();

            return results.getDouble("BALANCE");
        }catch (SQLException ex){
            plugin.getLogger().log(Level.WARNING, "An error occurred while executing a task", ex);
        }
        return 0;
    }

    @Override
    public double getBalance(String userName, Currency currency) {
        Player player = Bukkit.getPlayer(userName);
        try{
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM PlayerEconomy WHERE PLAYER_UUID=? AND CURRENCY_ID=?");
            statement.setString(1, player.getUniqueId().toString());
            statement.setString(2, currency.getCurrencyID().toString());
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
    public void createAccount(UUID playerUUID, String userName, List<Currency> currencies) {
        PreparedStatement statement = null;

        try{
            for(Currency currency: currencies){
                statement = plugin.getDatabase().getConnection().prepareStatement("INSERT INTO PlayerEconomy (PLAYER_UUID,PLAYER_NAME,CURRENCY_ID,BALANCE) VALUES (?,?,?,?)");
                statement.setString(1, playerUUID.toString());
                statement.setString(2, userName);
                statement.setString(3, currency.getCurrencyID().toString());
                statement.setDouble(4, currency.getStartingBalance());
                statement.execute();
            }
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

    @Override
    public void createCurrency(String currencyName) {
        double defaultStartingBalance = plugin.getConfigFile().getDouble("new_currency_config.default_starting_balance");
        boolean defaultCurrency = currencyTableIsEmpty();

        Currency currency = new Currency(currencyName, defaultStartingBalance, defaultCurrency);

        try{
            PreparedStatement s = getConnection().prepareStatement("INSERT INTO Currency " +
                    "(CURRENCY_ID,CURRENCY_NAME,CURRENCY_SYMBOL,CURRENCY_STARTING_BALANCE,DEFAULT_CURRENCY,CURRENCY_COLOUR) VALUES (?,?,?,?,?,?)");
            s.setString(1, currency.getCurrencyID().toString());
            s.setString(2, currency.getName());
            s.setString(3, String.valueOf(currency.getSymbol()));
            s.setDouble(4, defaultStartingBalance);
            s.setBoolean(5, defaultCurrency);
            s.setString(6, currency.getCurrencyColour().toString());
            s.executeUpdate();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private boolean currencyTableIsEmpty(){
        try{
            Statement statement = getConnection().createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM Currency");

            if(!results.next()){
                return true;
            }else{
                return false;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public Currency getCurrency(String currencyName) {

        try{
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM Currency WHERE CURRENCY_NAME=?");
            statement.setString(1, currencyName);

            ResultSet results = statement.executeQuery();

            Currency currency = new Currency(results.getString("CURRENCY_NAME"),
                    UUID.fromString(results.getString("CURRENCY_ID")),
                    results.getString("CURRENCY_SYMBOL").charAt(0),
                    results.getDouble("CURRENCY_STARTING_BALANCE"),
                    ChatColor.valueOf(results.getString("CURRENCY_COLOUR")),
                    results.getBoolean("DEFAULT_CURRENCY"));
            return currency;
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Currency getCurrency(UUID currencyID) {
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM Currency WHERE CURRENCY_ID=?");
            statement.setString(1, currencyID.toString());

            ResultSet results = statement.executeQuery();

            return new Currency(results.getString("CURRENCY_NAME"),
                    UUID.fromString(results.getString("CURRENCY_ID")),
                    results.getString("CURRENCY_SYMBOL").charAt(0),
                    results.getDouble("CURRENCY_STARTING_BALANCE"),
                    ChatColor.valueOf(results.getString("CURRENCY_COLOUR")),
                    results.getBoolean("DEFAULT_CURRENCY"));
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Currency getDefaultCurrency() {
        try{
            PreparedStatement statement = getConnection().prepareStatement("SELECT *  FROM Currency WHERE DEFAULT_CURRENCY=?");
            statement.setBoolean(1, true);

            ResultSet results = statement.executeQuery();

            UUID currencyID = UUID.fromString(results.getString("CURRENCY_ID"));
            getConnection().close();

            PreparedStatement getDataCurrency = getConnection().prepareStatement("SELECT * FROM Currency WHERE CURRENCY_ID=?");
            getDataCurrency.setString(1, currencyID.toString());
            ResultSet rs = getDataCurrency.executeQuery();

            return new Currency(rs.getString("CURRENCY_NAME"),
                    UUID.fromString(rs.getString("CURRENCY_ID")),
                    rs.getString("CURRENCY_SYMBOL").charAt(0),
                    rs.getDouble("CURRENCY_STARTING_BALANCE"),
                    ChatColor.valueOf(rs.getString("CURRENCY_COLOUR")),
                    rs.getBoolean("DEFAULT_CURRENCY"));
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean currencyExists(String currencyName) {
        try{
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM Currency WHERE CURRENCY_NAME=?");
            statement.setString(1, currencyName);

            ResultSet results = statement.executeQuery();

            if(results.next()){
                return true;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean currencyExists(UUID currencyID) {
        try{
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM Currency WHERE CURRENCY_ID=?");
            statement.setString(1, currencyID.toString());

            ResultSet results = statement.executeQuery();

            if(results.next()){
                return true;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }
}
