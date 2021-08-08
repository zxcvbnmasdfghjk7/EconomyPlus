package io.github.eddiediamondfire.economyplus.vault;

import io.github.eddiediamondfire.economyplus.EconomyPlus;
import io.github.eddiediamondfire.economyplus.config.YAML;
import io.github.eddiediamondfire.economyplus.storage.StorageMethod;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class Economy implements net.milkbowl.vault.economy.Economy {

    private final EconomyPlus plugin;
    private final YAML config;
    private final StorageMethod storageSystem;
    public Economy(EconomyPlus economyPlus) {
        plugin = economyPlus;
        config = plugin.getFileManager().getFile("config.yml");
        storageSystem = plugin.getDatabaseStorageMethod();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "EconomyPlus";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double amount) {
        return config.getBukkitConfig().getString("global_economy.currency_symbol") + amount;
    }

    @Override
    public String currencyNamePlural() {
        return config.getBukkitConfig().getString("global_economy.currency_plural");
    }

    @Override
    public String currencyNameSingular() {
        return config.getBukkitConfig().getString("global_economy.currency_singular");
    }

    @Override
    public boolean hasAccount(String playerName) {
        return storageSystem.accountExist(playerName);
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        return storageSystem.accountExist(player.getName());
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return hasAccount(player);
    }

    @Override
    public double getBalance(String playerName) {
        return storageSystem.getBalance(playerName);
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        UUID playerUUID = player.getUniqueId();
        return storageSystem.getBalance(playerUUID);
    }

    @Override
    public double getBalance(String playerName, String world) {
        return getBalance(playerName);
    }

    @Override
    public double getBalance(OfflinePlayer player, String world) {
        return getBalance(player);
    }

    @Override
    public boolean has(String playerName, double amount) {
        UUID playerUUID = Bukkit.getPlayer(playerName).getUniqueId();
        double amountInBank = storageSystem.getBalance(playerUUID);

        return amount == amountInBank;
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        UUID playerUUID = player.getUniqueId();
        double amountInBank = storageSystem.getBalance(playerUUID);
        return amount == amountInBank;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return has(playerName, amount);
    }

    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return has(player, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        double oldAmount, newAmount;
        oldAmount = storageSystem.getBalance(playerName);

        if(oldAmount < amount){
            return new EconomyResponse(amount, oldAmount, EconomyResponse.ResponseType.FAILURE, "Your withdraw amount is greater than how much you currently have in the Bank!");
        }

        newAmount = oldAmount - amount;

        storageSystem.update(playerName, newAmount);
        return new EconomyResponse(amount, newAmount, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        UUID playerUUID = player.getUniqueId();
        double oldAmount, newAmount;
        oldAmount = storageSystem.getBalance(playerUUID);

        if(oldAmount < amount){
            return new EconomyResponse(amount, oldAmount, EconomyResponse.ResponseType.FAILURE, "Your withdraw amount is greater than how much you currently have in the Bank!");
        }

        newAmount = oldAmount - amount;

        storageSystem.update(playerUUID, newAmount);
        return new EconomyResponse(amount, newAmount, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return withdrawPlayer(player, amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        double oldAmount, newAmount;
        oldAmount = storageSystem.getBalance(playerName);

        newAmount = oldAmount + amount;
        storageSystem.update(playerName, newAmount);
        return new EconomyResponse(amount, newAmount, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        UUID playerUUID = player.getUniqueId();
        double oldAmount, newAmount;
        oldAmount = storageSystem.getBalance(playerUUID);

        newAmount = oldAmount + amount;
        storageSystem.update(playerUUID, newAmount);
        return new EconomyResponse(amount, newAmount, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return depositPlayer(player, amount);
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Bank Accounts!");
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Bank Accounts!");
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Bank Accounts!");
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Bank Accounts!");
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Bank Accounts!");
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Bank Accounts!");
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Bank Accounts!");
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Bank Accounts!");
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Bank Accounts!");
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Bank Accounts!");
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Bank Accounts!");
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        UUID playerUUID = Bukkit.getPlayer(playerName).getUniqueId();

        storageSystem.createAccount(playerUUID, playerName, 0);
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        return createPlayerAccount(player.getName());
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return createPlayerAccount(playerName);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return createPlayerAccount(player);
    }

    public YAML getConfig() {
        return config;
    }
}
