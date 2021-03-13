package io.github.eddiediamondfire.economyplus.api.account;

import java.util.UUID;

public interface Account {

    void withdrawAmount(String currency, double amount, UUID player);
    void depositAmount(String currency, double amount, UUID player);

    double getAmount(String currency, UUID player);

}
