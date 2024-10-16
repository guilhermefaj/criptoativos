package br.com.voltz.service;

import br.com.voltz.model.Usuario;

import java.util.List;
import java.util.ArrayList;

public class PlataformaWeb {
    private String nome;
    private List<Usuario> usuarios;

    public PlataformaWeb(String nome) {
        this.nome = nome;
        this.usuarios = new ArrayList<>();
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
}
