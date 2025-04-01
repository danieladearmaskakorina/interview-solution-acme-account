package com.acme.account;

public class BasicAccount implements Account{
    private double balance;

    // Constructor
    public BasicAccount(double initialBalance) {
        if (initialBalance >= 0) {
            this.balance = initialBalance;
        } else {
            System.out.println("Initial balance cannot be negative.");
            this.balance = 0;
        }
    }

    // Deposit method
    @Override
    public void deposit(double amount, TransactionType type) {
    	if (type != TransactionType.CASH) {
    		System.out.println("Only cash deposits are supported.");
    	}
    	else if (amount <= 0.0) {
    		System.out.println("Deposit amount must be positive.");
    	}
    	else {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        }
    }

    // Withdrawal method
    @Override
    public void withdraw(double amount, TransactionType type) {
    	if (type != TransactionType.CASH) {
    		System.out.println("Only cash deposits are supported.");
    	}
    	else if (amount <= 0.0) {
    		System.out.println("Withdrawal amount must be positive.");
    	}
    	else if (amount > balance) {
            System.out.println("Insufficient balance.");
    	}
    	else {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
        }
    }

    // Get current balance
    @Override
    public double getBalance() {
        return this.balance; 
    }

	@Override
	// Print Transaction History
	public String getHistory() {
		return "Transactional history not available.";
	}
}
