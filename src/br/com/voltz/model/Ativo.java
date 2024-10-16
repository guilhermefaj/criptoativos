package br.com.voltz.model;

public class Ativo {
    private String idAtivo;
    private String nome;
    private double valorAtual;
    private double volatilidade;

    public Ativo(String idAtivo, String nome, double valorAtual, double volatilidade) {
        this.idAtivo = idAtivo;
        this.nome = nome;
        this.valorAtual = valorAtual;
        this.volatilidade = volatilidade;
    }

    public String getNome() {
        return nome;
    }

    public void atualizarValor(double novoValor) {
        this.valorAtual = novoValor;
        System.out.println("Valor do ativo " + nome + " atualizado para " + valorAtual);
    }
}
