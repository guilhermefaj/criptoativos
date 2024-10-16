package br.com.voltz.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;
    private List<Transacao> transacoes;
    private double saldoTotal;

    public Usuario(String id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.transacoes = new ArrayList<>();
        this.saldoTotal = 0.0;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void adicionarTransacao(Transacao transacao) {
        this.transacoes.add(transacao);
        if (transacao.getTipo().equalsIgnoreCase("Compra")) { //Diminui o valor
            saldoTotal -= transacao.getValor();
        } else if (transacao.getTipo().equalsIgnoreCase("Venda")) { //Aumenta o valor
            saldoTotal += transacao.getValor();
        }
    }

    public double getSaldoTotal() {
        return saldoTotal;
    }

    public void login() {
        System.out.println("Usuário " + nome + " logado.");
    }

    public void logout() {
        System.out.println("Usuário " + nome + " deslogado.");
    }
}
