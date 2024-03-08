package br.insper.ecommerce.compra;

import br.insper.ecommerce.cliente.Cliente;
import br.insper.ecommerce.pagamento.MeioPagamento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CompraService {
    ArrayList<Compra> compras = new ArrayList<>();

    public void registrarCompra(Compra compra) {
        compras.add(compra);
        System.out.println("Compra registrada com sucesso!");
    }

    public void listarCompras() {
        if (compras.isEmpty()) {
            System.out.println("Não há compras cadastradas.");
        } else {
            System.out.println("Lista de Compras:");
            for (Compra compra : compras) {
                System.out.println("Cliente: " + compra.getCliente().getNome());
                System.out.println("Preço da Compra: " + compra.getPrecoTotal());
                System.out.println("Itens:");

                for (Item item : compra.getItens()) {
                    System.out.println("   - Produto: " + item.getProduto().getNome());
                    System.out.println("     Quantidade: " + item.getQuantidade());
                    System.out.println("     Preço unitário: " + item.getProduto().getPreco());
                }
                System.out.println("--------------------------------------");
            }
        }
    }

}