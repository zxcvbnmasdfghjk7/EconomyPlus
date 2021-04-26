package io.github.eddiediamondfire.economyplus.datahandler;

import io.github.eddiediamondfire.economyplus.EconomyPlus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class H2DatabaseHandler {

    private final EconomyPlus plugin;
    public H2DatabaseHandler(EconomyPlus plugin){
        this.plugin = plugin;
    }

    public void initialiseDatabase(){
        PreparedStatement statement = null;

        try{
            statement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS PlayerAccounts(PlayerUUID varchar NOT NULL UNIQUE, Username varchar NOT NULL, Amount double NOT NULL)");
            statement.execute();
        }catch (SQLException ex){
            plugin.getCustomLogger().error("An error occured while executing an action in the database", ex);
        }
    }

    public Connection getConnection(){
        Connection connection = null;

        try{
            connection = DriverManager.getConnection("jdbc:h2:" + plugin.getDataFolder().getAbsolutePath() + "/data/accounts");
        }catch(SQLException ex){
            plugin.getCustomLogger().error("An error occured while connecting to the database", ex);
        }
        return connection;
    }
}
