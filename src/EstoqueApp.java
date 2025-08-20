import java.io.*;
import java.util.Scanner;
//Teste
public class EstoqueApp {
    private static final String NOME_ARQUIVO = "produtos.csv";
    private static final int TAMANHO_ARRAY = 100;
    private static Produto[] produtos = new Produto[TAMANHO_ARRAY];
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE CONTROLE DE ESTOQUE - LIVRARIA ===\n");

        // Carregar produtos do arquivo CSV
        carregarProdutos();

        // Exibir menu
        int opcao;
        do {
            opcao = exibirMenu();
            processarOpcao(opcao);
        } while (opcao != 6);

        scanner.close();
    }

    private static void carregarProdutos() {
        System.out.println("Carregando produtos do arquivo...");

        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            int contador = 0;

            while ((linha = reader.readLine()) != null && contador < TAMANHO_ARRAY) {
                try {
                    String[] dados = linha.split(";");

                    // Validar se a linha tem todos os campos necessários
                    if (dados.length != 5) {
                        System.out.println("⚠️  Linha inválida ignorada: " + linha);
                        continue;
                    }

                    // Converter os dados
                    int id = Integer.parseInt(dados[0].trim());
                    String nome = dados[1].trim();
                    double preco = Double.parseDouble(dados[2].trim());
                    int quantidade = Integer.parseInt(dados[3].trim());
                    int estoqueMinimo = Integer.parseInt(dados[4].trim());

                    // Criar e adicionar o produto
                    produtos[contador] = new Produto(id, nome, preco, quantidade, estoqueMinimo);
                    contador++;

                } catch (NumberFormatException e) {
                    System.out.println("⚠️  Erro ao processar linha (dados inválidos): " + linha);
                }
            }

            System.out.println("✅ " + contador + " produtos carregados com sucesso!\n");

        } catch (FileNotFoundException e) {
            System.out.println("⚠️  Arquivo produtos.csv não encontrado. Iniciando com estoque vazio.");
        } catch (IOException e) {
            System.out.println("❌ Erro ao ler arquivo: " + e.getMessage());
        }
    }

    private static int exibirMenu() {
        System.out.println("=== MENU PRINCIPAL ===");
        System.out.println("1. Listar todos os produtos");
        System.out.println("2. Buscar produto por ID");
        System.out.println("3. Cadastrar novo produto");
        System.out.println("4. Atualizar quantidade de produto");
        System.out.println("5. Relatório de baixo estoque");
        System.out.println("6. Salvar e sair");
        System.out.print("Escolha uma opção: ");

        try {
            int opcao = Integer.parseInt(scanner.nextLine());
            System.out.println();
            return opcao;
        } catch (NumberFormatException e) {
            System.out.println("⚠️  Opção inválida! Digite um número.\n");
            return -1;
        }
    }

    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                listarTodosProdutos();
                break;
            case 2:
                buscarPorId();
                break;
            case 3:
                cadastrarNovoProduto();
                break;
            case 4:
                atualizarQuantidade();
                break;
            case 5:
                relatorioBaixoEstoque();
                break;
            case 6:
                salvarESair();
                break;
            default:
                System.out.println("⚠️  Opção inválida! Tente novamente.\n");
        }
    }

    private static void listarTodosProdutos() {
        System.out.println("=== LISTA DE TODOS OS PRODUTOS ===");

        boolean encontrouProdutos = false;
        for (Produto produto : produtos) {
            if (produto != null) {
                System.out.println(produto);
                encontrouProdutos = true;
            }
        }

        if (!encontrouProdutos) {
            System.out.println("Nenhum produto cadastrado.");
        }

        System.out.println();
    }

    private static void buscarPorId() {
        System.out.print("Digite o ID do produto: ");

        try {
            int id = Integer.parseInt(scanner.nextLine());
            Produto produto = encontrarProdutoPorId(id);

            if (produto != null) {
                System.out.println("✅ Produto encontrado:");
                System.out.println(produto);
            } else {
                System.out.println("❌ Produto com ID " + id + " não encontrado.");
            }

        } catch (NumberFormatException e) {
            System.out.println("⚠️  ID inválido! Digite um número.");
        }

        System.out.println();
    }

    private static void cadastrarNovoProduto() {
        System.out.println("=== CADASTRAR NOVO PRODUTO ===");

        // Encontrar primeira posição vazia
        int posicaoVazia = -1;
        for (int i = 0; i < produtos.length; i++) {
            if (produtos[i] == null) {
                posicaoVazia = i;
                break;
            }
        }

        if (posicaoVazia == -1) {
            System.out.println("❌ Estoque cheio! Não é possível cadastrar mais produtos.");
            System.out.println();
            return;
        }

        try {
            System.out.print("ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            // Verificar se ID já existe
            if (encontrarProdutoPorId(id) != null) {
                System.out.println("❌ Já existe um produto com este ID!");
                System.out.println();
                return;
            }

            System.out.print("Nome: ");
            String nome = scanner.nextLine().trim();

            System.out.print("Preço: R$ ");
            double preco = Double.parseDouble(scanner.nextLine());

            System.out.print("Quantidade: ");
            int quantidade = Integer.parseInt(scanner.nextLine());

            System.out.print("Estoque mínimo: ");
            int estoqueMinimo = Integer.parseInt(scanner.nextLine());

            // Criar e adicionar o produto
            produtos[posicaoVazia] = new Produto(id, nome, preco, quantidade, estoqueMinimo);

            System.out.println("✅ Produto cadastrado com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("⚠️  Erro: Digite valores numéricos válidos.");
        }

        System.out.println();
    }

    private static void atualizarQuantidade() {
        System.out.println("=== ATUALIZAR QUANTIDADE ===");

        try {
            System.out.print("Digite o ID do produto: ");
            int id = Integer.parseInt(scanner.nextLine());

            Produto produto = encontrarProdutoPorId(id);
            if (produto == null) {
                System.out.println("❌ Produto não encontrado.");
                System.out.println();
                return;
            }

            System.out.println("Produto: " + produto.getNome());
            System.out.println("Quantidade atual: " + produto.getQuantidade());

            System.out.println("Tipo de movimentação:");
            System.out.println("1. Entrada (adicionar)");
            System.out.println("2. Saída (remover)");
            System.out.print("Escolha: ");

            int tipoMovimento = Integer.parseInt(scanner.nextLine());

            System.out.print("Quantidade: ");
            int quantidade = Integer.parseInt(scanner.nextLine());

            if (quantidade < 0) {
                System.out.println("⚠️  Quantidade deve ser positiva.");
                System.out.println();
                return;
            }

            int quantidadeAtual = produto.getQuantidade();

            if (tipoMovimento == 1) { // Entrada
                produto.setQuantidade(quantidadeAtual + quantidade);
                System.out.println("✅ Entrada registrada! Nova quantidade: " + produto.getQuantidade());
            } else if (tipoMovimento == 2) { // Saída
                if (quantidade > quantidadeAtual) {
                    System.out.println("❌ Quantidade insuficiente em estoque!");
                } else {
                    produto.setQuantidade(quantidadeAtual - quantidade);
                    System.out.println("✅ Saída registrada! Nova quantidade: " + produto.getQuantidade());
                }
            } else {
                System.out.println("⚠️  Tipo de movimentação inválido.");
            }

        } catch (NumberFormatException e) {
            System.out.println("⚠️  Erro: Digite valores numéricos válidos.");
        }

        System.out.println();
    }

    private static void relatorioBaixoEstoque() {
        System.out.println("=== RELATÓRIO DE BAIXO ESTOQUE ===");

        boolean encontrouProdutos = false;
        for (Produto produto : produtos) {
            if (produto != null && produto.isBaixoEstoque()) {
                System.out.println("⚠️  " + produto);
                encontrouProdutos = true;
            }
        }

        if (!encontrouProdutos) {
            System.out.println("✅ Nenhum produto com estoque baixo!");
        }

        System.out.println();
    }

    private static void salvarESair() {
        System.out.println("Salvando dados...");

        try (PrintWriter writer = new PrintWriter(new FileWriter(NOME_ARQUIVO))) {

            for (Produto produto : produtos) {
                if (produto != null) {
                    writer.println(produto.toCSV());
                }
            }

            System.out.println("✅ Dados salvos com sucesso!");
            System.out.println("👋 Obrigado por usar o sistema!");

        } catch (IOException e) {
            System.out.println("❌ Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    private static Produto encontrarProdutoPorId(int id) {
        for (Produto produto : produtos) {
            if (produto != null && produto.getId() == id) {
                return produto;
            }
        }
        return null;
    }
}