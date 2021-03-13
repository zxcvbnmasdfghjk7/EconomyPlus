package io.github.eddiediamondfire.economyplus.listener;

import io.github.eddiediamondfire.economyplus.Main;
import io.github.eddiediamondfire.economyplus.account.Account;
import io.github.eddiediamondfire.economyplus.account.AccountManager;
import io.github.eddiediamondfire.economyplus.currency.Currency;
import io.github.eddiediamondfire.economyplus.currency.CurrencyManager;
import io.github.eddiediamondfire.economyplus.storage.AbstractFile;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;

public class EconomyListener implements Listener {
    private final Main plugin;
    private final AbstractFile accounts;
    private final AccountManager accountManager;
    public EconomyListener(Main plugin){
        this.plugin = plugin;
        this.accounts = plugin.getAccountsStorage();
        accountManager = plugin.getAccountManager();
    }

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        FileConfiguration accountManagerStorage = accounts.getManager();

        if(accountManager.accountExist(player.getUniqueId())){
            Account account = new Account(player.getUniqueId(), accountManagerStorage.getString("accounts"));

            Map<Currency, Double> balances = account.getBalances();

            ConfigurationSection section = accountManagerStorage.getConfigurationSection("accounts." + player.getUniqueId() + ".currency");

            for(String currencies:section.getKeys(false)){
                CurrencyManager currencyManager = plugin.getCurrencyManager();

                Currency currency = currencyManager.getCurrency(currencies);
                double amount = accountManagerStorage.getDouble("accounts." + player.getUniqueId() + ".currency." + currencies + ".balance");

                balances.put(currency, amount);
            }

            accountManager.getAccounts().add(account);
        }else{
            accountManager.createAccount(player.getUniqueId(), player.getDisplayName());


        }

    }

    @EventHandler
    public void onLeaveEvent(PlayerQuitEvent event){
        Player player = event.getPlayer();

        if(accountManager.accountExist(player.getUniqueId())){

            this.accountManager.saveAccount(player.getUniqueId());

            this.accountManager.getAccounts().remove(accountManager.getAccount(player.getUniqueId()));
        }
    }

}
