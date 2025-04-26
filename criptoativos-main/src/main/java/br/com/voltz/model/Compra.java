package br.com.voltz.model;

import br.com.voltz.enumerations.TransacaoEnum;
import br.com.voltz.service.PlataformaWeb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Compra extends Transacao {
    private String idCompra;


    public Compra (String idTransacao, double valor, Date data){
        super(idTransacao,valor,data,null,null, TransacaoEnum.COMPRA, 0.015);
    }
    public Compra(String idTransacao, double valor, Ativo ativo, double taxa, String idCompra) {
        super(idTransacao, valor, ativo, taxa);
        this.idCompra = idCompra;
    }

    public Compra(String idTransacao, double valor, Ativo ativo, double taxa) {
        super(idTransacao, valor, ativo, taxa);
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
        double valorComTaxa = getValor() - (getValor() * getTaxa());
        String valorFormatado = String.format("%.2f", valorComTaxa);

        return String.format(
                "Compra %s de valor R$ %.2f executada em %s para o ativo %s. %s",
                idCompra,
                valorComTaxa,
                getData(),
                getAtivo().getNome(),
                getTaxaTransacao()
        );
    }
    @Override
    public String getcancelarTransacao() {
        return "Compra" + idCompra+ " Valor: "+ getValor() + " Dia: " + getData() + " cancelada.";

    }

}



