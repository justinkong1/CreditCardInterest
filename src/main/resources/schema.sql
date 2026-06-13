-- Need to clean up any existing structures
DROP TABLE IF EXISTS credit_card;
DROP TABLE IF EXISTS wallet;
DROP TABLE IF EXISTS person;

CREATE TABLE person (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE wallet (
    id INT AUTO_INCREMENT PRIMARY KEY,
    person_id INT NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person(id)
);

CREATE TABLE credit_card (
    id INT AUTO_INCREMENT PRIMARY KEY,
    wallet_id INT NOT NULL,
    network VARCHAR(50) NOT NULL,
    balance DOUBLE NOT NULL,
    FOREIGN KEY (wallet_id) REFERENCES wallet(id)
);

-- ==========================================
-- DATA FOR SCENARIO 1
-- 1 Person, 1 Wallet, 3 Cards (Visa, MC, Discover)
-- ==========================================
INSERT INTO person (id, name) VALUES (1, 'Justin Scenario 1');
INSERT INTO wallet (id, person_id) VALUES (1, 1);
INSERT INTO credit_card (wallet_id, network, balance) VALUES (1, 'Visa', 100.00);       -- $10.00 interest
INSERT INTO credit_card (wallet_id, network, balance) VALUES (1, 'Mastercard', 100.00); -- $5.00 interest
INSERT INTO credit_card (wallet_id, network, balance) VALUES (1, 'Discover', 100.00);   -- $1.00 interest

-- ==========================================
-- DATA FOR SCENARIO 2
-- 1 Person, 2 Wallets (W1: Visa + Discover, W2: MC)
-- ==========================================
INSERT INTO person (id, name) VALUES (2, 'Justin Scenario 2');
INSERT INTO wallet (id, person_id) VALUES (2, 2); -- Wallet ID 2
INSERT INTO wallet (id, person_id) VALUES (3, 2); -- Wallet ID 3
-- Wallet 1 (DB ID 2)
INSERT INTO credit_card (wallet_id, network, balance) VALUES (2, 'Visa', 100.00);       -- $10.00 interest
INSERT INTO credit_card (wallet_id, network, balance) VALUES (2, 'Discover', 100.00);   -- $1.00 interest
-- Wallet 2 (DB ID 3)
INSERT INTO credit_card (wallet_id, network, balance) VALUES (3, 'Mastercard', 100.00); -- $5.00 interest

-- ==========================================
-- DATA FOR SCENARIO 3
-- 2 People, 1 Wallet Each (Both have 1 Visa, 1 MC)
-- ==========================================
INSERT INTO person (id, name) VALUES (3, 'Justin Scenario 3');
INSERT INTO person (id, name) VALUES (4, 'JustinKong Scenario 3');

INSERT INTO wallet (id, person_id) VALUES (4, 3); -- Person 1's Wallet (DB ID 4)
INSERT INTO wallet (id, person_id) VALUES (5, 4); -- Person 2's Wallet (DB ID 5)

-- Person 1 Wallet Cards
INSERT INTO credit_card (wallet_id, network, balance) VALUES (4, 'Mastercard', 100.00);
INSERT INTO credit_card (wallet_id, network, balance) VALUES (4, 'Visa', 100.00);

-- Person 2 Wallet Cards
INSERT INTO credit_card (wallet_id, network, balance) VALUES (5, 'Visa', 100.00);
INSERT INTO credit_card (wallet_id, network, balance) VALUES (5, 'Mastercard', 100.00);