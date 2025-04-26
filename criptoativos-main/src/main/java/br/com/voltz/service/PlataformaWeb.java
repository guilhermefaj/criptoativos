package br.com.voltz.service;

import br.com.voltz.model.Ativo;
import br.com.voltz.model.Usuario;

import java.util.*;

import br.com.voltz.model.Carteira;

public class PlataformaWeb {
    private String nome;
    private String idPlataforma;
    private List<Usuario> usuarios;
    private List<Carteira> carteiras = new ArrayList<>();
    private final HashMap<Usuario, Carteira> carteirasPorUsuario;

    public PlataformaWeb(String nome) {
        this.idPlataforma = UUID.randomUUID().toString();
        this.nome = nome;
        this.usuarios = new ArrayList<>();
        this.carteiras = new ArrayList<>();
        this.carteirasPorUsuario = new HashMap<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdPlataforma() {
        return idPlataforma;
    }

    public void setIdPlataforma(String idPlataforma) {
        this.idPlataforma = idPlataforma;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Carteira> getCarteiras() {
        return carteiras;
    }

    public void setCarteiras(List<Carteira> carteiras) {
        this.carteiras = carteiras;
    }

    public HashMap<Usuario, Carteira> getCarteirasPorUsuario() {
        return carteirasPorUsuario;
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return usuario;
            }
        }
        return null;
    }

    public void exibirRelatorios(Monitoramento monitoramento) {
        monitoramento.gerarRelatorioDiario();
        System.out.println("Relatórios exibidos.");
    }

    public boolean autenticarUsuario(String email, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }

    public void registrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        System.out.println("Usuário registrado: " + usuario.getNome());
    }

    public void gerarGraficoMonitoramento(Monitoramento monitoramento) {
        System.out.println("Gráfico gerado com base no monitoramento.");
    }
    public void adicionarCarteira(Carteira carteira, Usuario usuario){
        if(carteirasPorUsuario.containsKey(usuario)){
            System.out.println("ERRO : O usuário já possui uma carteira associada.");
        }else{
            carteiras.add(carteira);
            carteirasPorUsuario.put(usuario, carteira);
            System.out.println("Carteira adicionada com sucesso para o usuário: "+ usuario.getNome() );
        }
    }
    public Carteira buscarCarteiraPorUsuario(Usuario usuario){
        for ( Carteira carteira: carteiras){
            if(carteira.getUsuario().equals(usuario)){
                return carteira;
            }
        }
        return null;
    }
    public List<Usuario> getUsuarios() { return usuarios; }

}