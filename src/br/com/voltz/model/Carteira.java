package br.com.voltz.model;


import java.util.Date;

public class Carteira {
    private String idCarteira;
    private Usuario usuario;
    private Ativo ativo;
    private double quantidade;
    private Date data;

    public Carteira(String idCarteira, Usuario usuario, Ativo ativo, double quantidade, Date data) {
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



    public void adicionarQuantidade(double quantidade, Ativo ativo) {
        this.quantidade += quantidade;
        this.ativo = ativo;
    }

    public void removerQuantidade(double quantidade) {
        if (this.quantidade >= quantidade) {
            this.quantidade -= quantidade;
        } else {
            System.out.println("Quantidade insuficiente para remover.");
        }

    }

    public void getexibirInformacoes() {
        System.out.println("\n--- Informações da Carteira: " + getIdCarteira() + " ---"
            + "\n ID do Usuário: " + getUsuario()
            + "\n Ativo: " + (getAtivo() != null ? getAtivo().getNome() : "Nenhum ativo")
            + "\n Quantidade disponível: " + getQuantidade()
            + "\n Data: " + getData());
    }
}