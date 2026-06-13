package org.example.service;

import org.example.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterestCalculatorServiceImplTest {

    private InterestCalculatorService calculatorService;
    private CardNetwork visaNetwork;
    private CardNetwork mastercardNetwork;
    private CardNetwork discoverNetwork;

    @BeforeEach
    void setUp() {
        calculatorService = new InterestCalculatorServiceImpl();
        visaNetwork = new VisaNetwork();
        mastercardNetwork = new MastercardNetwork();
        discoverNetwork = new DiscoverNetwork();
    }

    /**
     * Scenario 1: 1 person, 1 wallet, 3 cards
     * One person has one wallet containing three cards: 1 Visa, 1 Mastercard, and 1 Discover.
     * Each card has a balance of $100.00.
     * Expected Output: Total interest per card: Visa = $10.00, Mastercard = $5.00, Discover = $1.00.
     * Total person interest = $16.00.
     */
    @Test
    void testScenarioOne_OnePersonOneWalletThreeCards() {
        // Arrange
        CreditCard visaCard = new CreditCard(visaNetwork, 100.00);
        CreditCard mcCard = new CreditCard(mastercardNetwork, 100.00);
        CreditCard discCard = new CreditCard(discoverNetwork, 100.00);

        Wallet wallet = new Wallet();
        wallet.addCreditCard(visaCard);
        wallet.addCreditCard(mcCard);
        wallet.addCreditCard(discCard);

        Person person = new Person("Justin");
        person.addWallet(wallet);

        assertEquals(10.00, calculatorService.calculateCardInterest(visaCard));
        assertEquals(5.00, calculatorService.calculateCardInterest(mcCard));
        assertEquals(1.00, calculatorService.calculateCardInterest(discCard));


        double totalPersonInterest = calculatorService.calculatePersonInterest(person);
        assertEquals(16.00, totalPersonInterest);
    }

    /**
     * Scenario 2: 1 person, 2 wallets
     * One person has two wallets. Wallet 1 contains 1 Visa and 1 Discover.
     * Wallet 2 contains 1 Mastercard. Each card has a balance of $100.00.
     * Expected Output: Wallet 1 interest = $11.00 ($10 + $1). Wallet 2 interest = $5.00. Total person interest = $16.00.
     */
    @Test
    void testScenarioTwo_OnePersonTwoWallets() {
        CreditCard wallet1Visa = new CreditCard(visaNetwork, 100.00);
        CreditCard wallet1Discover = new CreditCard(discoverNetwork, 100.00);
        CreditCard wallet2Mastercard = new CreditCard(mastercardNetwork, 100.00);

        Wallet wallet1 = new Wallet();
        wallet1.addCreditCard(wallet1Visa);
        wallet1.addCreditCard(wallet1Discover);

        Wallet wallet2 = new Wallet();
        wallet2.addCreditCard(wallet2Mastercard);

        Person person = new Person("Justin");
        person.addWallet(wallet1);
        person.addWallet(wallet2);

        // Assert: Interest broken down per wallet in this scenario
        assertEquals(11.00, calculatorService.calculateWalletInterest(wallet1), 0.001);
        assertEquals(5.00, calculatorService.calculateWalletInterest(wallet2),0.001);

        // Assert: Total interest for this person in this scenario
        assertEquals(16.00, calculatorService.calculatePersonInterest(person));
    }

    /**
     * Scenario 3: 2 people, 1 wallet each
     * Person 1 has one wallet with 2 cards: 1 Mastercard and 1 Visa.
     * Person 2 has one wallet with 2 cards: 1 Visa and 1 Mastercard.
     * Each card has a balance of $100.00.
     * Expected Output: Each wallet has $15.00 ($5 + $10). Total interest for each person = $15.00.
     */
    @Test
    void testScenarioThree_TwoPeopleOneWalletEach() {
        // Person #1
        CreditCard p1Mastercard = new CreditCard(mastercardNetwork, 100.00);
        CreditCard p1Visa = new CreditCard(visaNetwork, 100.00);

        Wallet p1Wallet = new Wallet();
        p1Wallet.addCreditCard(p1Mastercard);
        p1Wallet.addCreditCard(p1Visa);

        Person person1 = new Person("Justin");
        person1.addWallet(p1Wallet);


        // Person #2
        CreditCard p2Visa = new CreditCard(visaNetwork, 100.00);
        CreditCard p2Mastercard = new CreditCard(mastercardNetwork, 100.00);

        Wallet p2Wallet = new Wallet();
        p2Wallet.addCreditCard(p2Visa);
        p2Wallet.addCreditCard(p2Mastercard);

        Person person2 = new Person("Kong");
        person2.addWallet(p2Wallet);

        // Assert: Person 1 calculations
        assertEquals(15.00, calculatorService.calculateWalletInterest(p1Wallet),0.001);
        assertEquals(15.00, calculatorService.calculatePersonInterest(person1),0.001);

        // Assert: Person 2 calculations
        assertEquals(15.00, calculatorService.calculateWalletInterest(p2Wallet),0.001);
        assertEquals(15.00, calculatorService.calculatePersonInterest(person2),0.001);
    }
}