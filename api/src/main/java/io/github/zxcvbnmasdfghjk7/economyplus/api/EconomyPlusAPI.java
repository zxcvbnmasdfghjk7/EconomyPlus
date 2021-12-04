package io.github.zxcvbnmasdfghjk7.economyplus.api;

import io.github.zxcvbnmasdfghjk7.economyplus.api.account.Account;
import io.github.zxcvbnmasdfghjk7.economyplus.api.currency.Currency;

import java.util.UUID;

public interface EconomyPlusAPI {

    Account getAccount(UUID playerUUID);
    Account getAccount(String username);

    Currency getCurrency(String currencyName);
    Currency getDefaultCurrency();
    boolean isCurrencyExist(String currencyName);
}

