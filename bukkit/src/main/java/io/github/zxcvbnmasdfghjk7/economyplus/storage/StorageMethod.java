package io.github.zxcvbnmasdfghjk7.economyplus.storage;

import io.github.zxcvbnmasdfghjk7.economyplus.currency.Currency;

import java.util.List;
import java.util.UUID;

public interface StorageMethod {

    void update(UUID playerUUID, double amount, Currency currency);
    void update(String userName, double amount, Currency currency);

    double getBalance(UUID playerUUID, Currency currency);
    double getBalance(String userName, Currency currency);

    boolean accountExist(UUID playerUUID);
    boolean accountExist(String userName);

    void createAccount(UUID playerUUID, String userName, List<Currency> currencies);

    UUID getPlayerAccountUUID(String username);

    void createCurrency(String currencyName);

    Currency getCurrency(String currencyName);
    Currency getCurrency(UUID currencyID);
    Currency getDefaultCurrency();

    boolean currencyExists(String currencyName);
    boolean currencyExists(UUID currencyID);

}
