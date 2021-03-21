package io.github.eddiediamondfire.economyplus.data.database;

import io.github.eddiediamondfire.economyplus.Main;
import io.github.eddiediamondfire.economyplus.data.Data;
import io.github.eddiediamondfire.economyplus.utils.MessageManager;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class H2Database implements Data {
    private Connection connection = null;
    private final Main plugin;
    private final String connectionUrl;
    private PreparedStatement statement = null;
    public H2Database(Main plugin)
    {
        this.plugin = plugin;
        connectionUrl = "jdbc:h2:" + plugin.getDataFolder().getAbsolutePath() + "/data/database.db";
    }

    @Override
    public void initaliseDatabase(){


        try{
            connection = this.getConnection();
            MessageManager.sendMessage(ChatColor.YELLOW, "Looking for Currency Table");
            if(!tableExist("CURRENCIES"))
            {
                MessageManager.sendMessage(ChatColor.RED, "Currency Table does not exist, creating one.");
                statement = connection.prepareStatement("CREATE TABLE Currencies (" +
                        "UUID varchar(255) NOT NULL, " +
                        "Plural varchar(20), " +
                        "Singular varchar(20), " +
                        "isDecimal bool, " +
                        "isExchangable bool, " +
                        "isDefault bool, " +
                        "StartBalance mediumint, " +
                        "Symbol varchar(1), " +
                        "CurrencyColor varchar(18), " +
                        "PRIMARY KEY (UUID)" +
                        ");");

                statement.execute();
            }
            if(!tableExist("ACCOUNTS"))
            {
                statement = connection.prepareStatement("CREATE TABLE Accounts (" +
                        "UUID varchar(255)," +
                        "Username varchar(18)," +
                        "PRIMARY KEY (UUID));");

                statement.execute();
            }

            if(!tableExist("BALANCES"))
            {
                statement = connection.prepareStatement("CREATE TABLE Balances (" +
                        "UUID varchar(255)," +
                        "Currency_ID varchar(255)," +
                        "Balance double(54)," +
                        "PRIMARY KEY (UUID));");
                statement.execute();
            }

            connection.close();
        }catch (SQLException e){
            MessageManager.sendMessage(ChatColor.RED, "An error occured while performing actions with SQL system.");
            e.printStackTrace();
        }

    }


    @Override
    public void saveDatabase(){

    }

    @Override
    public boolean tableExist(@NotNull String tableName)
    {
        try
        {
            DatabaseMetaData databaseMetaData = getConnection().getMetaData();
            ResultSet result = databaseMetaData.getTables(null, null, tableName, null);

            return result.next();
        }catch (SQLException e)
        {
            MessageManager.sendMessage(ChatColor.RED, "An error occured while executing a Query!");
            e.printStackTrace();
        }
        return false;
    }

    @NotNull
    @Override
    public Connection getConnection(){
        try
        {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(connectionUrl);
        }catch (SQLException | ClassNotFoundException e)
        {
            MessageManager.sendMessage(ChatColor.RED, "An error occured while connecting to a database!");
            e.printStackTrace();
        }
        return connection;
    }
}
