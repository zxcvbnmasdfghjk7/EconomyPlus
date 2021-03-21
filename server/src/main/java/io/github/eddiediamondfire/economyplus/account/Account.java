package io.github.eddiediamondfire.economyplus.account;

import io.github.eddiediamondfire.economyplus.currency.Currency;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Account {

    private UUID playerUUID;
    private String username;
    private UUID accountID;
    private Map<Currency, Double> balances;

    public Account(UUID playerUUID, String username){
        setPlayerUUID(playerUUID);
        setUsername(username);
        setBalances(new HashMap<>());
    }

    public void withdrawAccount(Currency currency, double amount){
        double currentBalance = balances.get(currency);
        double newBalance = currentBalance - amount;
        balances.put(currency, newBalance);
    }

    public void depositAccount(Currency currency, double amount){
        double currentBalance = balances.get(currency);
        double newBalance = currentBalance + amount;
        balances.put(currency, newBalance);
    }

    public Map<Currency, Double> getBalances(){
        return balances;
    }
    public double getAmount(Currency currency){
        return balances.get(currency);
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getAccountID() {
        return accountID;
    }

    public void setAccountID(UUID accountID) {
        this.accountID = accountID;
    }

    public void setBalances(Map<Currency, Double> balances) {
        this.balances = balances;
    }
}
