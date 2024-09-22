import java.util.Date;

public class Transacao {
    private String idTransacao;
    private double valor;
    private String tipo; // Compra ou Venda
    private Date data;
    private Ativo ativo;

    public Transacao(String idTransacao, double valor, String tipo, Ativo ativo) {
        this.idTransacao = idTransacao;
        this.valor = valor;
        this.tipo = tipo;
        this.data = new Date();
        this.ativo = ativo;
    }

    // Getters
    public String getIdTransacao() {
        return idTransacao;
    }

    public double getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public Date getData() {
        return data;
    }

    public Ativo getAtivo() {
        return ativo;
    }

    // Métodos
    public void executarTransacao() {
        System.out.println("Transação " + tipo + " de valor " + valor + " executada em " + data.toString() +
                " para o ativo " + ativo.getNome());
    }

    public void cancelarTransacao() {
        System.out.println("Transação " + idTransacao + " cancelada.");
    }
}
