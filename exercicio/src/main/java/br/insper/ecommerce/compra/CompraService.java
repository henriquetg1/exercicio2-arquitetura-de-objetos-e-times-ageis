package br.insper.ecommerce.compra;
import java.util.ArrayList;

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
                }
                System.out.println(" ");
            }
        }
    }