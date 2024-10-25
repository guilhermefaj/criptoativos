package br.com.voltz.model;

public class Compra extends Transacao {
    private String idCompra;

    public Compra(String idTransacao, double valor, Ativo ativo, double taxa) {
        super(idTransacao, valor + taxa, ativo, taxa);
    }

    public Compra(String idTransacao, double valor, Ativo ativo, double taxa, String idCompra) {
        super(idTransacao, valor + taxa, ativo, taxa);
        this.idCompra = idCompra;
    }

    public String getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(String idCompra) {
        this.idCompra = idCompra;
    }

    @Override
    public String getTaxaTransacao() {
        double valorDaTaxa = getValor() * getTaxa();
        String taxaFormatada = String.format("%.2f", (getValor() * getTaxa()));
        return "Taxa Aplicada: R$ " + taxaFormatada;
    }

    @Override
    public String getexecutarTransacao() {
        double valorComTaxa = (getValor() * getTaxa())- getValor();

        String valorFormatado = String.format("%.2f", valorComTaxa);

        return "Compra " + idCompra + " de valor " + valorFormatado + " executada em " + getData().toString() +
                " para o ativo " + getAtivo().getNome() +
                getTaxaTransacao();
    }

    @Override
    public String getcancelarTransacao() {
        return "Compra" + idCompra+ " Valor: "+ getValor() + " Dia: " + getData() + " cancelada.";

    }
}



