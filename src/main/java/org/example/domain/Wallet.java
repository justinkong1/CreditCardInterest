package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Wallet {
    private final List<CreditCard> creditCards;

    public Wallet(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public Wallet() {
        this.creditCards = new ArrayList<>();
    }

    public void addCreditCard(CreditCard creditCard) {
        creditCards.add(creditCard);
    }

    public List<CreditCard> getCreditCards() {
        return new ArrayList<>(creditCards);
    }


}
