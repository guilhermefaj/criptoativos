package br.com.voltz.view;

import br.com.voltz.dao.MonitoramentoDao;
import br.com.voltz.exception.entidadeNaoEncontrada;
import br.com.voltz.service.Monitoramento;
import br.com.voltz.service.PlataformaWeb;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MonitoramentoView {
        public static void main(String[] args){
            Scanner sc = new Scanner(System.in);
            MonitoramentoDao dao;
            try{
                dao = new MonitoramentoDao();
                int escolha;
                do{
                    System.out.println("\n == Menu Monitoramento: == " +
                            "1.Cadastrar um novo monitoramento: " +
                            "2.Pesquisar  monitoramento através do ID: " +
                            "3.Listar Monitoramentos" +
                            "4.Atualizar Monitoramento através do ID: " +
                            "5.Deletar Monitoramento " +
                            "0. Sair" +
                            "Escolha uma opção: ");
                    escolha = sc.nextInt();
                    sc.nextLine();

                    switch (escolha){
                        case 1:
                            cadastrar(sc, dao);
                            break;
                        case 2:
                            pesquisarMonitoramento(sc, dao);
                            break;
                        case 3:
                            listarMonitoramento(dao);
                            break;
                        case 4:
                            atualizarMonitoramento(sc, dao);
                            break;
                        case 5:
                            removerMonitoramento(sc, dao);
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
        private static void cadastrar(Scanner sc, MonitoramentoDao dao){
            try {
                System.out.println("Digite a data do monitoramento: (yyyy-MM-dd) ");
                String dataStr = sc.nextLine();
                Date data = new SimpleDateFormat("yyyy-MM-dd").parse(dataStr);
                System.out.println("Digite o ID da plataforma:  ");
                String idPlataforma = sc.nextLine();
                PlataformaWeb plataforma = new PlataformaWeb(idPlataforma);

                Monitoramento monitoramento = new Monitoramento(null, data, plataforma);

                    dao.cadastrarMonitoramento(monitoramento);
                    System.out.println("Monitoramento cadastrado com sucesso");
                    dao.fecharConexao();

            }catch (SQLException e){
                System.err.println("Erro ao cadastrar Monitoramento: "+ e.getMessage());
            }catch(ParseException e){
                System.err.println("Formato de data inválido. Use dd/MM/yyyy");
            }
        }

        private static void pesquisarMonitoramento(Scanner sc, MonitoramentoDao dao) {
            System.out.println("Digite o ID do monitoramento que deseja buscar: ");
            String id = sc.next() + sc.nextLine();
            try {
               Monitoramento monitoramento = dao.buscarMonitoramentoId(id);
                System.out.println("Monitoramento encontrado:");
                System.out.println("ID: " + monitoramento.getIdMonitoramento());
                System.out.println("Data: " + monitoramento.getData());
                System.out.println("Plataforma ID: " + monitoramento.getPlataformaWeb().getIdPlataforma());
            }catch (SQLException | entidadeNaoEncontrada e){
                System.err.println("Erro ao buscar Monitoramento: "+ e.getMessage());
            }

        }
        public static void listarMonitoramento(MonitoramentoDao dao) {
            try{
                List<Monitoramento> lista = dao.listarMonitoramentos();
                if (lista.isEmpty()) {
                    System.out.println("Nenhum monitoramento encontrado.");
                } else {
                    System.out.println("Lista de Monitoramentos: ");
                    for (Monitoramento monitoramento : lista) {
                        System.out.println("ID: " + monitoramento.getIdMonitoramento());
                        System.out.println("Data: " + monitoramento.getData());
                        System.out.println("Plataforma ID: " + monitoramento.getPlataformaWeb().getIdPlataforma());
                    }
                }
            }catch (SQLException | entidadeNaoEncontrada e){
                System.err.println("Erro ao listar monitoramentos: "+ e.getMessage());
            }
        }
        public static void atualizarMonitoramento(Scanner sc, MonitoramentoDao dao) {
            System.out.println("Digite o ID do Monitoramento que deseja atualizar:");
            String id = sc.next() + sc.nextLine();
            try{
               Monitoramento monitoramento = dao.buscarMonitoramentoId(id);
                System.out.println("Digite a data atualizada:  (yyyy-mm-dd)");
                String dataStr = sc.nextLine();
                Date novaData = new SimpleDateFormat("yyyy-MM-dd").parse(dataStr);

                System.out.println("Digite o ID da plataforma atualizado: ");
                String idPlataforma = sc.nextLine();
                PlataformaWeb plataformaWeb= new PlataformaWeb(idPlataforma);

                monitoramento.setData(novaData);
                monitoramento.setPlataformaWeb(plataformaWeb);
                dao.atualizarDadosMonitoramento(monitoramento, id);
                System.out.println("Monitoramento atualizado com sucesso!");
                System.out.println("ID: " + monitoramento.getIdMonitoramento());
                System.out.println("Data: " + monitoramento.getData());
                System.out.println("Plataforma ID: " + monitoramento.getPlataformaWeb().getIdPlataforma());
            }catch (SQLException | entidadeNaoEncontrada e){
                System.err.println("Erro ao atualizar o Monitoramento: "+ e.getMessage());
            }catch(ParseException e) {
                System.err.println("Formato de data inválido. Use dd/MM/yyyy");
            }

        }
        public static void removerMonitoramento(Scanner sc, MonitoramentoDao dao) {
            System.out.println("Digite o ID do Monitoramento que deseja deletar: ");
            String id = sc.next() + sc.nextLine();
            try{
                Monitoramento monitoramento = dao.buscarMonitoramentoId(id);
                System.out.println("Monitoramento encontrado:");
                System.out.println("ID: " + monitoramento.getIdMonitoramento());
                System.out.println("Data: " + monitoramento.getData());
                System.out.println("Plataforma ID: " + monitoramento.getPlataformaWeb().getIdPlataforma());

                System.out.print("Tem certeza que deseja deletar este ativo? (s/n): ");
                String confirmacao = sc.next().trim().toLowerCase();
                if (confirmacao.equals("s")) {
                    dao.deleteMonitoramento(id);
                    System.out.println("Monitoramento deletado com sucesso!");
                } else {
                    System.out.println("Ação cancelada. O Monitoramento não foi deletado.");
                }
            }catch (SQLException | entidadeNaoEncontrada e){
                System.err.println("Erro ao deletar o monitoramento: "+ e.getMessage());
            }
        }

    }


