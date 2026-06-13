package org.example.repository;

import org.example.domain.Person;
import java.sql.SQLException;

public interface PersonRepository {
    Person findPersonWithWalletsAndCards(int personId) throws SQLException;
}