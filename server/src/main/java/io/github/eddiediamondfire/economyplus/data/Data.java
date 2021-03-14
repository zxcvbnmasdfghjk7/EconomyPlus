package io.github.eddiediamondfire.economyplus.data;

import io.github.eddiediamondfire.economyplus.account.Account;
import io.github.eddiediamondfire.economyplus.currency.Currency;
import org.bukkit.ChatColor;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

public interface Data {

    void initaliseDatabase();
    void saveDatabase();

    boolean tableExist(String tableName);

    Connection getConnection();
}
