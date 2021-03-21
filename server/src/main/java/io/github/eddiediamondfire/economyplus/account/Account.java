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

    public double getAmount(Currency currency){
        return balances.get(currency);
    }
}
