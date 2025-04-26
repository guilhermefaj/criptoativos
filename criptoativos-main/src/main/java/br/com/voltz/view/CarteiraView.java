package br.com.voltz.view;


import br.com.voltz.dao.CarteiraDao;
import br.com.voltz.exception.entidadeNaoEncontrada;
import br.com.voltz.model.Ativo;
import br.com.voltz.model.Carteira;
import br.com.voltz.model.Usuario;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CarteiraView {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        CarteiraDao dao;
        try{
            dao = new CarteiraDao();
            int escolha;
            do{
                System.out.println("\n=== Menu Carteira ===");
                System.out.println(
                        "1.Cadastrar uma nova Carteira: " +
                        "2.Pesquisar Carteira através do id: " +
                        "3.Listar Carteiras" +
                        "4.Atualizar Carteira através do id: " +
                        "5.Deletar uma carteira " +
                        "0. Sair" +
                        "Escolha uma opção: " );
                escolha = sc.nextInt();
                sc.nextLine();

                switch (escolha){
                    case 1:
                        cadastrar(sc, dao);
                        break;
                    case 2:
                        pesquisarCarteira(sc, dao);
                        break;
                    case 3:
                        listarCarteiras(dao);
                        break;
                    case 4:
                        atualizarCarteiras(sc, dao);
                        break;
                    case 5:
                        removerCarteiras(sc, dao);
                        break;
                    case 0:
                        System.out.println("Finalizando o sistema ....");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            }while (escolha != 0);
            dao.fecharConexao();
        }catch (SQLException e){
            System.err.println("Erro ao conectar ao banco de dados "+ e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
    private static void cadastrar(Scanner sc, CarteiraDao dao) throws ParseException {
        try {
            System.out.println("Digite a quantidade de ativos presente na carteira: ");
            double quantidade = sc.nextDouble();
            sc.nextLine();

            System.out.println("Digite a data de Aquisição da carteira: (dd/MM/yyyy) ");
            String dataStr = sc.next() + sc.nextLine();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date dataUtil = formato.parse(dataStr);
            java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());

            System.out.println("Digite o ID do usuário: ");
            String idUsuario = sc.nextLine();
            System.out.println("Digite o ID do ativo: ");
            String idAtivo = sc.nextLine();

            Usuario usuario = new Usuario(idUsuario, "", "", "");
            Ativo ativo = new Ativo(idAtivo, "", 0.0);

            Carteira carteira  = new Carteira(null, usuario, ativo,quantidade,dataSql);

            dao.cadastrarNovaCarteira(carteira);
            System.out.println("Carteira cadastrada com sucesso");
            dao.fecharConexao();

        }catch (SQLException e){
            System.err.println("Erro ao cadastrar a Carteira: "+ e.getMessage());
        }catch (ParseException e) {
            System.err.println("Formato de data inválido. Use dd/MM/yyyy");
        }
    }

    private static void pesquisarCarteira (Scanner sc, CarteiraDao dao) {
        System.out.println("Digite o ID da carteira que deseja buscar: ");
        String id = sc.next() + sc.nextLine();
        try {
           Carteira carteira = dao.buscarCarteiraPorId(id);
            System.out.println("ID Carteira:" + carteira.getIdCarteira() + "\nID Usuário: " +
                   carteira.getUsuario().getId() + "\nID Ativo: " + carteira.getAtivo().getIdAtivo()
                    + "\nQuantidade: " + carteira.getQuantidade() + "\nData de Aquisição: " + carteira.getData() );
            dao.fecharConexao();
        }catch (SQLException | entidadeNaoEncontrada e){
            System.err.println("Erro ao buscar carteira: "+ e.getMessage());
        }

    }
    private static void listarCarteiras(CarteiraDao dao) {
        try {
            List<Carteira> lista = dao.listarCarteiras();
            if (lista.isEmpty()) {
                System.out.println("Nenhuma carteira cadastrada.");
            } else {
                for (Carteira carteira : lista) {
                    System.out.println("ID Carteira:" + carteira.getIdCarteira() + "\nID Usuário: " +
                            carteira.getUsuario().getId() + "\nID Ativo: " + carteira.getAtivo().getIdAtivo()
                            + "\nQuantidade: " + carteira.getQuantidade() + "\nData de Aquisição: " + carteira.getData() );
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar Carteiras: " + e.getMessage());
        }
    }
    public static void atualizarCarteiras(Scanner sc, CarteiraDao dao) {
        System.out.println("Digite o ID da carteira a ser atualizado:");
        String id = sc.next() + sc.nextLine();
        try{
            Carteira carteira = dao.buscarCarteiraPorId(id);
            System.out.println("Digite a quantidade atualizada de ativos presentes na carteira ");
            double quantidade = sc.nextDouble();
            sc.nextLine();
            System.out.println("Digite a data de aquisição da carteira atualizada: (dd/MM/yyyy)  ");
            String dataStr = sc.next() + sc.nextLine();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date dataUtil = formato.parse(dataStr);
            java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());
            carteira.setQuantidade(quantidade);
            carteira.setData(dataSql);
            dao.atualizarDadosCarteira(carteira, id);
            System.out.println("Carteira atualizada com sucesso!");
            System.out.println("ID Carteira:" + carteira.getIdCarteira() + "\nID Usuário: " +
                    carteira.getUsuario().getId() + "\nID Ativo: " + carteira.getAtivo().getIdAtivo()
                    + "\nQuantidade: " + carteira.getQuantidade() + "\nData de Aquisição: " + carteira.getData() );

        }catch (SQLException | entidadeNaoEncontrada e){
            System.err.println("Erro ao atualizar carteira: "+ e.getMessage());
        }catch (ParseException e) {
            System.err.println("Formato de data inválido. Use dd/MM/yyyy");
        }

    }
    public static void removerCarteiras(Scanner sc, CarteiraDao dao) {
        System.out.println("Digite o ID da carteira a ser deletado: ");
        String id = sc.next() + sc.nextLine();
        try{
            Carteira carteira = dao.buscarCarteiraPorId(id);
            System.out.println("ID Carteira:" + carteira.getIdCarteira() + "\nID Usuário: " +
                    carteira.getUsuario().getId() + "\nID Ativo: " + carteira.getAtivo().getIdAtivo()
                    + "\nQuantidade: " + carteira.getQuantidade() + "\nData de Aquisição: " + carteira.getData() );
            System.out.print("Tem certeza que deseja deletar esta carteira? (s/n): ");
            String confirmacao = sc.next().trim().toLowerCase();
            if (confirmacao.equals("s")) {
                dao.deleteCarteira(id);
                System.out.println("Carteira deletada com sucesso!");
            } else {
                System.out.println("Ação cancelada. A carteira não foi deletada.");
            }
        }catch (SQLException | entidadeNaoEncontrada e){
            System.err.println("Erro ao deletar Carteira: "+ e.getMessage());
        }
    }
}
