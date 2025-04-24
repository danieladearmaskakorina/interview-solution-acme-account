package com.acme.account;

/**
 * Account interface that defines the required operations
 * for any account implementation.
 *
 * Any class implementing this interface must provide:
 * - deposit and withdrawal behavior
 * - balance retrieval
 * - transaction history access
 */
public interface Account {

    /**
     * Deposit funds into the account.
     *
     * @param amount the amount to deposit
     * @param type the transaction type (CASH or STOCK)
     */
    public void deposit(double amount, TransactionType type);

    /**
     * Withdraw funds from the account.
     *
     * @param amount the amount to withdraw
     * @param type the transaction type (CASH or STOCK)
     */
    public void withdraw(double amount, TransactionType type);

    /**
     * Get the current account balance.
     *
     * @return the total balance (cash + stock value)
     */
    public double getBalance();
    
    /**
     * Get a string-formatted list of all transactions
     * recorded during the session.
     *
     * @return transaction history as a printable string
     */
    public String getHistory();
}
