package io.github.zxcvbnmasdfghjk7.economyplus.exceptions;

import java.util.UUID;

public class AccountNotFound extends Exception {

    private final UUID playerUUID;

    public AccountNotFound(UUID playerUUID){
        this.playerUUID = playerUUID;
    }

    public UUID getPlayerUUID(){
        return playerUUID;
    }
}
