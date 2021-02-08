package io.github.eddiediamondfire.economyplus.account;

import io.github.eddiediamondfire.economyplus.Main;
import io.github.eddiediamondfire.economyplus.currency.Currency;
import io.github.eddiediamondfire.economyplus.currency.CurrencyManager;
import io.github.eddiediamondfire.economyplus.storage.AbstractFile;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class AccountManager {
    private List<Account> accounts;
    private final Main plugin;
    private AbstractFile accountsStorage = null;
    public AccountManager(Main plugin){
        this.plugin = plugin;
        accounts = new ArrayList<>();
    }

    public Account getAccount(UUID playerUUID){
        for(Account account:accounts){
            if(account.getPlayerUUID().equals(playerUUID)){
                return account;
            }
        }
        return null;
    }

    public void createAccount(UUID playerUUID, String username){
        Account player = new Account(playerUUID, username);

        List<Currency> currencies = plugin.getCurrencyManager().getCurrencies();
        Map<Currency, Double> balance = player.getBalances();
        for(Currency currency:currencies){
            balance.put(currency, currency.getStartBalance());
        }
        accounts.add(player);

        FileConfiguration config = plugin.getAccountsStorage().getManager();

        // TODO
    }

    public boolean accountExist(UUID playerUUID){
        for(Account account:accounts){
            if(account.getPlayerUUID().equals(playerUUID)){
                return true;
            }else{
                return accountExistAccountsStorage(playerUUID);
            }
        }
        return accountExistAccountsStorage(playerUUID);
    }

    public boolean accountExistAccountsStorage(UUID playerUUID){
        accountsStorage = plugin.getAccountsStorage();
        return UUID.fromString(Objects.requireNonNull(accountsStorage.getManager().getString("accounts"))) != playerUUID;
    }

    // remove account when player leaves the game
    public void removeAccount(UUID playerUUID){
        FileConfiguration manager = plugin.getAccountsStorage().getManager();

        Player entity = Bukkit.getPlayer(playerUUID);
        if(accountExist(playerUUID)){
            Account player = this.getAccount(playerUUID);
            manager.set("accounts", playerUUID);
            manager.set("accounts." + playerUUID, entity.getDisplayName());

            for(Currency currency: plugin.getCurrencyManager().getCurrencies()){
                double amount = player.getAmount(currency);

                manager.set("accounts." + playerUUID + "." + entity.getDisplayName() + ".currency", currency.getSingular());
                manager.set("accounts." + playerUUID + "." + entity.getDisplayName() + ".currency." + currency.getSingular(), amount);
            }

            this.accounts.remove(player);
        }

    }

    // Add account when the player joins the server.
    public void addAccount(UUID playerUUID){
        FileConfiguration config = plugin.getAccountsStorage().getManager();

        if(!accountExist(playerUUID)){
            Account account = new Account(playerUUID, config.getString("accounts." + playerUUID));
            Map<Currency, Double> balances = account.getBalances();

            Player player = Bukkit.getPlayer(playerUUID);
            ConfigurationSection section = config.getConfigurationSection("accounts." + playerUUID + "." +
                    player.getDisplayName() + ".currency");
            for(String key: section.getKeys(false)){
                CurrencyManager currencyManager = plugin.getCurrencyManager();

                Currency currency = currencyManager.getCurrency(key);
                double amount = config.getDouble("accounts."
                        + playerUUID + "." +
                        player.getDisplayName()
                        + ".currency." +
                        key + "" +
                        ".balance");


                balances.put(currency, amount);
            }

            accounts.add(account);
        }
    }
}
