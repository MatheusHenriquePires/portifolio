package com.matheus.caixa;

public class SaleItem {
    private final String sku;
    private final String name;
    private final int quantity;
    private final double unitPrice;

    public SaleItem(String sku, String name, int quantity, double unitPrice) {
        this.sku = sku;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double subtotal() {
        return quantity * unitPrice;
    }
}
