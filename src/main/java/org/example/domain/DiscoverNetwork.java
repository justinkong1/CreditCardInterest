package org.example.domain;

public class DiscoverNetwork implements CardNetwork {
    @Override
    public String getName() {
        return "Discover";
    }

    @Override
    public double getMonthlyInterestRate() {
        return 0.01;
    }
}