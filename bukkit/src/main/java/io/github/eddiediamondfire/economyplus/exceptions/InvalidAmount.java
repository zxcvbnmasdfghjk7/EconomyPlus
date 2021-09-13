package io.github.eddiediamondfire.economyplus.exceptions;

public class InvalidAmount extends Exception {

    private final double amount;

    public InvalidAmount(double amount){
        this.amount = amount;
    }
}
