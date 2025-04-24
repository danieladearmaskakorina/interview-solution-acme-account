package com.acme.account;

/**
 * ------------------------------------------------------------------
 * SYSTEM DESIGN NOTES (Embedded Overview for Reviewers)
 * ------------------------------------------------------------------
 *
 * SYSTEM PURPOSE:
 * This class represents a single transaction performed on an account,
 * either a deposit or a withdrawal of CASH or STOCK (ACME shares).
 *
 * WHY THIS STRUCTURE:
 * Transaction is defined as its own object to support:
 * - Clean, readable history formatting
 * - Logical separation from business logic in TransactionalAccount
 * - Easy extension to new transaction types or attributes (e.g., timestamp)
 *
 * FIELDS:
 * - TransactionType type: distinguishes between CASH and STOCK
 * - String action: either "Deposit" or "Withdraw"
 * - double amount: represents value of cash or number of shares
 *
 * OUTPUT:
 * toString() generates a human-readable entry used by getHistory()
 *
 * NOTE:
 * While only CASH and STOCK are supported, this structure allows
 * extensibility for future asset types, transaction metadata, or IDs.
 */
public class Transaction {

    private TransactionType type;
    private String action;
    private double amount;

    /**
     * Constructs a new Transaction record.
     *
     * @param type the type of asset (CASH or STOCK)
     * @param action the action performed ("Deposit" or "Withdraw")
     * @param amount the amount of cash or number of shares involved
     */
    public Transaction(TransactionType type, String action, double amount) {
        this.type = type;
        this.action = action;
        this.amount = amount;
    }

    /**
     * Formats the transaction into a displayable string for history output.
     * - Cash transactions show as "$xx.xx"
     * - Stock transactions show as whole units
     *
     * @return formatted string representing the transaction
     */
    @Override
    public String toString() {
        String typeStr = (type == TransactionType.CASH) ? "Cash" : "Stock";
        String amountStr = (type == TransactionType.CASH)
                ? String.format("$%.2f", amount)
                : String.valueOf((int) amount); // truncate for display
        return typeStr + " - " + action + " - " + amountStr;
    }
}