package io.github.zxcvbnmasdfghjk7.economyplus.api.implementation;

import io.github.zxcvbnmasdfghjk7.economyplus.EconomyPlus;
import io.github.zxcvbnmasdfghjk7.economyplus.api.EconomyPlusAPI;
import io.github.zxcvbnmasdfghjk7.economyplus.api.account.Account;
import io.github.zxcvbnmasdfghjk7.economyplus.api.currency.Currency;
import io.github.zxcvbnmasdfghjk7.economyplus.api.implementation.account.PlayerAccount;
import io.github.zxcvbnmasdfghjk7.economyplus.api.implementation.currency.CurrencyAccount;
import io.github.zxcvbnmasdfghjk7.economyplus.currency.CurrencyManager;

import java.util.Map;
import java.util.UUID;

public class EconomyAPIImplementation implements EconomyPlusAPI {
    private final EconomyPlus plugin;
    public EconomyAPIImplementation(EconomyPlus plugin){
        this.plugin = plugin;
    }

    @Override
    public Account getAccount(UUID playerUUID) {
        return new PlayerAccount(this, playerUUID);
    }

    @Override
    public Account getAccount(String username) {
        return new PlayerAccount(this, username);
    }

    @Override
    public Currency getCurrency(String currencyName) {
        if(isCurrencyExist(currencyName)){
            return new CurrencyAccount(currencyName, plugin);
        }
        return null;
    }

    @Override
    public Currency getDefaultCurrency() {
        CurrencyManager currencyManager = plugin.getCurrencyManager();
        io.github.zxcvbnmasdfghjk7.economyplus.currency.Currency currency = currencyManager.getDefaultCurrency();
        String defaultCurrencyName = currency.getName();

        if(isCurrencyExist(defaultCurrencyName)){
            return new CurrencyAccount(defaultCurrencyName, plugin);
        }
        return null;
    }

    @Override
    public boolean isCurrencyExist(String currencyName) {
        Map<String, io.github.zxcvbnmasdfghjk7.economyplus.currency.Currency> currencies = plugin.getCurrencyManager().getCurrencies();
        return currencies.containsKey(currencyName);
    }

    public EconomyPlus getPlugin() {
        return plugin;
    }
}
