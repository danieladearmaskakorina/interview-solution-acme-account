package com.acme.account;

/**
 * Defines supported transaction types for the account system.
 * 
 * Only CASH and STOCK are implemented in this iteration.
 * Other types may be added in the future.
 */
public enum TransactionType {
    CASH,
    // MUTUALFUND, // Out of scope
    // BOND,       // Out of scope
    STOCK;

    /**
     * Determines if the given type is currently supported
     * by the system (CASH or STOCK only).
     *
     * This allows for re-usable handling of future types
     * without hardcoded rejection logic in the core account logic.
     *
     * @param type the transaction type to check
     * @return true if supported, false otherwise
     */
    public static boolean isSupported(TransactionType type) {
        return (type == CASH || type == STOCK);
    }
}