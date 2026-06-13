package org.example.repository;

import org.example.domain.*;
import java.sql.*;
import java.util.ArrayList;

public class JdbcPersonRepository implements PersonRepository {
    private final String dbUrl;
    public JdbcPersonRepository(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    @Override
    public Person findPersonWithWalletsAndCards(int personId) throws SQLException {
        Person person = null;

        String query =
                "SELECT p.name AS person_name, w.id AS wallet_id, cc.network, cc.balance " +
                        "FROM person p " +
                        "LEFT JOIN wallet w ON p.id = w.person_id " +
                        "LEFT JOIN credit_card cc ON w.id = cc.wallet_id " +
                        "WHERE p.id = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, personId);

            try (ResultSet rs = stmt.executeQuery()) {
                Wallet currentWallet = null;
                int lastWalletId = -1;

                while (rs.next()) {
                    // Initialize the Person entity once
                    if (person == null) {
                        person = new Person(rs.getString("person_name"));
                    }

                    // Handle Wallet mapping transitions
                    int walletId = rs.getInt("wallet_id");
                    if (walletId > 0 && walletId != lastWalletId) {
                        currentWallet = new Wallet();
                        person.addWallet(currentWallet);
                        lastWalletId = walletId;
                    }

                    // Map Credit Cards and link them
                    String networkType = rs.getString("network");
                    if (networkType != null && currentWallet != null) {
                        CardNetwork network = createNetworkStrategy(networkType);
                        CreditCard card = new CreditCard(network, rs.getDouble("balance"));
                        currentWallet.addCreditCard(card);
                    }
                }
            }
        }
        return person;
    }

    private CardNetwork createNetworkStrategy(String networkType) {
        return switch (networkType.toLowerCase()) {
            case "visa" -> new VisaNetwork();
            case "mastercard" -> new MastercardNetwork();
            case "discover" -> new DiscoverNetwork();
            default -> throw new IllegalArgumentException("Unsupported network type from database: " + networkType);
        };
    }
}