package com.acme.account;

import java.util.ArrayList;
import java.util.List;

/**
 * TransactionalAccount implements the Account interface with support for
 * both CASH and STOCK transactions (ACME shares at $5.00/share).
 * 
 * - Supports deposit, withdrawal, balance calculation, and transaction logging
 * - Validates input:
 * 		- Amounts must be positive
 * 		- Stock units must be positive integers
 * 		- Menu types restricted to CASH and STOCK only
 * - Prevents overflow by using Math.toIntExact when casting stock amounts
 * - Maintains readable transaction history using a List<Transaction>
 */
public class TransactionalAccount implements Account {

    private double cashBalance;
    private int stockShares;
    private static final double STOCK_PRICE = 5.00;
    private List<Transaction> history = new ArrayList<>();

    /**
     * Initializes the account with a starting cash balance.
     *
     * @param initialCash the amount of cash to initialize the account with
     */
    public TransactionalAccount(double initialCash) {
        this.cashBalance = initialCash;
        this.stockShares = 0;
        this.history = new ArrayList<>();
    }

    /**
     * Handles both cash and stock deposits.
     * Validates input and prevents overflow on stock.
     *
     * @param amount amount to deposit (cash or stock units)
     * @param type transaction type (CASH or STOCK)
     */
    @Override
    public void deposit(double amount, TransactionType type) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }

        if (type == TransactionType.CASH) {
            cashBalance += amount;
			// Record deposit in session log for CLI history view
            history.add(new Transaction(type, "Deposit", amount));
            System.out.println("Cash deposited: $" + amount);
        } else if (type == TransactionType.STOCK) {
            if (amount % 1 != 0) {
                System.out.println("Stock deposits must be whole numbers.");
                return;
            }

            int units;
            try {
				// Prevents silent overflow from large double-to-int cast
                units = Math.toIntExact((long) amount);
            } catch (ArithmeticException e) {
                System.out.println("Stock deposit exceeds allowed number of shares.");
                return;
            }

            stockShares += units;
			// Record deposit in session log for CLI history view
            history.add(new Transaction(type, "Deposit", units));
            System.out.println("Stocks deposited: " + units);
        } else {
            System.out.println("Unsupported transaction type.");
        }
    }

    /**
     * Withdraws either cash or stock units from the account.
     * Validates input, checks balance, and guards against overflow.
     *
     * @param amount amount to withdraw
     * @param type transaction type (CASH or STOCK)
     */
    @Override
    public void withdraw(double amount, TransactionType type) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return;
        }

        if (type == TransactionType.CASH) {
            if (amount > cashBalance) {
                System.out.println("Insufficient funds.");
                return;
            }
            cashBalance -= amount;
			// Record withdrawal in session log for CLI history view
            history.add(new Transaction(type, "Withdrawal", amount));
            System.out.println("Cash withdrawn: $" + amount);
        } else if (type == TransactionType.STOCK) {
            if (amount % 1 != 0) {
                System.out.println("Stock withdrawals must be whole numbers.");
                return;
            }

            int units;
            try {
				// Prevents silent overflow from large double-to-int cast
                units = Math.toIntExact((long) amount);
            } catch (ArithmeticException e) {
                System.out.println("Stock withdrawal exceeds allowed number of shares.");
                return;
            }

            if (units > stockShares) {
                System.out.println("Insufficient shares.");
                return;
            }

            stockShares -= units;
			// Record withdrawal in session log for CLI history view
            history.add(new Transaction(type, "Withdrawal", units));
            System.out.println("Stocks withdrawn: " + units + " shares.");
        } else {
            System.out.println("Unsupported transaction type.");
        }
    }

    /**
     * Calculates the current account balance by summing cash
     * and stock value (shares * $5.00).
     *
     * @return the total account balance
     */
    @Override
    public double getBalance() {
        double stockValue = stockShares * STOCK_PRICE;
        return cashBalance + stockValue;
    }

    /**
     * Returns a numbered list of all transactions for the session.
     *
     * @return formatted transaction history string
     */
    @Override
    public String getHistory() {
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (Transaction t : history) {
            sb.append(count).append(". ").append(t).append("\n");
            count++;
        }
        return sb.toString();
    }

    /**
     * Self-contained test path for scripted validation
     */
    public static void main(String[] args) {
        TransactionalAccount account = new TransactionalAccount(100.00);

        System.out.println("\nInitial Balance: $" + account.getBalance());

        account.deposit(50.00, TransactionType.CASH);
        account.deposit(-10.00, TransactionType.CASH);
        account.deposit(3, TransactionType.STOCK);
        account.deposit(2.5, TransactionType.STOCK);
        account.withdraw(30.00, TransactionType.CASH);
        account.withdraw(2, TransactionType.STOCK);
        account.withdraw(10, TransactionType.STOCK);

        System.out.println("\nCurrent Total Balance: $" + account.getBalance());

        System.out.println("\nTransaction History:");
        System.out.println(account.getHistory());
    }
}