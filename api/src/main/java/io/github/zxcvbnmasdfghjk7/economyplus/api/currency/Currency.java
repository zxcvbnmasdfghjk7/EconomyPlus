package io.github.zxcvbnmasdfghjk7.economyplus.api.currency;

import java.util.UUID;

public interface Currency {

    double getStartingBalance();
    UUID getCurrencyID();
    boolean isDefaultCurrency();

}
