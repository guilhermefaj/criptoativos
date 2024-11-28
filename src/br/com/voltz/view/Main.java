package br.com.voltz.view;

import br.com.voltz.model.*;
import br.com.voltz.service.Monitoramento;
import br.com.voltz.service.PlataformaWeb;
import br.com.voltz.service.Seguranca;
import br.com.voltz.model.Carteira;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PlataformaWeb plataforma = null;
        Seguranca seguranca = null;
        Monitoramento monitoramento = null;
        Usuario usuarioLogado = null;

        try {
            plataforma = new PlataformaWeb("Plataforma Cripto");
            seguranca = new Seguranca("AES-256");
            monitoramento = new Monitoramento("monitor001");
            
            carregarUsuariosDeArquivo(plataforma);

        } catch (Exception e) {
            System.out.println("Erro ao inicializar os serviços: " + e.getMessage());
            return;
        }

        boolean continuar = true;

        while (continuar) {
            System.out.println("\n--- Bem-vindo à Plataforma Cripto ---");
            System.out.println("1. Registrar novo usuário");
            System.out.println("2. Login");
            System.out.println("3. Realizar Venda de um Criptoativo:");
            System.out.println("4. Realizar Compra de um Criptoativo:");
            System.out.println("5. Gerar Relatório do Usuário");
            System.out.println("6. Exibir Carteira:");
            System.out.println("7. Sair: ");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        // Registro de novo usuário
                        try {
                            System.out.print("Digite o nome: ");
                            String nome = scanner.nextLine();
                            System.out.print("Digite o email: ");
                            String email = scanner.nextLine();
                            System.out.print("Digite a senha: ");
                            String senha = scanner.nextLine();

                            Usuario novoUsuario = new Usuario(String.valueOf(System.currentTimeMillis()), nome, email, senha);
                            plataforma.registrarUsuario(novoUsuario);
                            salvarUsuariosETransacoesEmArquivo(plataforma);
                            System.out.println("Usuário registrado com sucesso!");
                        } catch (Exception e) {
                            System.out.println("Erro ao registrar usuário: " + e.getMessage());
                        }
                        break;

                    case 2:
                        // Login do usuário
                        try {
                            System.out.print("Digite o email: ");
                            String loginEmail = scanner.nextLine();
                            System.out.print("Digite a senha: ");
                            String loginSenha = scanner.nextLine();

                            if (plataforma.autenticarUsuario(loginEmail, loginSenha)) {
                                usuarioLogado = plataforma.buscarUsuarioPorEmail(loginEmail);
                                usuarioLogado.login();
                                System.out.println("Login realizado com sucesso!");
                            } else {
                                System.out.println("Email ou senha inválidos.");
                            }
                        } catch (Exception e) {
                            System.out.println("Erro ao realizar login: " + e.getMessage());
                        }
                        break;


                    case 3:
                        if (usuarioLogado == null) {
                            System.out.println("Faça login primeiro.");
                            break;
                        }
                        try {
                            // Realizar uma transação venda
                            System.out.print("Digite o nome do ativo: ");
                            String ativoNome = scanner.nextLine();
                            System.out.print("Digite o valor da transação: ");
                            double valorTransacao = scanner.nextDouble();
                            scanner.nextLine();
                            double idVenda = System.currentTimeMillis();


                            Ativo ativo1 = new Ativo(String.valueOf(System.currentTimeMillis()), ativoNome, valorTransacao, 0.0);
                            Venda venda = new Venda(String.valueOf(System.currentTimeMillis()), valorTransacao, ativo1, 0.015, idVenda);
                            System.out.println(venda.getexecutarTransacao());

                            usuarioLogado.adicionarTransacao(venda);
                            ativo1.atualizarValor(valorTransacao);
                        } catch (Exception e) {
                            System.out.println("Erro ao realizar venda: " + e.getMessage());
                        }
                        break;

                    case 4:
                        if (usuarioLogado == null) {
                            System.out.println("Faça login primeiro.");
                            break;
                        }
                        try {
                            // Realizar uma transação compra
                            System.out.print("Digite o nome do ativo: ");
                            String ativoCompra = scanner.nextLine();
                            System.out.print("Digite o valor da transação: ");
                            double valorCompra = scanner.nextDouble();
                            scanner.nextLine();
                            double idCompra = System.currentTimeMillis();

                            Ativo ativo2 = new Ativo(String.valueOf(System.currentTimeMillis()), ativoCompra, valorCompra, 0.0);
                            Compra compra = new Compra(String.valueOf(System.currentTimeMillis()), valorCompra, ativo2, 0.015);
                            System.out.println(compra.getexecutarTransacao());
                            usuarioLogado.adicionarTransacao(compra);
                            ativo2.atualizarValor(valorCompra);

                            Carteira carteiraUsuario = (Carteira) plataforma.buscarCarteiraPorUsuario(usuarioLogado);
                            if (carteiraUsuario != null) {
                                carteiraUsuario.adicionarQuantidade(valorCompra, ativo2);
                            } else {
                                System.out.println("Erro: Carteira não encontrada");
                            }
                        } catch (Exception e) {
                            System.out.println("Erro ao realizar compra: " + e.getMessage());
                        }
                        break;

                    case 5:
                        if (usuarioLogado == null) {
                            System.out.println("Faça login primeiro.");
                            break;
                        }
                        try {
                            // Gerar relatório do usuário logado
                            monitoramento.gerarRelatorioUsuario(usuarioLogado);
                        } catch (Exception e) {
                            System.out.println("Erro ao gerar relatório: " + e.getMessage());
                        }
                        break;

                    case 6:
                        if (usuarioLogado == null) {
                            System.out.println("Faça login primeiro.");
                            break;
                        }

                        Carteira carteiraUsuario= (Carteira) plataforma.buscarCarteiraPorUsuario(usuarioLogado);

                        if (carteiraUsuario== null) {
                            Carteira novaCarteira = new Carteira(String.valueOf(System.currentTimeMillis()), usuarioLogado, null, 0.0, new Date());
                            System.out.println("O usuário " + usuarioLogado.getNome() + " não possui uma carteira, uma nova carteira será criada.");
                            plataforma.adicionarCarteira(novaCarteira, usuarioLogado);
                            System.out.println("Carteira adicionada para o usuário " + usuarioLogado.getNome());
                            novaCarteira.getexibirInformacoes();
                        } else {
                            System.out.println("Carteira do usuário " + usuarioLogado.getNome());
                            carteiraUsuario.getexibirInformacoes();
                        }

                        break;
                    case 7:
                        // Sair do sistema
                        continuar = false;
                        salvarUsuariosETransacoesEmArquivo(plataforma);
                        System.out.println("Saindo da plataforma. Até logo!");
                        break;

                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número válido.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private static void salvarUsuariosETransacoesEmArquivo(PlataformaWeb plataforma) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("usuarios.txt"))) {
            for (Usuario usuario : plataforma.getUsuarios()) {
                // Informações do usuário
                bw.write("Informações do usuário: ");
                bw.newLine();
                bw.write("  - ID: " + usuario.getId());
                bw.newLine();
                bw.write("  - Nome: " + usuario.getNome());
                bw.newLine();
                bw.write("  - Email: " + usuario.getEmail());
                bw.newLine();

                //Informações sobre transações
                bw.write("Transações:");
                bw.newLine();
                if (usuario.getTransacoes().isEmpty()) {
                    bw.write("  - Nenhuma transação registrada");
                    bw.newLine();
                } else {
                    for (Transacao transacao : usuario.getTransacoes()) {
                        String tipoTransacao = transacao.getClass().getSimpleName();
                        String nomeAtivo = transacao.getAtivo().getNome();

                        double taxa = transacao.getTaxa();
                        double valorTransacao = transacao.getValor();
                        bw.write("  - Tipo: " + tipoTransacao + ", Ativo: " + nomeAtivo + ", Valor: " + valorTransacao + ", Taxa: " + taxa);
                        bw.newLine();
                    }
                }

                bw.newLine();
            }
            System.out.println("Dados de usuários e transações salvos em 'usuarios.txt'.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void carregarUsuariosDeArquivo(PlataformaWeb plataforma) {
        File arquivo = new File("usuarios.txt");
        if (!arquivo.exists()) {
            System.out.println("Arquivo 'usuarios.txt' não encontrado. Um novo arquivo será criado ao salvar os dados.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            String id = null, nome = null, email = null, senha = null;

            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("ID: ")) {
                    id = linha.substring(4).trim();
                } else if (linha.startsWith("Nome: ")) {
                    nome = linha.substring(6).trim();
                } else if (linha.startsWith("Email: ")) {
                    email = linha.substring(7).trim();
                } else if (linha.startsWith("Senha: ")) {
                    senha = linha.substring(7).trim();
                }


                if (id != null && nome != null && email != null && senha != null) {
                    Usuario usuario = new Usuario(id, nome, email, senha);
                    plataforma.registrarUsuario(usuario);



                    id = nome = email = senha = null;
                }
            }
            System.out.println("Dados dos usuários carregados do arquivo.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
