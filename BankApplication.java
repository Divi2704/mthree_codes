import java.util.Scanner;

public class BankApplication {
    // Static variable to store the account balance
    static double balance = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter 1 for Savings Account or 2 for Current Account:");
        int accountType = sc.nextInt();
        switch (accountType) {
            case 1:
                System.out.println("Savings Account");
                accountOptions();
                break;
            case 2:
                System.out.println("Current Account");
                accountOptions();
                break;
            default:
                System.out.println("Invalid account type");
        }
        sc.close();
    }

    // Method to check and display the current balance
    public static void checkBalance() {
        System.out.println("Balance is: " + balance);
    }

    // Method to deposit money into the account
    public static void deposit() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter amount to deposit:");
        double amount = sc.nextDouble();
        balance += amount;
        System.out.println("Deposited amount is: " + amount);
    }

    // Method to withdraw money from the account
    public static void withdraw() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter amount to withdraw:");
        double amount = sc.nextDouble();
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn amount is: " + amount);
        } else {
            System.out.println("Insufficient balance");
        }
    }

    // Method to display and handle account options
    public static void accountOptions() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the bank");
        while (true) {
            // Display menu options
            System.out.println("\nChoose an option");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4.Exit");
            int option = sc.nextInt();
            // Handle user's choice
            switch (option) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    System.out.println("Thank you for using our bank");
                    return; // Exit the method and end the program
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}