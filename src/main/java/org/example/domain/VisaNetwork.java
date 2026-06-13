package org.example.domain;

public class VisaNetwork implements CardNetwork {
    @Override
    public String getName() {
        return "Visa";
    }

    @Override
    public double getMonthlyInterestRate() {
        return 0.10;
    }
}