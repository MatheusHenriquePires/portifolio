# Sistema de Caixa de Mercado (Java)

Projeto de terminal em Java para simular um caixa de mercado com:

- Cadastro de produtos no estoque
- Reposição e baixa de estoque
- Nova venda com múltiplos itens
- Cálculo de subtotal, desconto e total
- Finalização com dinheiro/cartão/pix
- Cálculo de troco (quando dinheiro)
- Histórico de vendas

## Requisitos

- Java 21+
- Maven 3.9+

## Executar

```bash
cd projects/caixa-mercado-java
mvn exec:java
```

## Estrutura

- `Main.java`: menu e fluxo do caixa
- `Product.java`: entidade de produto
- `SaleItem.java`: item da venda
- `Sale.java`: venda fechada
- `CashRegisterService.java`: regras de negócio
