package br.com.voltz.model;


import java.util.Date;

public class Carteira {
    private String idCarteira;
    private Usuario usuario;
    private Ativo ativo;
    private double quantidade;
    private Date data;


    public Carteira() {
    }

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

    public java.sql.Date getData() { return (java.sql.Date) data; }

    public void setIdCarteira(String idCarteira) {
        this.idCarteira = idCarteira;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setAtivo(Ativo ativo) {
        this.ativo = ativo;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void adicionarQuantidade(double quantidade, Ativo ativo) {
        if (this.ativo == null || this.ativo.getNome().equals(ativo.getNome())) {
            this.quantidade += quantidade;
            this.ativo = ativo;
        } else {
            System.out.println("Erro: Ativo diferente do existente na carteira");
        }
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