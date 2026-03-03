package com.matheus.bancario.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Account {

    public enum Type { CORRENTE, POUPANCA }

    private String id;
    private String ownerName;
    private Type type;
    private double balance;
    private List<Transaction> transactions;

    public Account() {}

    public Account(String ownerName, Type type, double initialBalance) {
        this.id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.ownerName = ownerName;
        this.type = type;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        if (initialBalance > 0) {
            this.transactions.add(new Transaction(
                Transaction.Type.DEPOSITO, initialBalance, "Depósito inicial", initialBalance
            ));
        }
    }

    public void deposit(double amount, String description) {
        this.balance += amount;
        transactions.add(new Transaction(Transaction.Type.DEPOSITO, amount, description, this.balance));
    }

    public void withdraw(double amount, String description) {
        if (amount > this.balance) throw new IllegalArgumentException("Saldo insuficiente");
        this.balance -= amount;
        transactions.add(new Transaction(Transaction.Type.SAQUE, amount, description, this.balance));
    }

    public void addTransferOut(double amount, String toName) {
        this.balance -= amount;
        transactions.add(new Transaction(Transaction.Type.TRANSFERENCIA_SAIDA, amount,
            "Transferência para " + toName, this.balance));
    }

    public void addTransferIn(double amount, String fromName) {
        this.balance += amount;
        transactions.add(new Transaction(Transaction.Type.TRANSFERENCIA_ENTRADA, amount,
            "Transferência de " + fromName, this.balance));
    }

    public String getId() { return id; }
    public String getOwnerName() { return ownerName; }
    public Type getType() { return type; }
    public double getBalance() { return balance; }
    public List<Transaction> getTransactions() { return transactions; }
}
