package io.github.zxcvbnmasdfghjk7.economyplus.player;

import io.github.zxcvbnmasdfghjk7.economyplus.EconomyPlus;
import io.github.zxcvbnmasdfghjk7.economyplus.currency.Currency;
import io.github.zxcvbnmasdfghjk7.economyplus.storage.StorageMethod;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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

        try{
            for(Player player: this.playersBank.values()){
                for(Currency currency: plugin.getCurrencyManager().getCurrenciesAsList()){
                    statement = connection.prepareStatement("UPDATE PlayerEconomy SET BALANCE=? WHERE PLAYERUUID=? AND CURRENCY_ID=?");
                    statement.setDouble(1, player.getBank().get(currency));
                    statement.setString(2, player.getPlayerUUID().toString());
                    statement.setString(3, currency.getCurrencyID().toString());
                    statement.executeUpdate();
                }
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    public boolean playerBankExist(UUID playerUUID) {
        if(!playersBank.containsKey(playerUUID)){
            return plugin.getDatabaseStorageMethod().accountExist(playerUUID);
        }
        return true;
    }

    public boolean playerBankExist(String username){
        for(Player player: playersBank.values()){
            if(player.getUserName().equals(username)){
                return true;
            }
        }
        return plugin.getDatabaseStorageMethod().accountExist(username);
    }

    public void update(UUID playerUUID, double amount, Currency currency){
        Player player = getPlayerBank(playerUUID);
        player.getBank().replace(currency, amount);
        StorageMethod storageMethod = plugin.getDatabaseStorageMethod();
        storageMethod.update(playerUUID, amount, currency);
    }

    public void update(String playerName, double amount, Currency currency){
        StorageMethod storageMethod = plugin.getDatabaseStorageMethod();
        UUID playerUUID = storageMethod.getPlayerAccountUUID(playerName);

        Player player = getPlayerBank(playerUUID);
        player.getBank().replace(currency, amount);
        storageMethod.update(playerUUID, amount, currency);
    }

    public double getBalance(String userName, Currency currency){
        Player player = getPlayer(userName);
        return player.getBank().get(currency);
    }

    public double getBalance(UUID playerUUID, Currency currency){
        Player player = this.getPlayerBank(playerUUID);
        return player.getBank().get(currency);
    }

    public void createAccount(UUID playerUUID, String username){
    }

    public void loadPlayerAccount(UUID playerUUID){
        Connection connection = plugin.getDatabase().getConnection();

        Player player = new Player(playerUUID, Bukkit.getPlayer(playerUUID).getName());
        try{
            List<Currency> currencies = plugin.getCurrencyManager().getCurrenciesAsList();
            Map<Currency, Double> playerBank = player.getBank();
            for(Currency currency: currencies){
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM PlayerEconomy WHERE PLAYER_UUID=? AND CURRENCY_ID=?");
                statement.setString(1, playerUUID.toString());
                statement.setString(2, currency.getCurrencyID().toString());

                ResultSet results = statement.executeQuery();
                results.next();

                playerBank.put(currency, results.getDouble("BALANCE"));
            }
            playersBank.put(playerUUID, player);
            plugin.getLogger().info("Loaded Player Account!");
        }catch (SQLException ex){
            plugin.getLogger().log(Level.WARNING, "An error occurred while executing a task on the database!", ex);
        }
    }



    public Player getPlayer(String username){
        for(Player player: getPlayersBank().values()){
            if(player.getUserName() == username){
                return player;
            }
        }
        return null;
    }

    public EconomyPlus getPlugin() {
        return plugin;
    }

    public Map<UUID, Player> getPlayersBank() {
        return playersBank;
    }
}
