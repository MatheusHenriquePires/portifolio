package com.matheus.bancario.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    public enum Type { DEPOSITO, SAQUE, TRANSFERENCIA_ENTRADA, TRANSFERENCIA_SAIDA }

    private String id;
    private Type type;
    private double amount;
    private String description;
    private LocalDateTime date;
    private double balanceAfter;

    public Transaction() {}

    public Transaction(Type type, double amount, String description, double balanceAfter) {
        this.id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = LocalDateTime.now();
        this.balanceAfter = balanceAfter;
    }

    public String getId() { return id; }
    public Type getType() { return type; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public LocalDateTime getDate() { return date; }
    public double getBalanceAfter() { return balanceAfter; }
}
