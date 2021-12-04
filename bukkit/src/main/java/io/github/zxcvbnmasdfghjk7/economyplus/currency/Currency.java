package io.github.zxcvbnmasdfghjk7.economyplus.currency;

import org.bukkit.ChatColor;

import java.util.UUID;

public class Currency {

    private String name;
    private UUID currencyID;
    private char symbol;
    private double startingBalance;
    private ChatColor currencyColour;
    private boolean defaultCurrency;

    public Currency(String currencyName, double startingBalance, boolean defaultCurrency){
        setName(currencyName);
        setCurrencyID(UUID.randomUUID());
        setSymbol(' ');
        setStartingBalance(startingBalance);
        setCurrencyColour(ChatColor.WHITE);
        setDefaultCurrency(defaultCurrency);
    }

    public Currency(String name, UUID currencyID, char symbol, double startingBalance, ChatColor colour, boolean isDefault){
        setName(name);
        setCurrencyID(currencyID);
        setSymbol(symbol);
        setStartingBalance(startingBalance);
        setCurrencyColour(colour);
        setDefaultCurrency(isDefault);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(UUID currencyID) {
        this.currencyID = currencyID;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public double getStartingBalance() {
        return startingBalance;
    }

    public void setStartingBalance(double startingBalance) {
        this.startingBalance = startingBalance;
    }

    public ChatColor getCurrencyColour() {
        return currencyColour;
    }

    public void setCurrencyColour(ChatColor currencyColour) {
        this.currencyColour = currencyColour;
    }

    public boolean isDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(boolean defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }
}
