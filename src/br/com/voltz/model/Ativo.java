package br.com.voltz.model;
import java.util.ArrayList;
import java.util.List;


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

    public double getValorAtual() {
        return valorAtual;
    }

    public void atualizarValor(double novoValor) {
        this.valorAtual = novoValor;
        System.out.println("Valor do ativo " + nome + " atualizado para " + valorAtual);
    }

    public String getId() {
        return idAtivo;
    }

    @Override
    public String toString() {
        return  "Nome: " + getNome() + "Valor Atual: " + getValorAtual();
    }


}
