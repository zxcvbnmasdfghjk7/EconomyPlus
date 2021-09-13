package io.github.eddiediamondfire.economyplus.api;

import io.github.eddiediamondfire.economyplus.api.account.Account;

import java.util.UUID;

public interface EconomyPlusAPI {

    Account getAccount(UUID playerUUID);
    Account getAccount(String playerUserName);
}
