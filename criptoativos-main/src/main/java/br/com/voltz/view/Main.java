package br.com.voltz.view;

import br.com.voltz.dao.UsuarioDao;
import br.com.voltz.exception.entidadeNaoEncontrada;
import br.com.voltz.model.*;
import br.com.voltz.service.Monitoramento;
import br.com.voltz.service.PlataformaWeb;
import br.com.voltz.service.Seguranca;
import br.com.voltz.model.Carteira;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    private static ArrayList<Usuario> usuariosTemporarios = new ArrayList<>();
    private static HashMap<String, Carteira> mapaCarteiras = new HashMap<>();
    private static ArrayList<Ativo> ativos = new ArrayList<>();
    private static HashMap<String, List<Transacao>> mapaTransacoes = new HashMap<>();
    private static PlataformaWeb plataforma;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Seguranca seguranca = null;
        Monitoramento monitoramento = null;
        Usuario usuarioLogado = null;
        UsuarioDao dao = null;

        try {
            plataforma = new PlataformaWeb("Plataforma Cripto");
            seguranca = new Seguranca("AES-256");
            monitoramento = new Monitoramento("monitor001");

            System.out.println("Iniciando carregamento de usuários...");
            carregarUsuariosDeArquivo(plataforma);

            System.out.println("\nIniciando carregamento de ativos...");
            carregarAtivosDeArquivo();

            System.out.println("\nAtivos disponíveis no sistema:");
            for (Ativo ativo : ativos) {
                System.out.println("- " + ativo.getNome() + " (ID: " + ativo.getId() + ") Valor: " + ativo.getValorAtual());
            }

        } catch (Exception e) {
            System.out.println("Erro ao inicializar os serviços: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        boolean continuar = true;

        while (continuar) {
            System.out.println("\n--- Bem-vindo à Plataforma Cripto ---");
            System.out.println("1. Registrar novo usuário");
            System.out.println("2. Login");
            System.out.println("3. Buscar usuário através do e-mail:");
            System.out.println("4. Listar um usuário através do e-mail");
            System.out.println("5. Atualizar dados do usuário: ");
            System.out.println("6. Deletar usuário: ");
            System.out.println("7. Realizar Venda de um Criptoativo:");
            System.out.println("8. Realizar Compra de um Criptoativo:");
            System.out.println("9. Gerar Relatório do Usuário");
            System.out.println("10. Exibir Carteira:");
            System.out.println("0. Sair: ");
            System.out.print("Escolha uma opção: ");

            try {
                dao = new UsuarioDao();
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                       cadastrarUsuario(scanner,dao);
                       break;

                    case 2:
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
                        buscarUsuarioPorEmail(scanner,dao);
                        break;
                    case 4:
                        listarUsuarioporEmail(scanner,dao);
                        break;
                    case 5:
                        atualizarUsuario(scanner,dao);
                        break;
                    case 6:
                        deletarUsuario(scanner,dao);
                        break;
                    case 7:
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

                            Ativo ativoExistente = buscarAtivoPorNome(ativoNome);
                            if (ativoExistente == null) {
                                System.out.println("Ativo não encontrado");
                                return;
                            }

                            Venda venda = new Venda(String.valueOf(System.currentTimeMillis()), valorTransacao, ativoExistente, 0.015, idVenda);
                            System.out.println(venda.getexecutarTransacao());

                            usuarioLogado.adicionarTransacao(venda);
                            ativoExistente.atualizarValor(valorTransacao);

                            if (!mapaTransacoes.containsKey(usuarioLogado.getId())) {
                                mapaTransacoes.put(usuarioLogado.getId(), new ArrayList<>());
                            }
                            mapaTransacoes.get(usuarioLogado.getId()).add(venda);
                        } catch (Exception e) {
                            System.out.println("Erro ao realizar venda: " + e.getMessage());
                        }
                        break;

                    case 8:
                        if (usuarioLogado == null) {
                            System.out.println("Faça login primeiro.");
                            break;
                        }
                        try {
                            Carteira carteiraUsuario = (Carteira) plataforma.buscarCarteiraPorUsuario(usuarioLogado);
                            if (carteiraUsuario == null) {
                                carteiraUsuario = new Carteira(String.valueOf(System.currentTimeMillis()),
                                        usuarioLogado, null, 0.0, new Date());
                                plataforma.adicionarCarteira(carteiraUsuario, usuarioLogado);
                                System.out.println("Nova carteira criada para o usuário.");
                            }

                            System.out.print("Digite o nome do ativo: ");
                            String ativoCompra = scanner.nextLine();
                            System.out.print("Digite o valor da transação: ");
                            double valorCompra = scanner.nextDouble();
                            scanner.nextLine();

                            Ativo ativoExistente = buscarAtivoPorNome(ativoCompra);
                            if (ativoExistente == null) {
                                System.out.println("Ativo não encontrado");
                                break;
                            }

                            Compra compra = new Compra(String.valueOf(System.currentTimeMillis()),
                                    valorCompra, ativoExistente, 0.015);
                            System.out.println(compra.getexecutarTransacao());
                            usuarioLogado.adicionarTransacao(compra);
                            ativoExistente.atualizarValor(valorCompra);

                            carteiraUsuario.adicionarQuantidade(valorCompra, ativoExistente);
                            System.out.println("Compra realizada com sucesso!");

                            if (!mapaTransacoes.containsKey(usuarioLogado.getId())) {
                                mapaTransacoes.put(usuarioLogado.getId(), new ArrayList<>());
                            }
                            mapaTransacoes.get(usuarioLogado.getId()).add(compra);
                        } catch (Exception e) {
                            System.out.println("Erro ao realizar compra: " + e.getMessage());
                        }
                        break;

                    case 9:
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

                    case 10:
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
                    case 0:
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
            boolean lendoUsuario = false;

            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("Informações do usuário:")) {
                    lendoUsuario = true;
                    continue;
                }

                if (lendoUsuario) {
                    if (linha.contains("ID:")) {
                        id = linha.substring(linha.indexOf("ID:") + 4).trim();
                    } else if (linha.contains("Nome:")) {
                        nome = linha.substring(linha.indexOf("Nome:") + 6).trim();
                    } else if (linha.contains("Email:")) {
                        email = linha.substring(linha.indexOf("Email:") + 7).trim();
                    } else if (linha.contains("Senha:")) {
                        senha = linha.substring(linha.indexOf("Senha:") + 7).trim();
                    }

                    if (id != null && nome != null && email != null && senha != null) {
                        Usuario usuario = new Usuario(id, nome, email, senha);
                        usuariosTemporarios.add(usuario);

                        Carteira carteira = new Carteira(String.valueOf(System.currentTimeMillis()),
                                usuario, null, 0.0, new Date());
                        mapaCarteiras.put(usuario.getId(), carteira);

                        plataforma.registrarUsuario(usuario);

                        // Resetar variáveis para próximo usuário
                        id = nome = email = senha = null;
                        lendoUsuario = false;
                    }
                }
            }
            System.out.println("Dados dos usuários carregados do arquivo.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void carregarAtivosDeArquivo() {
        File arquivo = new File("ativos.txt");
        if (!arquivo.exists()) {
            System.out.println("Arquivo 'ativos.txt' não encontrado.");
            return;
        }

        ativos.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(", ");
                if (partes.length == 3) {
                    String id = partes[0].substring(partes[0].indexOf(":") + 1).trim();
                    String nome = partes[1].substring(partes[1].indexOf(":") + 1).trim();
                    double valor = Double.parseDouble(partes[2].substring(partes[2].indexOf(":") + 1).trim());

                    Ativo ativo = new Ativo(id, nome, valor, 0.0);
                    ativos.add(ativo);
                    System.out.println("Ativo carregado: " + nome + " (ID: " + id + ") - Valor: " + valor);
                }
            }
            System.out.println("\nTotal de ativos carregados: " + ativos.size());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao carregar ativos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Ativo buscarAtivoPorNome(String nome) {
        for (Ativo ativo : ativos) {
            System.out.println("Comparando: '" + ativo.getNome() + "' com '" + nome + "'");
            if (ativo.getNome().trim().equalsIgnoreCase(nome.trim())) {
                return ativo;
            }
        }
        System.out.println("Nenhum ativo encontrado com o nome: " + nome);
        return null;
    }
    private static void cadastrarUsuario(Scanner scanner, UsuarioDao dao){
            System.out.println("Digite o nome do usuário: ");
            String nome = scanner.next() + scanner.nextLine();
            System.out.println("Digite o email do usuário: ");
            String email = scanner.next() + scanner.nextLine();
            System.out.println("Digite o senha do usuário: ");
            String senha = scanner.next() + scanner.nextLine();
            Usuario usuario = new Usuario(null, nome, email, senha);
            plataforma.registrarUsuario(usuario);
            salvarUsuariosETransacoesEmArquivo(plataforma);
            System.out.println("Usuário cadastrado com sucesso!");
        try { 
            dao.cadastrarUsuario(usuario);
            dao.fecharConexao();
        }catch (SQLException e){
            System.err.println("Erro ao cadastrar usuario: "+ e.getMessage());
        }
        Carteira novaCarteira = new Carteira(String.valueOf(System.currentTimeMillis()), usuario, null, 0.0, new Date());
        plataforma.adicionarCarteira(novaCarteira, usuario);

        System.out.println("Carteira criada com sucesso para o usuário " + nome);

    }

    private static void buscarUsuarioPorEmail(Scanner sc, UsuarioDao dao) {
        System.out.println("Digite o email do usuário: ");
        String email = sc.next() + sc.nextLine();
        try {
            Usuario usuario = dao.buscarUsuarioPorEmail(email);
            System.out.println("Id Usuário:" + usuario.getId()+", "  + " Nome: " +
                    usuario.getNome() +", " + " Email: " + usuario.getEmail() + ", "+ "Senha: " + usuario.getSenha()+ ", " + "Saldo total: " + usuario.getSaldoTotal()  );
        }catch (SQLException | entidadeNaoEncontrada e){
            System.err.println("Erro ao buscar usuario: "+ e.getMessage());
        }

    }
    public static void listarUsuarioporEmail(Scanner sc, UsuarioDao dao) {
            System.out.println("Digite o email do usuário que gostaria de listar:");
            String email = sc.next() + sc.nextLine();
            try{
            List<Usuario> usuario = dao.listarDadosUsuario(email);
            for(Usuario u : usuario) {
                System.out.println("Id Usuário:"+ u.getId()+ ", " +"Nome:" + u.getNome() +", "+"Email:"
                        + u.getEmail() + ", "+ "Senha: " + u.getSenha() +", " + "Saldo total: "+ u.getSaldoTotal());

            }
            }catch (SQLException | entidadeNaoEncontrada e){
                System.err.println("Erro ao listar usuario: "+ e.getMessage());
            }
    }
    public static void atualizarUsuario(Scanner sc, UsuarioDao dao) {
        System.out.println("Digite o e-mail do usuário a ser atualizado:");
        String emailOriginal = sc.next() + sc.nextLine();
        try{
            Usuario usuario = dao.buscarUsuarioPorEmail(emailOriginal);
            System.out.println("Digite o novo nome do usuário: ");
            String novoNome = sc.next() + sc.nextLine();
            System.out.println("Digite o novo email do usuario: ");
            String novoEmail = sc.next() + sc.nextLine();
            System.out.println("Digite a nova senha do usuario: ");
            String novaSenha = sc.next() + sc.nextLine();
            usuario.setNome(novoNome);
            usuario.setEmail(novoEmail);
            usuario.setSenha(novaSenha);
            dao.atualizarDadosUsuario(usuario, emailOriginal);
            System.out.println("Usuario atualizado com sucesso!");
            System.out.println("Id Usuário:" + usuario.getId()+", "  + " Nome: " +
                    usuario.getNome() +", " + " Email: " + usuario.getEmail() + ", "
                    + "Senha: " + usuario.getSenha()+ ", " + "Saldo total: " + usuario.getSaldoTotal()  );
        }catch (SQLException | entidadeNaoEncontrada e){
            System.err.println("Erro ao atualizar usuario: "+ e.getMessage());
        }

    }
    public static void deletarUsuario(Scanner sc, UsuarioDao dao) {
        System.out.println("Digite o email do usuário a ser deletado: ");
        String email = sc.next() + sc.nextLine();
        try{
            Usuario usuario = dao.buscarUsuarioPorEmail(email);
            System.out.println("Id Usuário:" + usuario.getId()+", "  + " Nome: " +
                    usuario.getNome() +", " + " Email: " + usuario.getEmail() + ", "
                    + "Senha: " + usuario.getSenha()+ ", " + "Saldo total: " + usuario.getSaldoTotal()  );
            dao.deleteUsuario(email);
            System.out.println("Usuario deletado com sucesso!");
        }catch (SQLException | entidadeNaoEncontrada e){
            System.err.println("Erro ao deletar usuario: "+ e.getMessage());
        }
    }
}
