package io.github.eddiediamondfire.economyplus.account;

import java.util.UUID;

public class PlayerAccount {

    private UUID playerUUID;
    private String userName;
    private double bankAmount;

    public PlayerAccount(UUID playerUUID, String userName, double amount){
        setPlayerUUID(playerUUID);
        setUserName(userName);
        setBankAmount(amount);
    }
    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getBankAmount() {
        return bankAmount;
    }

    public void setBankAmount(double bankAmount) {
        this.bankAmount = bankAmount;
    }
}
