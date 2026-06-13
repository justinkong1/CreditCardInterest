package org.example.domain;

public class MastercardNetwork implements CardNetwork {
    @Override
    public String getName() {
        return "Mastercard";
    }

    @Override
    public double getMonthlyInterestRate() {
        return 0.05;
    }
}