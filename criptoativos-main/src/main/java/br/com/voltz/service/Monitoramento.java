package br.com.voltz.service;

import br.com.voltz.model.Compra;
import br.com.voltz.model.Transacao;
import br.com.voltz.model.Usuario;
import br.com.voltz.model.Venda;


import java.util.*;

public class Monitoramento {
    private String idMonitoramento;
    private Date data;
    private PlataformaWeb plataformaWeb;

    public Monitoramento() {
    }

    public Monitoramento(String idMonitoramento) {
        this.idMonitoramento = idMonitoramento;
    }

    public Monitoramento(String idMonitoramento, Date data, PlataformaWeb plataformaWeb) {
        this.idMonitoramento = idMonitoramento;
        this.data = data;
        this.plataformaWeb = plataformaWeb;
    }

    public String getIdMonitoramento() {
        return idMonitoramento;
    }

    public void setIdMonitoramento(String idMonitoramento) {
        this.idMonitoramento = idMonitoramento;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public PlataformaWeb getPlataformaWeb() {
        return plataformaWeb;
    }

    public void setPlataformaWeb(PlataformaWeb plataformaWeb) {
        this.plataformaWeb = plataformaWeb;
    }

    public void gerarRelatorioDiario() {
        System.out.println("Relatório diário gerado.");
    }

    public void gerarRelatorioUsuario(Usuario usuario) {
        System.out.println("\n--- Relatório do Usuário: " + usuario.getNome() + " ---");

        Map<String, List<Transacao>> transacoesPorAtivo = new HashMap<>();

        for (Transacao transacao : usuario.getTransacoes()) {
            String nomeAtivo = transacao.getAtivo().getNome().name();
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
