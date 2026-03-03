package com.matheus.caixa;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final Scanner SC = new Scanner(System.in);
    private static final NumberFormat BRL = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public static void main(String[] args) {
        CashRegisterService service = new CashRegisterService();
        service.seedProducts();

        while (true) {
            printMenu();
            String option = SC.nextLine().trim();

            switch (option) {
                case "1" -> listStock(service);
                case "2" -> registerProduct(service);
                case "3" -> restockProduct(service);
                case "4" -> addItemToSale(service);
                case "5" -> viewCurrentSale(service);
                case "6" -> closeSale(service);
                case "7" -> cancelSale(service);
                case "8" -> listSales(service);
                case "0" -> {
                    System.out.println("Saindo do sistema de caixa...");
                    return;
                }
                default -> System.out.println("Opcao invalida.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===== CAIXA DE MERCADO =====");
        System.out.println("1. Listar estoque");
        System.out.println("2. Cadastrar novo produto");
        System.out.println("3. Repor estoque");
        System.out.println("4. Adicionar item a venda atual");
        System.out.println("5. Ver venda atual");
        System.out.println("6. Finalizar venda");
        System.out.println("7. Cancelar venda atual");
        System.out.println("8. Historico de vendas");
        System.out.println("0. Sair");
        System.out.print("Escolha: ");
    }

    private static void listStock(CashRegisterService service) {
        System.out.println("\n--- ESTOQUE ---");
        for (Product p : service.listProducts()) {
            System.out.printf("%s | %s | %s | estoque: %d%n", p.getSku(), p.getName(), BRL.format(p.getPrice()), p.getStock());
        }
    }

    private static void registerProduct(CashRegisterService service) {
        System.out.print("SKU: ");
        String sku = SC.nextLine();
        System.out.print("Nome: ");
        String name = SC.nextLine();
        System.out.print("Preco: ");
        double price = parseDouble(SC.nextLine());
        System.out.print("Estoque inicial: ");
        int stock = parseInt(SC.nextLine());

        boolean ok = service.addProduct(sku, name, price, stock);
        System.out.println(ok ? "Produto cadastrado com sucesso." : "Falha ao cadastrar produto.");
    }

    private static void restockProduct(CashRegisterService service) {
        System.out.print("SKU do produto: ");
        String sku = SC.nextLine();
        System.out.print("Quantidade para repor: ");
        int quantity = parseInt(SC.nextLine());

        boolean ok = service.restock(sku, quantity);
        System.out.println(ok ? "Estoque atualizado." : "Nao foi possivel repor estoque.");
    }

    private static void addItemToSale(CashRegisterService service) {
        System.out.print("SKU: ");
        String sku = SC.nextLine();
        System.out.print("Quantidade: ");
        int qty = parseInt(SC.nextLine());

        boolean ok = service.addItemToCart(sku, qty);
        System.out.println(ok ? "Item adicionado a venda." : "Falha ao adicionar item (SKU invalido ou estoque insuficiente).");
    }

    private static void viewCurrentSale(CashRegisterService service) {
        List<SaleItem> cart = service.getCurrentCart();
        if (cart.isEmpty()) {
            System.out.println("Venda atual vazia.");
            return;
        }

        System.out.println("\n--- VENDA ATUAL ---");
        for (SaleItem i : cart) {
            System.out.printf("%s x%d | %s | subtotal: %s%n", i.getName(), i.getQuantity(), BRL.format(i.getUnitPrice()), BRL.format(i.subtotal()));
        }
        System.out.println("Subtotal: " + BRL.format(service.getCurrentSubtotal()));
    }

    private static void closeSale(CashRegisterService service) {
        if (service.getCurrentCart().isEmpty()) {
            System.out.println("Nao ha itens na venda atual.");
            return;
        }

        viewCurrentSale(service);
        System.out.print("Desconto (valor): ");
        double discount = parseDouble(SC.nextLine());
        System.out.print("Forma de pagamento (dinheiro/cartao/pix): ");
        String payment = SC.nextLine();

        Sale sale = service.finalizeSale(discount, payment);
        if (sale == null) {
            System.out.println("Nao foi possivel finalizar venda (desconto invalido).\n");
            return;
        }

        System.out.println("\n--- CUPOM ---");
        System.out.println("Venda: " + sale.getId());
        System.out.println("Data: " + sale.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        for (SaleItem item : sale.getItems()) {
            System.out.printf("%s x%d - %s%n", item.getName(), item.getQuantity(), BRL.format(item.subtotal()));
        }
        System.out.println("Desconto: " + BRL.format(sale.getDiscount()));
        System.out.println("Total: " + BRL.format(sale.getTotal()));
        System.out.println("Pagamento: " + sale.getPaymentMethod());

        if ("dinheiro".equalsIgnoreCase(sale.getPaymentMethod())) {
            System.out.print("Valor recebido: ");
            double paid = parseDouble(SC.nextLine());
            if (paid >= sale.getTotal()) {
                System.out.println("Troco: " + BRL.format(paid - sale.getTotal()));
            } else {
                System.out.println("Valor insuficiente. Falta: " + BRL.format(sale.getTotal() - paid));
            }
        }
    }

    private static void cancelSale(CashRegisterService service) {
        service.cancelCurrentSale();
        System.out.println("Venda atual cancelada e estoque devolvido.");
    }

    private static void listSales(CashRegisterService service) {
        List<Sale> sales = service.getSalesHistory();
        if (sales.isEmpty()) {
            System.out.println("Nenhuma venda realizada.");
            return;
        }

        System.out.println("\n--- HISTORICO DE VENDAS ---");
        for (Sale sale : sales) {
            System.out.printf("%s | %s | itens: %d | total: %s | pagamento: %s%n",
                    sale.getId(),
                    sale.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM HH:mm")),
                    sale.getItems().size(),
                    BRL.format(sale.getTotal()),
                    sale.getPaymentMethod());
        }
    }

    private static int parseInt(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (Exception e) {
            return -1;
        }
    }

    private static double parseDouble(String text) {
        try {
            return Double.parseDouble(text.trim().replace(',', '.'));
        } catch (Exception e) {
            return -1;
        }
    }
}
