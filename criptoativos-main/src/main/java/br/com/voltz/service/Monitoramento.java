package br.com.voltz.service;

import br.com.voltz.model.Compra;
import br.com.voltz.model.Transacao;
import br.com.voltz.model.Usuario;
import br.com.voltz.model.Venda;
import br.com.voltz.model.Compra;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Monitoramento {
    private String idMonitoramento;

    public Monitoramento(String idMonitoramento) {
        this.idMonitoramento = idMonitoramento;
    }

    public void gerarRelatorioDiario() {
        System.out.println("Relatório diário gerado.");
    }

    public void gerarRelatorioUsuario(Usuario usuario) {
        System.out.println("\n--- Relatório do Usuário: " + usuario.getNome() + " ---");

        Map<String, List<Transacao>> transacoesPorAtivo = new HashMap<>();

        for (Transacao transacao : usuario.getTransacoes()) {
            String nomeAtivo = transacao.getAtivo().getNome();
            transacoesPorAtivo.computeIfAbsent(nomeAtivo, k -> new ArrayList<>()).add(transacao);
        }

        for (String nomeAtivo : transacoesPorAtivo.keySet()) {
            List<Transacao> transacoes = transacoesPorAtivo.get(nomeAtivo);
            System.out.println("Ativo: " + nomeAtivo);
            double saldoTotalAtivo = 0.0;


            for (Transacao transacao: transacoes) {
                if(transacao instanceof Venda venda){
                    System.out.println("ID: " + venda.getIdvenda() +
                        ", Valor: R$ " + venda.getValor() +
                        ", Data: " + venda.getData());
                saldoTotalAtivo -= venda.getValor();
                }else if (transacao instanceof Compra compra){
                    System.out.println( "ID: " + compra.getIdCompra()+
                            ", Valor: R$ " + compra.getValor() +
                            ", Data: " + compra.getData());
                    saldoTotalAtivo += compra.getValor();
                }

                }
            System.out.println("Saldo total de " + nomeAtivo + ": R$ " + saldoTotalAtivo);
        }
    }

    public void enviarAlerta() {
        System.out.println("Alerta enviado aos usuários.");
    }
}
