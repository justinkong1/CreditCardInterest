package org.example.domain;

public class CreditCard {
    private double balance;
    private final CardNetwork network;

    public CreditCard(CardNetwork network, double balance) {
        this.network = network;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public CardNetwork getNetwork() {
        return network;
    }
}