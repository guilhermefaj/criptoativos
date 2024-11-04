package br.com.voltz.model;

import java.util.Date;

public abstract class Transacao {
    private String idTransacao;
    private double valor;
    private Date data;
    private Ativo ativo;
    private double taxa;
   
    public Transacao(String idTransacao, double valor, Ativo ativo, double taxa) {
        this.idTransacao = idTransacao;
        this.valor = valor;
        this.data = new Date();
        this.ativo = ativo;
        this.taxa = 0.015;
    }


    public String getIdTransacao() {
        return idTransacao;
    }

    public double getValor() {
        return valor;
    }



    public Date getData() {
        return data;
    }

    public Ativo getAtivo() {
        return ativo;
    }

    public double getTaxa() {
        return taxa;
    }

    public abstract String getexecutarTransacao();

    public abstract String getcancelarTransacao();

    public abstract String getTaxaTransacao();

}
