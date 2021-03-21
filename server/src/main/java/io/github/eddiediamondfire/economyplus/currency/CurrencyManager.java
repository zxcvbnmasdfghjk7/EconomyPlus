package io.github.eddiediamondfire.economyplus.currency;

import io.github.eddiediamondfire.economyplus.Main;
import io.github.eddiediamondfire.economyplus.account.Account;
import org.bukkit.ChatColor;

import java.util.*;

public class CurrencyManager {
    private final Main plugin;
    private final List<Currency> currencies;

    public CurrencyManager(Main plugin){
        this.plugin = plugin;
        this.currencies = new ArrayList<>();
    }

    // currencyName can be a plural or a singular
    public boolean currencyExist(String currencyName){
        for(Currency currency:currencies){
            if(currency.getPlural().equalsIgnoreCase(currencyName)){
                return true;
            }else return currency.getSingular().equalsIgnoreCase(currencyName);
        }
        return false;
    }

    public Currency getDefaultCurrency(){
        for(Currency currency:currencies){
            if(currency.isDefault()){
                return currency;
            }
        }
        return null;
    }

    // identifier currencyName can be a plural or a singular
    public Currency getCurrency(String currencyName){
        for(Currency cu:currencies){
            if(cu.getSingular().equalsIgnoreCase(currencyName)){
                return cu;
            }else if(cu.getPlural().equalsIgnoreCase(currencyName)){
                return cu;
            }else{
                return null;
            }
        }
        return null;
    }

    // TODO rewrite how to store data
    public void createCurrency(String singular, String plural){
        UUID randomID = UUID.randomUUID();
        Currency currency = new Currency(randomID, plural, singular);

        currencies.add(currency);

        for(Account account: plugin.getAccountManager().getAccounts()){
            Map<Currency, Double> balance = account.getBalances();
            balance.put(currency, currency.getStartBalance());
        }
    }

    // TODO Rewrite how data is handled
    public void removeCurrency(String currencyName){
        Currency cu = getCurrency(currencyName);

        if(!(currencies.size() == 1)){

            List<Account> accounts = plugin.getAccountManager().getAccounts();

            for(Account acc:accounts){
                Map<Currency, Double> balances = acc.getBalances();

                balances.remove(cu);
            }

            currencies.remove(cu);
        }
    }

    public void loadCurrencies(UUID id,
                               String plural,
                               String singular,
                               ChatColor currencyColor,
                               boolean isDecimal,
                               boolean isExchangeAble,
                               boolean isDefault,
                               double startBalance,
                               char symbol)
    {
        Currency currency = new Currency(id, plural, singular);
        currency.setCurrencyColour(currencyColor);
        currency.setDecimal(isDecimal);
        currency.setExchangeAble(isExchangeAble);
        currency.setDefault(isDefault);
        currency.setStartBalance(startBalance);
        currency.setSymbol(symbol);

        currencies.add(currency);
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }
}
