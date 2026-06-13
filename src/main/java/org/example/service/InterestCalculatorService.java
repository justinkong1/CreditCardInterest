package org.example.service;

import org.example.domain.CreditCard;
import org.example.domain.Person;
import org.example.domain.Wallet;

public interface InterestCalculatorService {
    double calculateCardInterest(CreditCard card);
    double calculateWalletInterest(Wallet wallet);
    double calculatePersonInterest(Person person);
}