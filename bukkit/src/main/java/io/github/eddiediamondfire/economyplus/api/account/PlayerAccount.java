package io.github.eddiediamondfire.economyplus.api.account;

import io.github.eddiediamondfire.economyplus.EconomyPlus;
import io.github.eddiediamondfire.economyplus.exceptions.AccountNotFound;
import io.github.eddiediamondfire.economyplus.exceptions.InvalidAmount;
import io.github.eddiediamondfire.economyplus.player.MoneyManager;

import java.util.UUID;

public class PlayerAccount implements Account{

    private final EconomyPlus plugin;
    private final MoneyManager moneyManager;
    private UUID playerUUID;

    public PlayerAccount(UUID playerUUID){
        this.plugin = new EconomyPlus();
        this.moneyManager = plugin.getMoneyManager();
        this.playerUUID = playerUUID;
    }


    @Override
    public void setBalance(double amount) {
        try{
            if(!moneyManager.playerBankExist(playerUUID)){
                throw new AccountNotFound(playerUUID);
            }
            moneyManager.update(playerUUID, amount);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void addBalance(double amount) {
        double oldAmount = moneyManager.getBalance(playerUUID);
        try{
            if(!moneyManager.playerBankExist(playerUUID)){
                throw new AccountNotFound(playerUUID);
            }
            double newAmount = amount + oldAmount;
            moneyManager.update(playerUUID, newAmount);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void subtractBalance(double amount) {
        double newAmount;
        double oldAmount = moneyManager.getBalance(playerUUID);
        try{
            if(!moneyManager.playerBankExist(playerUUID)){
                throw new AccountNotFound(playerUUID);
            }
            
            if(amount > oldAmount){
                throw new InvalidAmount(amount);
            }
            newAmount = oldAmount - amount;
            moneyManager.update(playerUUID, newAmount);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public double getBalance() {
        double balance = 0;
        try{
            if(!moneyManager.playerBankExist(playerUUID)){
                throw new AccountNotFound(playerUUID);
            }
            
            balance = moneyManager.getBalance(playerUUID);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return balance;
    }
}
