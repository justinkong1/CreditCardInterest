package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    private final List<Wallet> wallets;

    public Person(String name) {
        this.name = name;
        this.wallets = new ArrayList<>();
    }

    public void addWallet(Wallet wallet) {
        wallets.add(wallet);
    }

    public List<Wallet> getWallets() {
        return new ArrayList<>(wallets);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}