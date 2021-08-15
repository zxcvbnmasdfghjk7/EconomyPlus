package io.github.eddiediamondfire.economyplus.storage;

import java.util.UUID;

public interface StorageMethod {

    void update(UUID playerUUID, double amount);
    void update(String userName, double amount);

    double getBalance(UUID playerUUID);
    double getBalance(String userName);

    boolean accountExist(UUID playerUUID);
    boolean accountExist(String userName);

    void createAccount(UUID playerUUID, String userName, double amount);

    UUID getPlayerAccountUUID(String username);

}
