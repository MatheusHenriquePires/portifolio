package com.matheus.bancario.service;

import com.matheus.bancario.model.Account;
import com.matheus.bancario.model.Account.Type;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    private final Map<String, Account> accounts = new LinkedHashMap<>();

    public AccountService() {
        // Contas de demonstração
        Account demo1 = new Account("Matheus Pires", Type.CORRENTE, 5000.00);
        Account demo2 = new Account("Maria Silva", Type.POUPANCA, 2500.00);
        accounts.put(demo1.getId(), demo1);
        accounts.put(demo2.getId(), demo2);
        // Algumas transações de exemplo
        demo1.deposit(1500.00, "Salário");
        demo1.withdraw(200.00, "Conta de luz");
        demo2.deposit(500.00, "Rendimentos");
    }

    public List<Account> getAll() {
        return new ArrayList<>(accounts.values());
    }

    public Account getById(String id) {
        Account acc = accounts.get(id);
        if (acc == null) throw new NoSuchElementException("Conta não encontrada: " + id);
        return acc;
    }

    public Account create(String ownerName, String type) {
        Type accountType = type.equalsIgnoreCase("POUPANCA") ? Type.POUPANCA : Type.CORRENTE;
        Account acc = new Account(ownerName, accountType, 0.0);
        accounts.put(acc.getId(), acc);
        return acc;
    }

    public Account deposit(String id, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Valor deve ser positivo");
        Account acc = getById(id);
        acc.deposit(amount, "Depósito");
        return acc;
    }

    public Account withdraw(String id, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Valor deve ser positivo");
        Account acc = getById(id);
        acc.withdraw(amount, "Saque");
        return acc;
    }

    public void transfer(String fromId, String toId, double amount) {
        if (fromId.equals(toId)) throw new IllegalArgumentException("Contas de origem e destino iguais");
        if (amount <= 0) throw new IllegalArgumentException("Valor deve ser positivo");
        Account from = getById(fromId);
        Account to = getById(toId);
        if (amount > from.getBalance()) throw new IllegalArgumentException("Saldo insuficiente");
        from.addTransferOut(amount, to.getOwnerName());
        to.addTransferIn(amount, from.getOwnerName());
    }
}
