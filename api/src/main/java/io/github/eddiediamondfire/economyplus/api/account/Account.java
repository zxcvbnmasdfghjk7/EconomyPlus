package io.github.eddiediamondfire.economyplus.api.account;

public interface Account {

    void setBalance(double amount);
    void addBalance(double amount);
    void subtractBalance(double amount);
    double getBalance();
}
