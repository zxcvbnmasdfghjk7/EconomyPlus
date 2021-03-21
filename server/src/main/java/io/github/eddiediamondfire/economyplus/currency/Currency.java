package io.github.eddiediamondfire.economyplus.currency;

import org.bukkit.ChatColor;

import java.util.UUID;

public class Currency {

    private UUID id;
    private String plural;
    private String singular;
    private ChatColor currencyColour;
    private boolean isDecimal;
    private boolean isExchangeAble;
    private boolean isDefault;
    private double startBalance;
    private char symbol;

    public Currency(UUID id, String plural, String singular){
        setId(id);
        setPlural(plural);
        setSingular(singular);
        setCurrencyColour(ChatColor.WHITE);
        setDecimal(true);
        setExchangeAble(true);
        setDefault(false);
        setStartBalance(0);
        setSymbol(' ');
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPlural() {
        return plural;
    }

    public void setPlural(String plural) {
        this.plural = plural;
    }

    public String getSingular() {
        return singular;
    }

    public void setSingular(String singular) {
        this.singular = singular;
    }

    public ChatColor getCurrencyColour() {
        return currencyColour;
    }

    public void setCurrencyColour(ChatColor currencyColour) {
        this.currencyColour = currencyColour;
    }

    public boolean isDecimal() {
        return isDecimal;
    }

    public void setDecimal(boolean decimal) {
        isDecimal = decimal;
    }

    public boolean isExchangeAble() {
        return isExchangeAble;
    }

    public void setExchangeAble(boolean exchangeAble) {
        isExchangeAble = exchangeAble;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public double getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(double startBalance) {
        this.startBalance = startBalance;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}
