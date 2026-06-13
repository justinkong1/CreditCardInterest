# Credit Card Interest
## Instructions / How to Run the Tests

You can run the full JUnit 5 test suite verifying all scenarios either via the terminal or directly inside your IDE.

### Option 1: Via Terminal (Using the included Maven Wrapper)
Navigate to the project root directory and run the command matching your operating system. You do not need to have Maven pre-installed globally.

- **On macOS / Linux:**
  ```bash
  ./mvnw clean test

- **On Windows powershell:**
  ```bash
  .\mvnw.cmd clean test
  or
  mvnw.cmd clean test

### Option 2: Via IntelliJ IDEA
- Open IntelliJ and select File > Open...
- Select the root folder of this project (the one containing the pom.xml file).
- Once the project loads and dependencies finish indexing, navigate to: src/test/java/org/example/service/InterestCalculatorServiceImplTest.java
- Right-click the test class file and click Run 'InterestCalculatorServiceImplTest' (or click the green play icon next to the class declaration).

## Documentation
Java backend implementation that models credit card hierarchies and calculates monthly simple interest.
This solution follows SOLID programming principles by cleanly separating data structures from business logic.

### Domain Models
- ```Person```: Manages a list of wallets belonging to an individual.
- ```Wallet```: Manages a collection of credit cards. Encapsulates internal lists safely using defensive copying.
- ```CreditCard```: Holds the current balance and a reference to a CardNetwork strategy.
- ```CardNetwork (Interface)```: The strategy abstraction that allows different networks (Visa, Mastercard, Discover) each separating their network and interest rates respectively.
  
### Service Layer
- calculateCardInterest(```CreditCard``` card): Calculates simple interest for a single month
- calculateWalletInterest(```Wallet``` wallet): Sums the total interest across all ```Person```'s cards inside a specific wallet.
- calculatePersonInterest(```Person``` person): Sums the total interest across all ```wallet```s owned by the ```Person```.  

### Database Architecture / SQL
- Without creating external dependencies, I am using in-memory H2 Database engine which is integrated into the test runtime environment.
- File Location: src/main/resources/schema.sql
- Behavior: The schema will drop, recreates tables, and fills the records matching all three required information for the scenarios upon test initialization.
- Relational Mapping: Handled in JdbcPersonRepository wrapper. It executes deep joins on the relational tables and uses a factory strategy pattern to dynamically hydrate plain SQL table strings.