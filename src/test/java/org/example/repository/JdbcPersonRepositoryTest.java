package org.example.repository;

import org.example.domain.CreditCard;
import org.example.domain.Person;
import org.example.domain.Wallet;
import org.example.service.InterestCalculatorService;
import org.example.service.InterestCalculatorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcPersonRepositoryTest {

    private static final String H2_TEST_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:schema.sql'";

    private PersonRepository personRepository;
    private InterestCalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        personRepository = new JdbcPersonRepository(H2_TEST_URL);
        calculatorService = new InterestCalculatorServiceImpl();
    }

    /**
     * Scenario 1: 1 person, 1 wallet, 3 cards
     * One person has one wallet containing three cards: 1 Visa, 1 Mastercard, and 1 Discover.
     * Each card has a balance of $100.00.
     * Expected Output: Total interest per card: Visa = $10.00, Mastercard = $5.00, Discover = $1.00.
     * Total person interest = $16.00.
     */
    @Test
    void testScenarioOne_DatabaseHydration() throws SQLException {
        Person person = personRepository.findPersonWithWalletsAndCards(1);

        assertNotNull(person);
        List<Wallet> wallets = person.getWallets();
        assertEquals(1, wallets.size());

        List<CreditCard> cards = wallets.get(0).getCreditCards();
        assertEquals(3, cards.size());

        // Test individual card calculation from DB data
        assertEquals(10.00, calculatorService.calculateCardInterest(cards.get(0)), 0.001);
        assertEquals(5.00, calculatorService.calculateCardInterest(cards.get(1)), 0.001);
        assertEquals(1.00, calculatorService.calculateCardInterest(cards.get(2)), 0.001);

        // Test person grand total
        assertEquals(16.00, calculatorService.calculatePersonInterest(person), 0.001);
    }

    /**
     * Scenario 2: 1 person, 2 wallets
     * One person has two wallets. Wallet 1 contains 1 Visa and 1 Discover.
     * Wallet 2 contains 1 Mastercard. Each card has a balance of $100.00.
     * Expected Output: Wallet 1 interest = $11.00 ($10 + $1). Wallet 2 interest = $5.00. Total person interest = $16.00.
     */
    @Test
    void testScenarioTwo_DatabaseHydration() throws SQLException {
        Person person = personRepository.findPersonWithWalletsAndCards(2);

        assertNotNull(person);
        List<Wallet> wallets = person.getWallets();
        assertEquals(2, wallets.size());

        // Test wallet-level interest breakdowns
        assertEquals(11.00, calculatorService.calculateWalletInterest(wallets.get(0)), 0.001);
        assertEquals(5.00, calculatorService.calculateWalletInterest(wallets.get(1)), 0.001);

        // Test person grand total
        assertEquals(16.00, calculatorService.calculatePersonInterest(person), 0.001);
    }

    /**
     * Scenario 3: 2 people, 1 wallet each
     * Person 1 has one wallet with 2 cards: 1 Mastercard and 1 Visa.
     * Person 2 has one wallet with 2 cards: 1 Visa and 1 Mastercard.
     * Each card has a balance of $100.00.
     * Expected Output: Each wallet has $15.00 ($5 + $10). Total interest for each person = $15.00.
     */
    @Test
    void testScenarioThree_DatabaseHydration() throws SQLException {
        Person person1 = personRepository.findPersonWithWalletsAndCards(3);
        Person person2 = personRepository.findPersonWithWalletsAndCards(4);

        assertNotNull(person1);
        assertNotNull(person2);

        // Validate Person 1 breaks
        assertEquals(15.00, calculatorService.calculateWalletInterest(person1.getWallets().get(0)), 0.001);
        assertEquals(15.00, calculatorService.calculatePersonInterest(person1), 0.001);

        // Validate Person 2 breaks
        assertEquals(15.00, calculatorService.calculateWalletInterest(person2.getWallets().get(0)), 0.001);
        assertEquals(15.00, calculatorService.calculatePersonInterest(person2), 0.001);
    }
}