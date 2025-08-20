public class Produto {
    // Atributos privados.
    private int id;
    private String nome;
    private double preco;
    private int quantidade;
    private int estoqueMinimo;

    // Construtor padrão
    public Produto() {
    }

    // Construtor com parâmetros
    public Produto(int id, String nome, double preco, int quantidade, int estoqueMinimo) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.estoqueMinimo = estoqueMinimo;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(int estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    // Método toString para exibição formatada
    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s | Preço: R$ %.2f | Quantidade: %d | Estoque Mínimo: %d",
                id, nome, preco, quantidade, estoqueMinimo);
    }

    // Método para verificar se está em baixo estoque
    public boolean isBaixoEstoque() {
        return quantidade < estoqueMinimo;
    }

    // Método para converter para formato CSV
    public String toCSV() {
        return id + ";" + nome + ";" + preco + ";" + quantidade + ";" + estoqueMinimo;
    }
}