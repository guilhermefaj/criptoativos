package br.com.voltz.view;


import br.com.voltz.dao.SegurancaDao;
import br.com.voltz.exception.entidadeNaoEncontrada;
import br.com.voltz.service.PlataformaWeb;
import br.com.voltz.service.Seguranca;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class SegurancaView {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SegurancaDao dao;
        try {
            dao = new SegurancaDao();
            int escolha;
            do {
                System.out.println("\n==Menu Segurança: == " +
                        "1.Cadastrar uma nova Segurança: " +
                        "2.Pesquisar Segurança por ID " +
                        "3.Listar Seguranças" +
                        "4.Atualizar Segurança por ID: " +
                        "5.Deletar Segurança " +
                        "0. Sair" +
                        "Escolha uma opção: ");
                escolha = sc.nextInt();
                switch (escolha) {
                    case 1:
                        cadastrar(sc, dao);
                        break;
                    case 2:
                        pesquisarSeguranca(sc, dao);
                        break;
                    case 3:
                        listarSeguranca(dao);
                        break;
                    case 4:
                        atualizarSeguranca(sc, dao);
                        break;
                    case 5:
                        removerSeguranca(sc, dao);
                        break;
                    case 0:
                        System.out.println("Finalizando o sistema ....");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } while (escolha != 0);
            dao.fecharConexao();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados " + e.getMessage());
        }

    }

    private static void cadastrar(Scanner sc, SegurancaDao dao) {
        System.out.println("Digite o nivel de criptografia: ");
        String nivelCriptografia = sc.next() + sc.nextLine();
        System.out.println("Digite o número de tentativas falhas:  ");
        int tentativasFalhas = Integer.parseInt(sc.nextLine());
        System.out.println("Digite o ID da plataforma: ");
        String idPlataforma = sc.next() + sc.nextLine();
        PlataformaWeb plataforma = new PlataformaWeb(idPlataforma);

        Seguranca seguranca = new Seguranca(null, nivelCriptografia, tentativasFalhas, plataforma);
        try {
            dao.cadastrarSeguranca(seguranca);
            System.out.println("Segurança cadastrada com sucesso");
            dao.fecharConexao();

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar Segurança: " + e.getMessage());
        }
    }

    private static void pesquisarSeguranca(Scanner sc, SegurancaDao dao) {
        System.out.println("Digite o ID da Segurança que deseja buscar: ");
        String id = sc.next() + sc.nextLine();
        try {
            Seguranca seguranca = dao.buscarSegurancaId(id);
            System.out.println("Segurança encontrada:");
            System.out.println("ID: " + seguranca.getIdSeguranca());
            System.out.println("Nível de Criptografia: " + seguranca.getNivelCriptografia());
            System.out.println("Tentativas Falhas: " + seguranca.getTentativasFalhas());
            System.out.println("ID Plataforma: " + seguranca.getPlataforma().getIdPlataforma());

        } catch (SQLException | entidadeNaoEncontrada e) {
            System.err.println("Erro ao buscar a segurança: " + e.getMessage());
        }

    }

    public static void listarSeguranca(SegurancaDao dao) {
        try {
            List<Seguranca> segurancas = dao.listarSeguranca();
            System.out.println("\nLista de Seguranca: ");
            for (Seguranca seguranca : segurancas) {
                System.out.println("-----------------------------");
                System.out.println("ID: " + seguranca.getIdSeguranca());
                System.out.println("Nível de Criptografia: " + seguranca.getNivelCriptografia());
                System.out.println("Tentativas Falhas: " + seguranca.getTentativasFalhas());
                System.out.println("ID Plataforma: " + seguranca.getPlataforma().getIdPlataforma());
            }
        } catch (SQLException | entidadeNaoEncontrada e) {
            System.err.println("Erro ao listar as Seguranças: " + e.getMessage());
        }
    }

    public static void atualizarSeguranca(Scanner sc, SegurancaDao dao) {
        System.out.println("Digite o ID da segurança que deseja atualizar:");
        String id = sc.next() + sc.nextLine();
        try {
            Seguranca seguranca = dao.buscarSegurancaId(id);
            System.out.println("Digite o nível de criptografia atualizado: ");
            String nivelCriptografia = sc.next() + sc.nextLine();
            System.out.println("Digite o número de tentativas falhas atualizado: ");
            int tentativasFalhas = Integer.parseInt(sc.nextLine());
            System.out.println("Digite o novo ID da plataforma: ");
            String idPlataforma = sc.nextLine();

            PlataformaWeb plataforma = new PlataformaWeb(idPlataforma);

            seguranca.setNivelCriptografia(nivelCriptografia);
            seguranca.setTentativasFalhas(tentativasFalhas);
            seguranca.setPlataforma(plataforma);
            dao.atualizarDadosSeguranca(seguranca, id);
            System.out.println("Segurança atualizada com sucesso!");
            System.out.println("ID: " + seguranca.getIdSeguranca());
            System.out.println("Nível de Criptografia: " + seguranca.getNivelCriptografia());
            System.out.println("Tentativas Falhas: " + seguranca.getTentativasFalhas());
            System.out.println("ID Plataforma: " + seguranca.getPlataforma().getIdPlataforma());

        } catch (SQLException | entidadeNaoEncontrada e) {
            System.err.println("Erro ao atualizar a segurança: " + e.getMessage());
        }

    }

    public static void removerSeguranca(Scanner sc, SegurancaDao dao) {
        System.out.println("Digite o ID da segurança que deseja  deletar: ");
        String id = sc.next() + sc.nextLine();
        try {
            Seguranca seguranca = dao.buscarSegurancaId(id);
            System.out.println("Segurança Encontrada: ");
            System.out.println("ID: " + seguranca.getIdSeguranca());
            System.out.println("Nível de Criptografia: " + seguranca.getNivelCriptografia());
            System.out.println("Tentativas Falhas: " + seguranca.getTentativasFalhas());
            System.out.println("ID Plataforma: " + seguranca.getPlataforma().getIdPlataforma());
            System.out.print("Tem certeza que deseja deletar esta segurança? (s/n): ");
            String confirmacao = sc.next().trim().toLowerCase();
            if (confirmacao.equals("s")) {
                dao.deleteSeguranca(id);
                System.out.println("Segurança deletada com sucesso!");
            } else {
                System.out.println("Ação cancelada. A segurança não foi deletada.");
            }
        } catch (SQLException | entidadeNaoEncontrada e) {
            System.err.println("Erro ao deletar a segurança: " + e.getMessage());
        }
    }
}

