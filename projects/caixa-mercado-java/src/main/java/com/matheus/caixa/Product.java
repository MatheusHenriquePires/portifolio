package com.matheus.caixa;

public class Product {
    private final String sku;
    private final String name;
    private final double price;
    private int stock;

    public Product(String sku, String name, double price, int stock) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void addStock(int quantity) {
        this.stock += quantity;
    }

    public void removeStock(int quantity) {
        this.stock -= quantity;
    }
}
