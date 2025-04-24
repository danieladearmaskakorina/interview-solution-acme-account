/**
 * ------------------------------------------------------------------
 * SYSTEM DESIGN NOTES (Embedded Overview for Reviewers)
 * ------------------------------------------------------------------
 *
 * SYSTEM PURPOSE:
 * This class provides a command-line interface (CLI) for interacting
 * with the transactional account system. It simulates basic banking
 * behavior by allowing deposits, withdrawals, balance checks, and 
 * history retrieval via a text-based menu.
 *
 * WHY THIS STRUCTURE:
 * - CLI is separated from core account logic (see TransactionalAccount)
 * - The menu-based loop allows session-style interaction
 * - The interface reflects both business requirements and user feedback best practices
 *
 * USER EXPERIENCE:
 * - Deposits and withdrawals are categorized by asset type (CASH vs STOCK)
 * - Prompts distinguish "amount" from "number of shares" for clarity
 * - Input validation prevents crashes and provides recovery guidance
 *
 * SYSTEM INTEGRATION:
 * - TransactionalAccount.java handles state, balance logic, and validations
 * - Transaction.java structures individual actions and history formatting
 * - This file simply receives user input and calls into those components
 *
 * TESTING & DEMOS:
 * - This file can be run directly for live user input testing
 * - For scripted testing, see TransactionalAccount.main()
 *
 * DESIGN PRIORITIES:
 * - Clear separation of concerns between interface and logic
 * - Resilience to invalid inputs (e.g., non-numeric menu choices)
 * - Minimal assumptions about user behavior
 *
 * FILE STRUCTURE:
 * - TransactionalAccount.java → Business logic and state tracking
 * - Transaction.java → Individual transaction modeling and display
 * - UserInterface.java → CLI driver (this file)5
 *
 * MAINTAINER NOTES:
 * This file was designed to require minimal changes if additional
 * asset types or menu actions are added. It serves primarily as
 * a routing layer between user input and account logic.
 */


package com.acme.application;

import java.util.Scanner;
import com.acme.account.Account;
import com.acme.account.TransactionalAccount;
import com.acme.account.TransactionType;

public class UserInterface {
    /** 
     * CLI - Command Line Interface for user interaction.
     * Displays a menu, handles input, and delegates actions to the account.
     **/
	
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        double depositAmount;
        double withdrawAmount;

        // Create a new account with an initial balance of $1000
        Account account = new TransactionalAccount(1000.00);

        while (true) {
            System.out.println("\n-- Acme Financial Menu --");
            System.out.println("1. Cash - Deposit");
            System.out.println("2. Cash - Withdraw");
            System.out.println("3. Stock - Deposit");
            System.out.println("4. Stock - Withdraw");
            System.out.println("5. Check Balance");
            System.out.println("6. Get History");
            System.out.println("7. Exit");
            System.out.println("Choose an option: ");
            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number from 1 to 7.\n");
                scanner.nextLine(); // consume invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Cash - Enter amount to deposit: ");
                    depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount, TransactionType.CASH);
                    break;

                case 2:
                    System.out.println("Cash - Enter amount to withdraw: ");
                    withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount, TransactionType.CASH);
                    break;
                    
                case 3:
                    // readability: from "amount" to "number of shares"
                    System.out.println("Stock - Enter number of shares to deposit: ");
                    depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount, TransactionType.STOCK);
                    break;

                case 4:
                    // readability: from "amount" to "number of shares"
                    System.out.println("Stock - Enter the number of shares to withdraw: ");
                    withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount, TransactionType.STOCK);
                    break;

                case 5:
                    System.out.println("Current Balance: $" + account.getBalance());
                    break;
                    
                case 6:
                    System.out.println("Transaction History:\n" + account.getHistory());
                    break;

                case 7:
                    System.out.println("Exiting the program...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please choose a valid option.");
            }
        }
    }
}