package com.matheus.caixa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Sale {
    private final String id;
    private final LocalDateTime createdAt;
    private final List<SaleItem> items;
    private final double discount;
    private final double total;
    private final String paymentMethod;

    public Sale(String id, List<SaleItem> items, double discount, double total, String paymentMethod) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.items = new ArrayList<>(items);
        this.discount = discount;
        this.total = total;
        this.paymentMethod = paymentMethod;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<SaleItem> getItems() {
        return items;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotal() {
        return total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}
