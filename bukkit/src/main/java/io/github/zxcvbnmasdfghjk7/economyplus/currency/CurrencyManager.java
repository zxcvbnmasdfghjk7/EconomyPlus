package io.github.zxcvbnmasdfghjk7.economyplus.currency;

import io.github.zxcvbnmasdfghjk7.economyplus.EconomyPlus;
import io.github.zxcvbnmasdfghjk7.economyplus.storage.StorageMethod;
import org.bukkit.ChatColor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CurrencyManager {

    private final Map<String, Currency> currencies;
    private final EconomyPlus plugin;

    public CurrencyManager(EconomyPlus plugin) {
        this.currencies = new HashMap<>();
        this.plugin  = plugin;
    }

    // TODO NOT TESTED
    public void loadCurrencies(){
        Connection connection = plugin.getDatabase().getConnection();

        List<UUID> currencyIDs = new ArrayList<>();
        try {
            PreparedStatement currencyIDsStatement = connection.prepareStatement("SELECT CURRENCY_ID from Currency");
            ResultSet results = currencyIDsStatement.executeQuery();

            while(results.next()){
                plugin.getLogger().info("Found Currency: " + results.getString("CURRENCY_ID"));
                currencyIDs.add(UUID.fromString(results.getString("CURRENCY_ID")));
            }

            for(UUID currrencyID: currencyIDs){
                PreparedStatement getStuffStatement = connection.prepareStatement("SELECT * FROM Currency WHERE CURRENCY_ID=?");
                getStuffStatement.setString(1, currencyIDs.toString());

                ResultSet resultsFromStatement = getStuffStatement.executeQuery();

                Currency currency = new Currency(resultsFromStatement.getString("CURRENCY_NAME"),
                        resultsFromStatement.getDouble("CURRENCY_STARTING_BALANCE"),
                        resultsFromStatement.getBoolean("DEFAULT_CURRENCY"));

                currency.setCurrencyID(currrencyID);
                currency.setSymbol(resultsFromStatement.getString("CURRENCY_SYMBOL").charAt(1));
                currency.setCurrencyColour(ChatColor.valueOf(resultsFromStatement.getString("CURRENCY_COLOUR")));

                currencies.put(currency.getName(), currency);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveCurrencies(){

    }

    public Currency getCurrency(UUID currencyID){
        for(Currency currency: currencies.values()){
            if(currency.getCurrencyID() == currencyID){
                return currency;
            }
        }
        return null;
    }

    public Currency getDefaultCurrency(){
        for(Currency currency: currencies.values()){
            if(currency.isDefaultCurrency()){
                return currency;
            }
        }
        return null;
    }

    public boolean currencyExists(String currencyName){
        if(currencies.containsKey(currencyName)){
            return true;
        }else{
            StorageMethod databaseStorage = plugin.getDatabaseStorageMethod();
            return databaseStorage.currencyExists(currencyName);
        }
    }

    public List<Currency> getCurrenciesAsList(){
        return new ArrayList<>(currencies.values());
    }

    public Map<String, Currency> getCurrencies() {
        return currencies;
    }

    public EconomyPlus getPlugin() {
        return plugin;
    }
}
