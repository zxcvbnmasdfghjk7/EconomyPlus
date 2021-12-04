package io.github.zxcvbnmasdfghjk7.economyplus.api.implementation.currency;

import io.github.zxcvbnmasdfghjk7.economyplus.EconomyPlus;
import io.github.zxcvbnmasdfghjk7.economyplus.api.currency.Currency;

import java.util.Map;
import java.util.UUID;

public class CurrencyAccount implements Currency {

    private final String currencyName;
    private final EconomyPlus plugin;
    public CurrencyAccount(String currencyName, EconomyPlus plugin)
    {
        this.currencyName = currencyName;
        this.plugin = plugin;
    }

    @Override
    public double getStartingBalance() {
        Map<String, io.github.zxcvbnmasdfghjk7.economyplus.currency.Currency> currencies = plugin.getCurrencyManager().getCurrencies();
        if(currencies.containsKey(currencyName)){
            io.github.zxcvbnmasdfghjk7.economyplus.currency.Currency currency = currencies.get(currencyName);
            return currency.getStartingBalance();
        }
        return 0;
    }

    @Override
    public UUID getCurrencyID() {
        Map<String, io.github.zxcvbnmasdfghjk7.economyplus.currency.Currency> currencies = plugin.getCurrencyManager().getCurrencies();
        if(currencies.containsKey(currencyName)){
            io.github.zxcvbnmasdfghjk7.economyplus.currency.Currency currency = currencies.get(currencyName);
            return currency.getCurrencyID();
        }
        return null;
    }

    @Override
    public boolean isDefaultCurrency() {
        Map<String, io.github.zxcvbnmasdfghjk7.economyplus.currency.Currency> currencies = plugin.getCurrencyManager().getCurrencies();
        if(currencies.containsKey(currencyName)){
            io.github.zxcvbnmasdfghjk7.economyplus.currency.Currency currency = currencies.get(currencyName);

            return currency.isDefaultCurrency();
        }
        return false;
    }
}
