package io.github.zxcvbnmasdfghjk7.economyplus.api.account;

import java.util.UUID;

public interface Account {

    /**
     *
     * @param currencyName The Currency Name
     * @return Returns the account balance of the currency given.
     */
    double getAccountBalance(String currencyName);

    /**
     *
     * @param currencyID The Currency ID
     * @return Returns the account balance of the currency given.
     */
    double getAccountBalance(UUID currencyID);

    /**
     *
     * @return Returns the account balance in the default vault currency set.
     */
    double getAccountBalance();

    void withdraw(double amount, String currencyName);
    void withdraw(double amount, UUID currencyID);
    void withdraw(double amount);

    void deposit(double amount, String currencyName);
    void deposit(double amount, UUID currencyID);
    void deposit(double amount);
}
