package io.github.zxcvbnmasdfghjk7.economyplus.api.implementation.account;

import io.github.zxcvbnmasdfghjk7.economyplus.EconomyPlus;
import io.github.zxcvbnmasdfghjk7.economyplus.api.account.Account;
import io.github.zxcvbnmasdfghjk7.economyplus.api.implementation.EconomyAPIImplementation;

import java.util.UUID;

public class PlayerAccount implements Account {

    private final EconomyPlus plugin;
    public PlayerAccount(EconomyAPIImplementation api, UUID playerUUID){
        this.plugin = api.getPlugin();
    }

    public PlayerAccount(EconomyAPIImplementation api, String username){
        this.plugin = api.getPlugin();
    }

    @Override
    public double getAccountBalance(String currencyName) {
        return 0;
    }

    @Override
    public double getAccountBalance(UUID currencyID) {
        return 0;
    }

    @Override
    public double getAccountBalance() {
        return 0;
    }

    @Override
    public void withdraw(double amount, String currencyName) {

    }

    @Override
    public void withdraw(double amount, UUID currencyID) {

    }

    @Override
    public void withdraw(double amount) {

    }

    @Override
    public void deposit(double amount, String currencyName) {

    }

    @Override
    public void deposit(double amount, UUID currencyID) {

    }

    @Override
    public void deposit(double amount) {

    }

    public EconomyPlus getPlugin() {
        return plugin;
    }
}
