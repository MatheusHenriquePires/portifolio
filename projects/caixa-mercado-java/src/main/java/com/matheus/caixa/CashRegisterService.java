package com.matheus.caixa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class CashRegisterService {
    private final Map<String, Product> inventory = new HashMap<>();
    private final List<SaleItem> currentCart = new ArrayList<>();
    private final List<Sale> salesHistory = new ArrayList<>();

    public void seedProducts() {
        addProduct("ARR-001", "Arroz 5kg", 29.90, 20);
        addProduct("FEJ-002", "Feijao 1kg", 8.50, 35);
        addProduct("LEI-003", "Leite 1L", 5.20, 50);
        addProduct("CAF-004", "Cafe 500g", 16.90, 25);
    }

    public boolean addProduct(String sku, String name, double price, int stock) {
        String key = sku.toUpperCase(Locale.ROOT);
        if (inventory.containsKey(key) || price <= 0 || stock < 0) {
            return false;
        }
        inventory.put(key, new Product(key, name, price, stock));
        return true;
    }

    public boolean restock(String sku, int quantity) {
        Product product = inventory.get(sku.toUpperCase(Locale.ROOT));
        if (product == null || quantity <= 0) {
            return false;
        }
        product.addStock(quantity);
        return true;
    }

    public boolean addItemToCart(String sku, int quantity) {
        Product product = inventory.get(sku.toUpperCase(Locale.ROOT));
        if (product == null || quantity <= 0 || product.getStock() < quantity) {
            return false;
        }

        product.removeStock(quantity);
        currentCart.add(new SaleItem(product.getSku(), product.getName(), quantity, product.getPrice()));
        return true;
    }

    public void cancelCurrentSale() {
        for (SaleItem item : currentCart) {
            Product product = inventory.get(item.getSku());
            if (product != null) {
                product.addStock(item.getQuantity());
            }
        }
        currentCart.clear();
    }

    public Sale finalizeSale(double discount, String paymentMethod) {
        if (currentCart.isEmpty()) {
            return null;
        }
        double subtotal = getCurrentSubtotal();
        if (discount < 0 || discount > subtotal) {
            return null;
        }

        double total = subtotal - discount;
        Sale sale = new Sale(
                UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT),
                currentCart,
                discount,
                total,
                paymentMethod
        );

        salesHistory.add(sale);
        currentCart.clear();
        return sale;
    }

    public List<Product> listProducts() {
        return new ArrayList<>(inventory.values());
    }

    public List<SaleItem> getCurrentCart() {
        return new ArrayList<>(currentCart);
    }

    public List<Sale> getSalesHistory() {
        return new ArrayList<>(salesHistory);
    }

    public double getCurrentSubtotal() {
        return currentCart.stream().mapToDouble(SaleItem::subtotal).sum();
    }
}
