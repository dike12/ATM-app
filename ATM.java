import java.util.InputMismatchException;
import java.util.Scanner;

public class ATM {
    public static void main(String[] args){
        //inti scanner
        Scanner scan = new Scanner(System.in);

        //init bank
        Bank theBank = new Bank("KB Bank");

        //add a user, which also creates a savings account
        User aUser = theBank.addUser("Kabelo", "Dike", "1234");

        //add a checking account for our user
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true){
            //stay in the login prompt until successful login
            curUser = ATM.mainMenuPrompt(theBank, scan);

            //stay in main menu until user quits
            ATM.printUserMenu(curUser, scan);
        }
    }

    /**
     * Print the ATM's login menu
     * @param theBank       the bank object whose accounts to use
     * @param scan          the Scanner object to use for user input
     * @return              the Authenticated User object
     */
    public static User mainMenuPrompt(Bank theBank, Scanner scan){
        //inits
        String userID;
        String pin;
        User authUser;

        //prompt user for user ID/pin combo until a correct one is reached
        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userID = scan.nextLine();
            System.out.print("Enter pin: ");
            pin = scan.nextLine();

            //try to get the user object corresponding to the ID and pin combo
            authUser = theBank.userLogin(userID, pin);
            if(authUser == null){
                System.out.println("incorrect user ID/pin combination. please try again");
            }
        }while (authUser == null);  //continue looping until successful login

        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner scan){
        //print a summary of the user's accounts
        theUser.printAccountsSummary();

        //init
        int choice;

        //user menu
        do{
            System.out.printf("welcome %s, what would you like to do?\n", theUser.getFirstName());
            System.out.println("    1) Show account transaction history");
            System.out.println("    2) Withdrawl");
            System.out.println("    3) Deposit");
            System.out.println("    4) Transfer");
            System.out.println("    5) Quit");
            System.out.println();
            System.out.println("Enter choice: ");
            choice = 0;
                try {
                    choice = scan.nextInt();

                    if (choice < 1 || choice > 5) {
                        System.out.println("Invalid choice. Please choose 1-5.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scan.next(); // Clear the buffer by reading the invalid input
                }

        }while(choice < 1 || choice > 5);

        //process the choice
        switch (choice){
            case 1:
                ATM.showTransHistory(theUser, scan);
                break;
            case 2:
                ATM.withdrawFunds(theUser, scan);
                break;
            case 3:
                ATM.depositFunds(theUser, scan);
                break;
            case 4:
                ATM.transferFunds(theUser, scan);
        }

        //redisplay this menu unless the user wants to quit
        if(choice != 5){
            ATM.printUserMenu(theUser, scan);
        }

    }

    /**
     * Show the transaction history for an account
     * @param theUser       the logged-in user object
     * @param scan          the scanner object used for user input
     */
    public static void showTransHistory(User theUser, Scanner scan){
            int theAcct;

            do{
                //get account whose transaction history to look at
                System.out.printf("Enter the number (1-%d) of the account whose transactions you want to see: ", theUser.numAccounts());
                theAcct = scan.nextInt() - 1;
                if(theAcct < 0 || theAcct >= theUser.numAccounts()){
                    System.out.println("Invalid account. please try again");
                }

            }while (theAcct < 0 || theAcct >= theUser.numAccounts());

            //print the transaction history
            theUser.printAcctTransHistory(theAcct);
    }

    /**
     * Process transferring funds from one account to another
     * @param theUser       the logged-in user object
     * @param scan          the Scanner object used for user input
     */
    public static void transferFunds(User theUser, Scanner scan){
        //inits
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        //get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n to transfer from: ", theUser.numAccounts());
            fromAcct = scan.nextInt() - 1;
            if(fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. please try again");
            }
        }while (fromAcct < 0 || fromAcct >= theUser.numAccounts());

        acctBal = theUser.getAcctBalance(fromAcct);

        //get the account to transfer to
        do{
            System.out.printf("Enter the number (1-%d) of the account\n to tranfer to: ", theUser.numAccounts() );
            toAcct = scan.nextInt() - 1;
            if(toAcct < 0 || toAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. please try again");
            }
        }while (toAcct < 0 || toAcct >= theUser.numAccounts());

        //get the amount to transfer
        do{
            System.out.printf("Enter the amount to transfer (max $%.2f): $", acctBal);
            amount = scan.nextDouble();

            if(amount < 0){
                System.out.println("Amount must be greater than zero.");
            }else if(amount > acctBal){
                System.out.printf("Amount must not be greater than\n balance of $%.2f.\n", acctBal);
            }
        }while(amount < 0 || amount > acctBal);

        //finally, do the transfer
        theUser.addAcctTransaction(fromAcct, -1*amount, String.format("Transfer to account %s", theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount, String.format("Transfer to account %s", theUser.getAcctUUID(toAcct)));
    }

    /**
     * Process a fund withdraw from an account
     * @param theUser       the logged-in User object
     * @param scan          the Scanner object user for user input
     */
    public static void withdrawFunds(User theUser, Scanner scan){
        //inits
        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        //get the account to withdraw from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n to withdraw from: ", theUser.numAccounts());
            fromAcct = scan.nextInt() - 1;
            if(fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. please try again");
            }
        }while (fromAcct < 0 || fromAcct >= theUser.numAccounts());

        acctBal = theUser.getAcctBalance(fromAcct);
        //get the amount to withdraw
        do{
            System.out.printf("Enter the amount to withdraw (max $%.2f): $", acctBal);
            amount = scan.nextDouble();

            if(amount < 0){
                System.out.println("Amount must be greater than zero.");
            }else if(amount > acctBal){
                System.out.printf("Amount must not be greater than\n balance of $%.2f.\n", acctBal);
            }
        }while(amount < 0 || amount > acctBal);

        //gobble up the rest of previous input
        scan.nextLine();

        //Get memo
        System.out.println("Enter a memo: ");
        memo = scan.nextLine();

        //do the withdrawal
        theUser.addAcctTransaction(fromAcct, -1*amount, memo);
    }

    /**
     * Process a fund deposit to an account
     * @param theUser       the logged-in User object
     * @param scan          the Scanner object used for user input
     */
    public static void depositFunds(User theUser, Scanner scan){
        //inits
        int toAcct;
        double amount;
        double acctBal;
        String memo;

        //get the account to deposit to
        do{
            System.out.printf("Enter the number (1-%d) of the account\n to deposit to: ", theUser.numAccounts() );
            toAcct = scan.nextInt() - 1;
            if(toAcct < 0 || toAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. please try again");
            }
        }while (toAcct < 0 || toAcct >= theUser.numAccounts());

        //get the amount to deposit
        do{
            double min = 0.00;
            System.out.printf("Enter the amount to deposit (min $%.2f): $", min);

            amount = scan.nextDouble();

            if(amount < 0){
                System.out.println("Amount must be greater than zero.");
            }
        }while(amount < 0 );

        //gobble up the rest of previous input
        scan.nextLine();

        //Get memo
        System.out.print("Enter a memo: ");
        memo = scan.nextLine();

        //do the deposit
        theUser.addAcctTransaction(toAcct, amount, memo);
    }
}
