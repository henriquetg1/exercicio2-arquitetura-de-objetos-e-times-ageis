package br.insper.ecommerce;

import br.insper.ecommerce.cliente.Cliente;
import br.insper.ecommerce.cliente.ClienteService;
import br.insper.ecommerce.compra.Compra;
import br.insper.ecommerce.compra.CompraService;
import br.insper.ecommerce.compra.Item;
import br.insper.ecommerce.pagamento.Boleto;
import br.insper.ecommerce.pagamento.CartaoCredito;
import br.insper.ecommerce.pagamento.CartaoDebito;
import br.insper.ecommerce.pagamento.Pix;
import br.insper.ecommerce.produto.Produto;
import br.insper.ecommerce.produto.ProdutoService;

import java.time.LocalDateTime;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String opcao = "0";

        ClienteService clienteService = new ClienteService();
        ProdutoService produtoService = new ProdutoService();
        CompraService compraService = new CompraService();

        while (!opcao.equalsIgnoreCase("9")) {

            System.out.println("""
                    1 - Cadastrar Cliente
                    2 - Listar Clientes
                    3 - Excluir Cliente
                    4 - Cadastrar Produto
                    5 - Listar Produtos
                    6 - Excluir Produto
                    7 - Cadastrar Compra
                    8 - Listar Compras
                    9 - Sair
                    """);

            opcao = scanner.nextLine();

            if (opcao.equalsIgnoreCase("1")) {
                System.out.println("Digite o nome do cliente:");
                String nome = scanner.nextLine();
                System.out.println("Digite o CPF do cliente;");
                String cpf = scanner.nextLine();

                clienteService.cadastrarCliente(nome, cpf);
            }

            if (opcao.equalsIgnoreCase("2")) {
                clienteService.listarClientes();
            }

            if (opcao.equalsIgnoreCase("3")) {
                System.out.println("Digite o cpf do cliente para deletar:");
                String cpf = scanner.nextLine();

                clienteService.excluirCliente(cpf);
            }

            if (opcao.equalsIgnoreCase("4")) {
                System.out.println("Digite o nome do produto:");
                String nome = scanner.nextLine();
                System.out.println("Digite o preço do produto:");
                Double preco = Double.valueOf(scanner.nextLine());
                produtoService.cadastrarProduto(nome, preco);
            }

            if (opcao.equalsIgnoreCase("5")) {
                produtoService.listarProdutos();
            }

            if (opcao.equalsIgnoreCase("6")) {

                System.out.println("Digite o nome do produto para deletar:");
                String nome = scanner.nextLine();

                produtoService.excluirProduto(nome);
            }

            if (opcao.equalsIgnoreCase("7")) {
                Compra compra = new Compra(); // Cria uma nova compra antes do loop
                while (!opcao.equalsIgnoreCase("3")) {
                    System.out.println("""
                            1 - Adicionar Item
                            2 - Finalizar Compra
                            3 - Voltar
                            """);
                    opcao = scanner.nextLine();
                    if (opcao.equalsIgnoreCase("1")) {
                        System.out.println("Digite o nome do produto:");
                        String nomeProduto = scanner.nextLine();
                        Produto produto = produtoService.encontrarProduto(nomeProduto);

                        if (produto != null) {
                            System.out.println("Digite a quantidade:");
                            int quantidade = scanner.nextInt();
                            scanner.nextLine();

                            Item item = new Item(quantidade, produto);
                            compra.adicionarItem(item);
                            System.out.println("Item adicionado ao carrinho.");
                        } else {
                            System.out.println("Produto não encontrado.");
                        }
                    } else if (opcao.equalsIgnoreCase("2")) {
                        if (opcao.equalsIgnoreCase("2")) {
                            if (compra.getItens().isEmpty()) {
                                System.out.println("Não é possível finalizar a compra sem itens. Adicione pelo menos um item.");
                            } else {
                                System.out.println("Digite o cpf do cliente da compra:");
                                String cpf = scanner.nextLine();
                                Cliente cliente = clienteService.buscarCliente(cpf);
                                compra.calculaPrecoTotal();
                                double precoTotal = compra.getPrecoTotal();
                                System.out.println("O preço total da compra é: " + precoTotal);

                                if (cliente != null) {
                                    System.out.println("""
                                            Selecione um meio de pagamento:
                                            1 - Pix
                                            2 - Cartão de Crédito
                                            3 - Cartão de Débito
                                            4 - Boleto
                                            """);
                                    String meioPagamento = scanner.nextLine();

                                    if (meioPagamento.equalsIgnoreCase("1")) {
                                        System.out.println("Digite a chave de origem:");
                                        String chaveOrigem = scanner.nextLine();
                                        System.out.println("Digite o QR CODE:");
                                        String qrCode = scanner.nextLine();
                                        compra.setMeioPagamento(new Pix(true, LocalDateTime.now(), chaveOrigem, qrCode));
                                        compra.setCliente(cliente); // Define o cliente da compra
                                        compraService.registrarCompra(compra); // Registra a compra
                                        compra.limparItens();
                                        System.out.println("Compra finalizada. Obrigado!");
                                    } else if (meioPagamento.equalsIgnoreCase("2")) {
                                        System.out.println("Digite o número do cartão:");
                                        String numeroCartao = scanner.nextLine();
                                        System.out.println("Digite a bandeira do cartão:");
                                        String bandeira = scanner.nextLine();
                                        compra.setMeioPagamento(new CartaoCredito(true, LocalDateTime.now(), numeroCartao, bandeira));
                                        compra.setCliente(cliente); // Define o cliente da compra
                                        compraService.registrarCompra(compra); // Registra a compra
                                        compra.limparItens();
                                        System.out.println("Compra finalizada. Obrigado!");
                                    } else if (meioPagamento.equalsIgnoreCase("3")) {
                                        System.out.println("Digite o número do cartão:");
                                        String numeroCartao = scanner.nextLine();
                                        System.out.println("Digite a bandeira do cartão:");
                                        String bandeira = scanner.nextLine();
                                        compra.setMeioPagamento(new CartaoDebito(true, LocalDateTime.now(), numeroCartao, bandeira));
                                        compra.setCliente(cliente); // Define o cliente da compra
                                        compraService.registrarCompra(compra); // Registra a compra
                                        compra.limparItens();
                                        System.out.println("Compra finalizada. Obrigado!");
                                    } else if (meioPagamento.equalsIgnoreCase("4")) {
                                        System.out.println("Digite o código de barra:");
                                        String codigoBarra = scanner.nextLine();
                                        compra.setMeioPagamento(new Boleto(true, LocalDateTime.now(), codigoBarra));
                                        compra.setCliente(cliente); // Define o cliente da compra
                                        compraService.registrarCompra(compra); // Registra a compra
                                        compra.limparItens();
                                        System.out.println("Compra finalizada. Obrigado!");
                                    } else {
                                        System.out.println("Meio de pagamento inválido.");
                                    }
                                } else {
                                    System.out.println("Cliente não encontrado.");
                                }
                            }
                        }
                    }
                }
            }
            if (opcao.equalsIgnoreCase("8")) {
                compraService.listarCompras();
            }
        }
    }
}
