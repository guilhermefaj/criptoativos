package br.com.voltz.model;

import java.util.Date;

public class Carteira {
    private String idCarteira;
    private Usuario usuario;
    private Ativo ativo;
    private double quantidade;
    private Date data;

    public Carteira(String idCarteira, Usuario usuario, Ativo ativo, double quantidade) {
        this.idCarteira = idCarteira;
        this.usuario = usuario;
        this.quantidade = quantidade;
        this.ativo = ativo;
        this.data = new Date();
    }

    public String getIdCarteira() { return idCarteira; }

    public Usuario getUsuario() { return usuario; }

    public Ativo getAtivo() { return ativo; }

    public double getQuantidade() { return quantidade; }

    public Date getData() { return data; }

    public void adicionarQuantidade(double quantidade) {
        this.quantidade += quantidade;
    }

    public void removerQuantidade(double quantidade) {
        this.quantidade -= quantidade;
    }

    public void exibirInformacoes() {
        System.out.println("\n--- Informações da Carteira: " + getIdCarteira() + " ---"
            + "\n ID do Usuário: " + getUsuario()
            + ", ID do Ativo: " + getAtivo()
            + ", Quantidade: " + getQuantidade()
            + ", Data: " + getData());

    }
}