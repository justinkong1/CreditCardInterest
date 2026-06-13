package org.example.service;

import org.example.domain.CreditCard;
import org.example.domain.Person;
import org.example.domain.Wallet;

public class InterestCalculatorServiceImpl implements InterestCalculatorService {

    @Override
    public double calculateCardInterest(CreditCard card) {
        if (card == null) {
            return 0.0;
        }

        double monthlyRate = card.getNetwork().getMonthlyInterestRate();
        // use Math.max in the case of a negative balance
        return Math.max(card.getBalance() * monthlyRate, 0.0);
    }

    // using the first method, we can easily calculate interest for wallet and person.
    @Override
    public double calculateWalletInterest(Wallet wallet) {
        if (wallet == null || wallet.getCreditCards() == null) {
            return 0.0;
        }

        double totalInterest = 0.0;
        for (CreditCard card : wallet.getCreditCards()) {
            totalInterest += calculateCardInterest(card);
        }
        return totalInterest;
    }

    @Override
    public double calculatePersonInterest(Person person) {
        if (person == null || person.getWallets() == null) {
            return 0.0;
        }

        double totalInterest = 0.0;
        for (Wallet wallet : person.getWallets()) {
            totalInterest += calculateWalletInterest(wallet);
        }
        return totalInterest;
    }
}