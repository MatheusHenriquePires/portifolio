package com.matheus.bancario.controller;

import com.matheus.bancario.model.Account;
import com.matheus.bancario.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/accounts")
    public List<Account> getAll() {
        return service.getAll();
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> create(@RequestBody Map<String, String> body) {
        try {
            String name = body.get("ownerName");
            String type = body.getOrDefault("type", "CORRENTE");
            if (name == null || name.isBlank()) return ResponseEntity.badRequest().body(Map.of("error", "Nome obrigatório"));
            return ResponseEntity.ok(service.create(name, type));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(service.getById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/accounts/{id}/deposit")
    public ResponseEntity<?> deposit(@PathVariable String id, @RequestBody Map<String, Double> body) {
        try {
            return ResponseEntity.ok(service.deposit(id, body.get("amount")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/accounts/{id}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable String id, @RequestBody Map<String, Double> body) {
        try {
            return ResponseEntity.ok(service.withdraw(id, body.get("amount")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody Map<String, Object> body) {
        try {
            String fromId = (String) body.get("fromId");
            String toId   = (String) body.get("toId");
            double amount = ((Number) body.get("amount")).doubleValue();
            service.transfer(fromId, toId, amount);
            return ResponseEntity.ok(Map.of("message", "Transferência realizada com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
