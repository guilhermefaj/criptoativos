package br.com.voltz.model;
import br.com.voltz.enumerations.AtivosEnum;
import br.com.voltz.service.PlataformaWeb;


public class Ativo {
    private String idAtivo;
    private AtivosEnum nome;
    private double valorAtual;
    private double volatilidade;
    private PlataformaWeb plataformaWeb;

    public Ativo(String idAtivo, String s, double v) {
    }

    public Ativo(String idAtivo, AtivosEnum nome, double valorAtual, double volatilidade, PlataformaWeb idPlataforma) {
        this.idAtivo = idAtivo;
        this.nome = nome;
        this.valorAtual = valorAtual;
        this.volatilidade = volatilidade;
        this.plataformaWeb = idPlataforma;
    }
    public Ativo(String idAtivo, AtivosEnum nome, double valorAtual, double volatilidade) {
        this(idAtivo, nome, valorAtual, volatilidade, null);
    }
    public AtivosEnum getNome() {
        return nome;
    }

    public void setNome(AtivosEnum nome) {
        this.nome = nome;
    }


    public double getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(double valorAtual) {
        this.valorAtual = valorAtual;
    }

    public String getIdAtivo() {
        return idAtivo;
    }

    public void setIdAtivo(String idAtivo) {
        this.idAtivo = idAtivo;
    }


    public double getVolatilidade() {
        return volatilidade;
    }

    public void setVolatilidade(double volatilidade) {
        this.volatilidade = volatilidade;
    }

    public PlataformaWeb getPlataformaWeb() {
        return plataformaWeb;
    }

    public void setPlataformaWeb(PlataformaWeb plataformaWeb) {
        this.plataformaWeb = plataformaWeb;
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
