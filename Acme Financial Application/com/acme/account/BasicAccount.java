package com.acme.account;

/**
 * A simple implementation of the Account interface that supports
 * only CASH transactions. No transaction history is stored.
 */
public class BasicAccount implements Account {

    private double balance; // Tracks total cash in the account

    /**
     * Constructs a BasicAccount with an initial balance.
     * If the initial balance is negative, defaults to 0.
     *
     * @param initialBalance the starting balance
     */
    public BasicAccount(double initialBalance) {
        if (initialBalance >= 0) {
            this.balance = initialBalance;
        } else {
            System.out.println("Initial balance cannot be negative.");
            this.balance = 0;
        }
    }

    /**
     * Deposits cash into the account.
     * Only CASH transactions are accepted.
     *
     * @param amount the amount to deposit
     * @param type must be TransactionType.CASH
     */
    @Override
    public void deposit(double amount, TransactionType type) {
        if (type != TransactionType.CASH) {
            System.out.println("Only cash deposits are supported.");
        } else if (amount <= 0.0) {
            System.out.println("Deposit amount must be positive.");
        } else {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        }
    }

    /**
     * Withdraws cash from the account.
     * Only CASH transactions are accepted. Rejects overdrafts.
     *
     * @param amount the amount to withdraw
     * @param type must be TransactionType.CASH
     */
    @Override
    public void withdraw(double amount, TransactionType type) {
        if (type != TransactionType.CASH) {
            System.out.println("Only cash withdrawals are supported.");
        } else if (amount <= 0.0) {
            System.out.println("Withdrawal amount must be positive.");
        } else if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
        }
    }

    /**
     * Returns the current cash balance.
     *
     * @return the balance
     */
    @Override
    public double getBalance() {
        return this.balance;
    }

    /**
     * Returns a static message, as transaction history
     * is not implemented in this account type.
     *
     * @return message indicating no history
     */
    @Override
    public String getHistory() {
        return "Transactional history not available.";
    }
}