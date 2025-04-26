package br.com.voltz.model;

import br.com.voltz.enumerations.TransacaoEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public abstract class Transacao {
    private String idTransacao;
    private double valor;
    private Date data;
    private Ativo ativo;
    private Usuario usuario;
    private TransacaoEnum tipo;
    private double taxa;


    public Transacao(String idTransacao, double valor, Date data, Ativo ativo, Usuario usuario, TransacaoEnum tipo, double taxa) {
        this.idTransacao = idTransacao;
        this.valor = valor;
        this.data = data;
        this.ativo = ativo;
        this.usuario = usuario;
        this.tipo = tipo;
        this.taxa = taxa;
    }

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

    public void setIdTransacao(String idTransacao) {
        this.idTransacao = idTransacao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Ativo getAtivo() {
        return ativo;
    }

    public void setAtivo(Ativo ativo) {
        this.ativo = ativo;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TransacaoEnum getTipo() {
        return tipo;
    }

    public void setTipo(TransacaoEnum tipo) {
        this.tipo = tipo;
    }


    public abstract String getexecutarTransacao();

    public abstract String getcancelarTransacao();

    public abstract String getTaxaTransacao();


}
