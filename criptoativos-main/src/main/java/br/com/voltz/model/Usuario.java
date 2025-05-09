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
    private  Carteira carteira;

    public Usuario() {
    }

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

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public boolean gettemCarteira() {
        return carteira != null;
    }

    public Carteira getCarteira() {
        return carteira;
    }

    public void setCarteira(Carteira carteira) {
        this.carteira = carteira;
    }

    public void adicionarTransacao(Transacao transacao) {
        this.transacoes.add(transacao);
        if (transacao instanceof Venda) {
            saldoTotal -= transacao.getValor();
        } else if (transacao instanceof Compra) {
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

    @Override
    public String toString() {
        return getId() + ", Nome: " + getNome() + ", Email: " + getEmail();
    }

}
