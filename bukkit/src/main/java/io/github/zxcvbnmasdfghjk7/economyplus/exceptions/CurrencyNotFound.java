package io.github.zxcvbnmasdfghjk7.economyplus.exceptions;

public class CurrencyNotFound extends Exception{

    private String currencyName;

    public CurrencyNotFound(){

    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
}
