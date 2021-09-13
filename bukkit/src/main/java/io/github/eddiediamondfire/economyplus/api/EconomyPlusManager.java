package io.github.eddiediamondfire.economyplus.api;

import io.github.eddiediamondfire.economyplus.EconomyPlus;
import io.github.eddiediamondfire.economyplus.api.account.Account;
import io.github.eddiediamondfire.economyplus.api.account.PlayerAccount;

import java.util.UUID;

public class EconomyPlusManager implements EconomyPlusAPI{

    private final EconomyPlus plugin;
    public EconomyPlusManager(EconomyPlus plugin){
        this.plugin = plugin;
    }

    @Override
    public Account getAccount(UUID playerUUID) {
        return new PlayerAccount(playerUUID);
    }

    @Override
    public Account getAccount(String playerUserName) {
        UUID playerUUID = plugin.getMoneyManager().getPlayer(playerUserName).getPlayerUUID();
        return new PlayerAccount(playerUUID);
    }
}
