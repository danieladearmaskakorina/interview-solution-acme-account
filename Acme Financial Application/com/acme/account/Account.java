package com.acme.account;

public interface Account {

	//Deposit money into the account
    public void deposit(double amount, TransactionType type);

    //Withdraw money from the account
    public void withdraw(double amount, TransactionType type);

    //Get current account balance
    public double getBalance();
    
    //List transaction history
    public String getHistory();
}
