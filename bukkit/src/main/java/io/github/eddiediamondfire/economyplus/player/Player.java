package io.github.eddiediamondfire.economyplus.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Player {
    private UUID playerUUID;
    private String userName;
    private Map<String, Double> bank;

    public Player(UUID playerUUID, String userName){
        setPlayerUUID(playerUUID);
        setUserName(userName);
        setBank(new HashMap<>());
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

    public Map<String, Double> getBank() {
        return bank;
    }

    public void setBank(Map<String, Double> bank) {
        this.bank = bank;
    }
}
