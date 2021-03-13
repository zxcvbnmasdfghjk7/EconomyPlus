package io.github.eddiediamondfire.economyplus.account;

import io.github.eddiediamondfire.economyplus.Main;
import io.github.eddiediamondfire.economyplus.currency.Currency;
import io.github.eddiediamondfire.economyplus.storage.AbstractFile;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class AccountManager {
    private final List<Account> accounts;
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

    private boolean accountExistAccountsStorage(UUID playerUUID){
        accountsStorage = plugin.getAccountsStorage();
        return UUID.fromString(Objects.requireNonNull(accountsStorage.getManager().getString("accounts"))) == playerUUID;
    }

    public void saveAccount(UUID playerUUID){
        FileConfiguration config = plugin.getAccountsStorage().getManager();

        Player player = Bukkit.getPlayer(playerUUID);
        if(accountExist(playerUUID)){
            Account playerAccount = this.getAccount(playerUUID);

            config.set("accounts", playerUUID);

            config.set("accounts." + playerUUID, player.getDisplayName());

            for(Currency currency: plugin.getCurrencyManager().getCurrencies()){
                double amount =playerAccount.getAmount(currency);

                config.set("accounts." + playerUUID + "." + player.getDisplayName() + ".currency", currency.getSingular());
                config.set("accounts." + playerUUID + "." + player.getDisplayName() + ".currency." + currency.getSingular(), amount);
            }
        }
    }
}
