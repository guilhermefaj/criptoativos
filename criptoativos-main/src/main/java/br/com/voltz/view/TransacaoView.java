package br.com.voltz.view;

import br.com.voltz.dao.TransacaoDao;
import br.com.voltz.enumerations.TransacaoEnum;
import br.com.voltz.exception.entidadeNaoEncontrada;
import br.com.voltz.model.Compra;
import br.com.voltz.model.Transacao;
import br.com.voltz.model.Venda;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class TransacaoView {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        TransacaoDao dao;
        try{
            dao = new TransacaoDao();
            int escolha;
            do{
                System.out.println("Escolha uma opção: " +
                        "1.Cadastrar uma nova transação: " +
                        "2.Pesquisar Transação através do id: " +
                        "3.Listar Transações: " +
                        "4.Atualizar Transação através do id: " +
                        "5.Deletar uma Transação:  " +
                        "0. Sair");
                escolha = sc.nextInt();
                switch (escolha){
                    case 1:
                        cadastrar(sc, dao);
                        break;
                    case 2:
                        pesquisarTransacao(sc, dao);
                        break;
                    case 3:
                        listarTransacoes(dao);
                        break;
                    case 4:
                        atualizarTransacao(sc, dao);
                        break;
                    case 5:
                        removerTransacao(sc, dao);
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
    private static void cadastrar(Scanner sc, TransacaoDao dao){
        System.out.println("Digite o valor da Transação : ");
        double valor = sc.nextDouble();
        sc.nextLine();
        System.out.println("Digite o tipo de Transação (COMPRA/VENDA): ");
        String tipoString = sc.nextLine();
        TransacaoEnum tipo = TransacaoEnum.valueOf(tipoString.toUpperCase());
        System.out.println("Digite a data da Transação: (yyyy-MM-dd)");
        String dataString = sc.nextLine();
        Date data = Date.valueOf(dataString);

        String id = UUID.randomUUID().toString().substring(0,8);

        Transacao transacao;
        if (tipo == TransacaoEnum.COMPRA) {
            transacao =  new Compra(id, valor, data);
        } else {
            transacao= new Venda(id, valor, data);
        }
        try {
            dao.inserirTransacoes(transacao);
            System.out.println("Transação cadastrada com sucesso");
            dao.fecharConexao();

        }catch (SQLException e){
            System.err.println("Erro ao cadastrar uma nova Transação: "+ e.getMessage());
        }
    }

    private static void pesquisarTransacao(Scanner sc, TransacaoDao dao) {
        System.out.println("Digite o id da Transação que deseja buscar: ");
        String id = sc.next() + sc.nextLine();
        try {
            Transacao transacao = dao.buscarTransacaoId(id);
            System.out.println("Id Transação: " + transacao.getIdTransacao() + "\nValor: " +
                    transacao.getValor()  + "\nTipo: " + transacao.getTipo() + "\nData da transação: " + transacao.getData() + "\nId Usuário: "+ transacao.getUsuario().getId() +"\nId Ativo: " + transacao.getAtivo().getIdAtivo() );
        }catch (SQLException | entidadeNaoEncontrada e){
            System.err.println("Erro ao buscar a transação: "+ e.getMessage());
        }

    }
    public static void listarTransacoes (TransacaoDao dao) {
        try{
            List<Transacao> transacoes = dao.listarTransacao();
            System.out.println("Lista de Transações: ");
            for(Transacao transacao: transacoes) {
                System.out.println("Id Transação: " + transacao.getIdTransacao() + "\nValor: " +
                        transacao.getValor()  + "\nTipo: " + transacao.getTipo() + "\nData da transação: " + transacao.getData() +
                        "\nId Usuário: "+ transacao.getUsuario().getId() +
                        "\nId Ativo: " + transacao.getAtivo().getIdAtivo() );
            }
        }catch (SQLException e){
            System.err.println("Erro ao listar as Transações: "+ e.getMessage());
        }
    }
    public static void atualizarTransacao (Scanner sc, TransacaoDao dao) {
        System.out.println("Digite o id da transação a ser atualizado:");
        String id = sc.next() + sc.nextLine();
        try{
            Transacao transacao  = dao.buscarTransacaoId(id);
            System.out.println("Digite o valor atualizado da transação: ");
            double valor = sc.nextDouble();
            sc.nextLine();
            System.out.println("Digite o tipo atualizado da transação: (COMPRA/VENDA)  ");
            String tipoString = sc.nextLine();
            TransacaoEnum tipo = TransacaoEnum.valueOf(tipoString.toUpperCase());
            System.out.println("Digite a data atualizada da transação: ");
            String dataString = sc.nextLine();
            Date data = Date.valueOf(dataString);
            transacao.setValor(valor);
            transacao.setTipo(tipo);
            transacao.setData(data);
            dao.atualizarTransacao(transacao, id);
            System.out.println("Transação atualizada com sucesso!");
            System.out.println("Id Transação: " + transacao.getIdTransacao() + "\nValor: " +
                    transacao.getValor()  + "\nTipo: " + transacao.getTipo() + "\nData da transação: " + transacao.getData() +
                    "\nId Usuário: "+ transacao.getUsuario().getId() +
                    "\nId Ativo: " + transacao.getAtivo().getIdAtivo() );

        }catch (SQLException | entidadeNaoEncontrada e){
            System.err.println("Erro ao atualizar transação: "+ e.getMessage());
        }

    }
    public static void removerTransacao(Scanner sc, TransacaoDao dao) {
        System.out.println("Digite o id da transação a ser deletado: ");
        String id = sc.next() + sc.nextLine();
        try{
           Transacao transacao = dao.buscarTransacaoId(id);
            System.out.println("Id Transação: " + transacao.getIdTransacao() + "\nValor: " +
                    transacao.getValor()  + "\nTipo: " + transacao.getTipo() + "\nData da transação: " + transacao.getData() +
                    "\nId Usuário: "+ transacao.getUsuario().getId() +
                    "\nId Ativo: " + transacao.getAtivo().getIdAtivo() );
            System.out.print("Tem certeza que deseja deletar esta transação ? (s/n): ");
            String confirmacao = sc.next().trim().toLowerCase();
            if (confirmacao.equals("s")) {
                dao.deleteTransacao(id);
                System.out.println("Transação deletada com sucesso!");
            } else {
                System.out.println("Ação cancelada. A transação não foi deletado.");
            }
        }catch (SQLException | entidadeNaoEncontrada e){
            System.err.println("Erro ao deletar a transação: "+ e.getMessage());
        }
    }

}
