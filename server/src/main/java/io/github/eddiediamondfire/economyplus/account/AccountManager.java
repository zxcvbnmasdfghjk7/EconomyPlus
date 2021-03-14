package io.github.eddiediamondfire.economyplus.account;

import io.github.eddiediamondfire.economyplus.Main;
import io.github.eddiediamondfire.economyplus.currency.Currency;
import io.github.eddiediamondfire.economyplus.currency.CurrencyManager;
import io.github.eddiediamondfire.economyplus.data.Data;
import io.github.eddiediamondfire.economyplus.utils.MessageManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Getter
public class AccountManager {
    private List<Account> accounts;
    private final Main plugin;
    private Connection connection = null;
    public AccountManager(Main plugin){
        this.plugin = plugin;
        accounts = new ArrayList<>();
    }

    public Account getAccount(UUID playerUUID){
        for(Account account:accounts){
            if(account.getPlayerUUID().equals(playerUUID)){
                return account;
            }
        }
        return null;
    }

    public void createAccount(UUID playerUUID, String username){
        Account player = new Account(playerUUID, username);

        if(!(Bukkit.getPlayer(playerUUID).getDisplayName().equals(username)))
        {
            MessageManager.sendMessage(ChatColor.RED, "Username does not match online player's Username");
        }

        List<Currency> currencies = plugin.getCurrencyManager().getCurrencies();
        Map<Currency, Double> balance = player.getBalances();
        try
        {
            connection = plugin.getDatabase().getConnection();

            PreparedStatement accountStatement = connection.prepareStatement("INSERT INTO Accounts (UUID,Username) VALUES (?,?)");
            accountStatement.setString(1, playerUUID.toString());
            accountStatement.setString(2, username);
            accountStatement.execute();

            for(Currency currency:currencies){
                balance.put(currency, currency.getStartBalance());

                PreparedStatement balanceStatement = connection.prepareStatement("INSERT INTO Balances (UUID,Currency_ID,Balance) VALUES (?,?,?)");
                balanceStatement.setString(1, playerUUID.toString());
                balanceStatement.setString(2, currency.getId().toString());
                balanceStatement.setDouble(3, currency.getStartBalance());
                balanceStatement.execute();
            }
            accounts.add(player);
            connection.close();
        }catch (SQLException e)
        {
            MessageManager.sendMessage(ChatColor.RED, "An error occurred while adding player "+ Bukkit.getPlayer(playerUUID).getDisplayName() +" to Accounts Table in the Database");
            e.printStackTrace();
        }
    }

    // TODO WIP
    public void deleteAccount(UUID playerUUID){

    }

    public boolean accountExist(UUID playerUUID){
        for(Account account:accounts){
            if(account.getPlayerUUID().equals(playerUUID)){
                return true;
            }else{
                return accountExistDatabase(playerUUID);
            }
        }
        return accountExistDatabase(playerUUID);
    }

    // TODO Rewite how data is handled
    public boolean accountExistDatabase(UUID playerUUID)
    {
        try
        {
            connection = plugin.getDatabase().getConnection();

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Accounts WHERE UUID=?");
            statement.setString(1, playerUUID.toString());
            statement.execute();

            ResultSet result = statement.getResultSet();

            return result.next();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    // TODO Rewrite how data is handled
    // remove account when player leaves the game
    public void removeAccount(UUID playerUUID){

        Player entity = Bukkit.getPlayer(playerUUID);
        if(accountExist(playerUUID)){

        }

    }

    // TODO Rewrite how data is handled
    // Add account when the player joins the server.
    public void addAccount(UUID playerUUID){
        if(!accountExist(playerUUID)){
        }
    }
}
