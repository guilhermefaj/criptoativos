package br.com.voltz.view;
import br.com.voltz.dao.AtivosDao;
import br.com.voltz.enumerations.AtivosEnum;
import br.com.voltz.exception.entidadeNaoEncontrada;
import br.com.voltz.model.Ativo;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AtivoView {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        AtivosDao dao;
        try{
            dao = new AtivosDao();
            int escolha;
            do{
                System.out.println("Escolha uma opção: " +
                        "1.Cadastrar um novo ativo: " +
                        "2.Pesquisar Ativo através do id: " +
                        "3.Listar Ativos" +
                        "4.Atualizar Ativo através do id: " +
                        "5.Deletar um Ativo " +
                        "0. Sair");
                escolha = sc.nextInt();
                switch (escolha){
                    case 1:
                        cadastrar(sc, dao);
                        break;
                    case 2:
                        pesquisarAtivo(sc, dao);
                        break;
                    case 3:
                        listarAtivos(dao);
                        break;
                    case 4:
                        atualizarAtivo(sc, dao);
                        break;
                    case 5:
                        removerAtivo(sc, dao);
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
        }

    }
    private static void cadastrar(Scanner sc, AtivosDao dao){
        System.out.println("Digite o nome do Ativo: ");
        String nomeStr = sc.next() + sc.nextLine();
        AtivosEnum nome = AtivosEnum.valueOf(nomeStr);
        System.out.println("Digite o valor atual do Ativo: ");
        double valorAtual = sc.nextDouble();
        System.out.println("Digite a volatilidade do Ativo: ");
        double volatilidade = sc.nextDouble();
        Ativo ativo = new Ativo(null, nome, valorAtual, volatilidade);
        try {
            dao.cadastrarAtivo(ativo);
            System.out.println("Ativo cadastrado com sucesso");
            dao.fecharConexao();

        }catch (SQLException e){
            System.err.println("Erro ao cadastrar Ativo: "+ e.getMessage());
        }
    }

    private static void pesquisarAtivo(Scanner sc, AtivosDao dao) {
        System.out.println("Digite o ID do Ativo que deseja buscar: ");
        String id = sc.next() + sc.nextLine();
        try {
            Ativo ativo = dao.buscarAtivoId(id);
            System.out.println("Id Ativo:" + ativo.getId()+ "\n Nome: " +
                    ativo.getNome().name()+ "\n Valor Atual: " + ativo.getValorAtual() + "\nVolatilidade: " + ativo.getVolatilidade() );
        }catch (SQLException | entidadeNaoEncontrada e){
            System.err.println("Erro ao buscar ativo: "+ e.getMessage());
        }

    }
    public static void listarAtivos(AtivosDao dao) {
        try{
            List<Ativo> ativos = dao.listarAtivos();
            System.out.println("Lista de ativos: ");
            for(Ativo ativo: ativos) {
                System.out.println("ID Ativo:" + ativo.getId() + "\nNome: " +
                        ativo.getNome().name()+  "\nValor Atual: " + ativo.getValorAtual() + "\nVolatilidade: " + ativo.getVolatilidade() );
            }
        }catch (SQLException e){
            System.err.println("Erro ao listar ativos: "+ e.getMessage());
        }
    }
    public static void atualizarAtivo(Scanner sc, AtivosDao dao) {
        System.out.println("Digite o ID do ativo a ser atualizado:");
        String id = sc.next() + sc.nextLine();
        try{
            Ativo ativo  = dao.buscarAtivoId(id);
            System.out.println("Digite o novo nome do ativo: ");
            String novoNomeStr = sc.next() + sc.nextLine();
            AtivosEnum nome = AtivosEnum.valueOf(novoNomeStr);
            System.out.println("Digite o Valor atual atualizado ");
            double novoValorAtual = sc.nextDouble();
            System.out.println("Digite aa volatilidade atualziada: ");
            double novaVolatilidade = sc.nextDouble();
            ativo.setNome(nome);
            ativo.setValorAtual(novoValorAtual);
            ativo.setVolatilidade(novaVolatilidade);
            dao.atualizarAtivos(ativo, id);
            System.out.println("Ativo atualizado com sucesso!");
            System.out.println("ID Ativo:" + ativo.getId() + "\nNome: " +
                    ativo.getNome().name()+  "\nValor Atual: " + ativo.getValorAtual() +
                    "\nVolatilidade: " + ativo.getVolatilidade() );
        }catch (SQLException | entidadeNaoEncontrada e){
            System.err.println("Erro ao atualizar ativo: "+ e.getMessage());
        }

    }
    public static void removerAtivo(Scanner sc, AtivosDao dao) {
        System.out.println("Digite o ID do ativo a ser deletado: ");
        String id = sc.next() + sc.nextLine();
        try{
         Ativo ativo= dao.buscarAtivoId(id);
            System.out.println("ID Ativo:" + ativo.getId() + "\nNome: " +
                    ativo.getNome().name()+  "\nValor Atual: " + ativo.getValorAtual() +
                    "\nVolatilidade: " + ativo.getVolatilidade() );
            System.out.print("Tem certeza que deseja deletar este ativo? (s/n): ");
            String confirmacao = sc.next().trim().toLowerCase();
            if (confirmacao.equals("s")) {
               dao.deleteAtivo(id);
                System.out.println("Ativo deletado com sucesso!");
            } else {
                System.out.println("Ação cancelada. O ativo não foi deletado.");
            }
        }catch (SQLException | entidadeNaoEncontrada e){
            System.err.println("Erro ao deletar ativo: "+ e.getMessage());
        }
    }

}
