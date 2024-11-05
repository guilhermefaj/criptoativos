package br.com.voltz.service;

import br.com.voltz.model.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import br.com.voltz.model.Carteira;

public class PlataformaWeb {
    private String nome;
    private List<Usuario> usuarios;
    private List<Carteira> carteiras = new ArrayList<>();
    private final HashMap<Object, Object> carteirasPorUsuario;

    public PlataformaWeb(String nome) {
        this.nome = nome;
        this.usuarios = new ArrayList<>();
        this.carteirasPorUsuario = new HashMap<>();
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
      if (buscarCarteiraPorUsuario(usuario) == null ) {
          carteiras.add(carteira);
          System.out.println("Carteira adicionada para o usuário: "+ usuario);
      }else {
          System.out.println("Erro: Carteira ou usuário são nulos");
      }
    }
    public Object buscarCarteiraPorUsuario(Usuario usuario){
        for(Carteira carteira: carteiras) {
            if (carteira.getUsuario().equals(usuario)) {
                return carteira;
            }
        }
            return null;

    }
}
