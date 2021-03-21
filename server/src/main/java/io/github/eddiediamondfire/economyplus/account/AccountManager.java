package io.github.eddiediamondfire.economyplus.account;

import io.github.eddiediamondfire.economyplus.Main;
import io.github.eddiediamondfire.economyplus.currency.Currency;
import io.github.eddiediamondfire.economyplus.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AccountManager {
    private final List<Account> accounts;
    private final Main plugin;
    private Connection connection = null;
    private PreparedStatement statement = null;
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

    public boolean accountExistDatabase(UUID playerUUID)
    {
        try
        {
            connection = plugin.getDatabase().getConnection();

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ACCOUNTS WHERE UUID=?");
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
        try
        {
            Player entity = Bukkit.getPlayer(playerUUID);
            connection = plugin.getDatabase().getConnection();
            List<Currency> currencies = plugin.getCurrencyManager().getCurrencies();
            Account account = getAccount(playerUUID);
            Map<Currency, Double> balance = account.getBalances();

            if(accountExist(playerUUID)){
                statement = connection.prepareStatement("SELECT * FROM BALANCES WHERE UUID=?");
                statement.setString(1, entity.getUniqueId().toString());

                ResultSet results = statement.executeQuery();
                results.next();

                
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // TODO Rewrite how data is handled
    // Add account when the player joins the server.
    public void addAccount(UUID playerUUID){
        if(!accountExist(playerUUID)){
        }
    }

    public List<Account> getAccounts(){
        return accounts;
    }
}
