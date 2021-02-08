package io.github.eddiediamondfire.economyplus.core;

import io.github.eddiediamondfire.economyplus.Main;
import io.github.eddiediamondfire.economyplus.account.Account;
import io.github.eddiediamondfire.economyplus.account.AccountManager;
import io.github.eddiediamondfire.economyplus.currency.Currency;
import io.github.eddiediamondfire.economyplus.currency.CurrencyManager;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EconomyCore implements Economy {

    private final Main plugin;
    private final CurrencyManager currencyManager;
    private final AccountManager accountManager;
    public EconomyCore(Main plugin){
        this.plugin = plugin;
        this.currencyManager = plugin.getCurrencyManager();
        this.accountManager = plugin.getAccountManager();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return currencyManager.getDefaultCurrency().getSingular();
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
        return String.valueOf(currencyManager.getDefaultCurrency().getSymbol()) + amount;
    }

    @Override
    public String currencyNamePlural() {
        return currencyManager.getDefaultCurrency().getPlural();
    }

    @Override
    public String currencyNameSingular() {
        return currencyManager.getDefaultCurrency().getSingular();
    }

    @Override
    public boolean hasAccount(String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        return accountManager.accountExist(player.getUniqueId());
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        return accountManager.accountExist(player.getUniqueId());
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
        Player player = Bukkit.getPlayer(playerName);
        Currency defaultCurrency = currencyManager.getDefaultCurrency();

        if(accountManager.accountExist(player.getUniqueId())){
            Map<Currency, Double> balance = plugin.getAccountManager().getAccount(player.getUniqueId()).getBalances();
            return balance.get(defaultCurrency);
        }
        return 0;
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        Currency defaultCurrency = currencyManager.getDefaultCurrency();

        if(accountManager.accountExist(player.getUniqueId())){
            Map<Currency, Double> balance = plugin.getAccountManager().getAccount(player.getUniqueId()).getBalances();
            return balance.get(defaultCurrency);
        }
        return 0;
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
        Player player = Bukkit.getPlayer(playerName);
        if(accountManager.accountExist(player.getUniqueId())){
            Currency defaultCurrency = plugin.getCurrencyManager().getDefaultCurrency();
            Map<Currency, Double> balances = accountManager.getAccount(player.getUniqueId()).getBalances();
            return balances.get(defaultCurrency) == amount || balances.get(defaultCurrency) >= amount;
        }
        return false;
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        if(accountManager.accountExist(player.getUniqueId())){
            Currency defaultCurrency = plugin.getCurrencyManager().getDefaultCurrency();
            Map<Currency, Double> balances = accountManager.getAccount(player.getUniqueId()).getBalances();
            return balances.get(defaultCurrency) == amount || balances.get(defaultCurrency) >= amount;
        }
        return false;
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
        Player player = Bukkit.getPlayer(playerName);
        Currency currency = plugin.getCurrencyManager().getDefaultCurrency();
        if(accountManager.accountExist(player.getUniqueId())){
            Account account = accountManager.getAccount(player.getUniqueId());
            account.withdrawAccount(currency, amount);
            return new EconomyResponse(amount,
                    account.getAmount(currency),
                    EconomyResponse.ResponseType.SUCCESS,
                    null);
        }
        return new EconomyResponse(0,
                accountManager.getAccount(player.getUniqueId()).getAmount(currency),
                EconomyResponse.ResponseType.FAILURE,
                "Theres an error with no account!");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        Currency currency = plugin.getCurrencyManager().getDefaultCurrency();
        if(accountManager.accountExist(player.getUniqueId())){
            Account account = accountManager.getAccount(player.getUniqueId());
            account.withdrawAccount(currency, amount);
            return new EconomyResponse(amount,
                    account.getAmount(currency),
                    EconomyResponse.ResponseType.SUCCESS,
                    null);
        }
        return new EconomyResponse(0,
                accountManager.getAccount(player.getUniqueId()).getAmount(currency),
                EconomyResponse.ResponseType.FAILURE,
                "Theres an error with no account!");
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
        Player player = Bukkit.getPlayer(playerName);
        Currency defaultCurrency = currencyManager.getDefaultCurrency();

        if(accountManager.accountExist(player.getUniqueId())){
            Account account = accountManager.getAccount(player.getUniqueId());
            account.depositAccount(defaultCurrency, amount);
            return new EconomyResponse(amount,
                    account.getAmount(defaultCurrency),
                    EconomyResponse.ResponseType.SUCCESS,
                    null);
        }
        return new EconomyResponse(
                0,
                accountManager.getAccount(player.getUniqueId()).getAmount(defaultCurrency),
                EconomyResponse.ResponseType.FAILURE,
                "An account does not Exist!"
        );
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        Currency defaultCurrency = currencyManager.getDefaultCurrency();

        if(accountManager.accountExist(player.getUniqueId())){
            Account account = accountManager.getAccount(player.getUniqueId());
            account.depositAccount(defaultCurrency, amount);
            return new EconomyResponse(amount,
                    account.getAmount(defaultCurrency),
                    EconomyResponse.ResponseType.SUCCESS,
                    null);
        }
        return new EconomyResponse(
                0,
                accountManager.getAccount(player.getUniqueId()).getAmount(defaultCurrency),
                EconomyResponse.ResponseType.FAILURE,
                "An account does not Exist!"
        );
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
        return new EconomyResponse(0,0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Banks!");
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return new EconomyResponse(0,0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Banks!");
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0,0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Banks!");
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0,0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Banks!");
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0,0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Banks!");
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0,0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Banks!");
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0,0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Banks!");
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0,0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Banks!");
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return new EconomyResponse(0,0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Banks!");
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0,0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Banks!");
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return new EconomyResponse(0,0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EconomyPlus does not support Banks!");
    }

    @Override
    public List<String> getBanks() {
        return new ArrayList<>();
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return false;
    }
}
