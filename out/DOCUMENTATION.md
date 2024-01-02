# ATM Interface Simulation - Detailed Class and Method Documentation

## ATM Class
Serves as the main interface for interacting with the bank and user accounts through a simulated ATM terminal.

- **main(String[] args)**: The entry point of the program. Initializes the bank, creates a user with a savings and checking account, and enters the main program loop.
- **mainMenuPrompt(Bank theBank, Scanner scan)**: Displays the main menu, handles user login, and returns the authenticated user.
- **printUserMenu(User theUser, Scanner scan)**: Prints the user menu for banking options like viewing transaction history, withdrawing funds, depositing funds, and transferring funds.
- **showTransHistory(User theUser, Scanner scan)**: Displays the transaction history for a specific account of the user.
- **transferFunds(User theUser, Scanner scan)**: Handles the process of transferring funds between user accounts.
- **withdrawFunds(User theUser, Scanner scan)**: Manages the withdrawal of funds from a user account.
- **depositFunds(User theUser, Scanner scan)**: Manages the depositing of funds into a user account.

## User Class
Represents a bank customer with personal details, authentication credentials, and a list of associated bank accounts.

- **Constructor:**

  - **User(String firstName, String lastName, String pin, Bank theBank):** Initializes a new User with the specified first name, last name, pin, and associated bank. It generates a unique user ID and encrypts the pin for security.
- **Methods:**

- **addAccount(Account anAcct):** Adds a new account to the user's list of accounts.
- **getUUID():** Returns the unique user ID.
- **validatePin(String aPin):** Checks if the provided pin matches the user's pin.
- **getFirstName():** Retrieves the user's first name.
- **printAccountsSummary():** Prints a summary of all the user's accounts.
- **numAccounts():** Returns the number of accounts the user has.
- **printAcctTransHistory(int accIdx):** Prints the transaction history for a specified account index.
- **getAcctBalance(int accIdx):** Returns the balance of a specific account.
- **getAcctUUID(int acctIdx):** Retrieves the UUID of a specific account.
- **addAcctTransaction(int acctIdx, double amount, String memo):** Adds a transaction to a specific account.

## Account Class
Represents a bank account with a unique ID, account holder, and transaction history.

- **Constructor:**

  - **Account(String name, User holder, Bank theBank)**: Initializes a new Account with a name, the holder of the account, and the bank. It generates a unique account ID.
- **Methods:**

- **getUUID():** Returns the account's unique ID.
- **getSummaryLine():** Provides a summary line for the account showing the balance and account name.
- **getBalance()**: Calculates and returns the current balance of the account.
- **printTransHistory():** Prints the transaction history of the account.
- **addTransaction(double amount, String memo):** Adds a new transaction to the account.

## Transaction Class
Encapsulates details of a financial transaction, including amount, timestamp, and memo.

- **Constructors:**

  - **Transaction(double amount, Account inAccount):** Creates a new transaction with the specified amount in the given account.
  - **Transaction(double amount, String memo, Account inAccount):** Additional constructor that includes a memo for the transaction.
- **Methods:**

  - **getAmount():** Returns the amount of the transaction.
  - **getSummaryLine():** Provides a formatted summary line of the transaction, including the timestamp, amount, and memo.

## Bank Class
Represents the bank system, managing users and their accounts.

- **Constructor:**

  - **Bank(String name)**: Initializes a new Bank object with a name.
- **Methods:**

  - **getNewUserUUID():** Generates a unique ID for a new user.
  - **getNewAccountUUID():** Generates a unique ID for a new account.
  - **addAccount(Account anAcct):** Adds a new account to the bank.
  - **addUser(String firstName, String lastName, String pin):** Creates and adds a new user to the bank.
  - **userLogin(String userID, String pin):** Authenticates a user based on the provided userID and pin.
  - **getName():** Returns the name of the bank.

## Running the Program
To find out how to run the program, please check out the [README file](README.md) in this repository.
