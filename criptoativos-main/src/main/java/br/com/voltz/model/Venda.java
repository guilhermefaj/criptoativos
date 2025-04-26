package br.com.voltz.model;

import br.com.voltz.enumerations.TransacaoEnum;
import br.com.voltz.service.PlataformaWeb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venda extends Transacao{
    private double idVenda;

    public Venda (String idTransacao, double valor, Date data){
        super(idTransacao,valor,data,null,null, TransacaoEnum.VENDA, 0.015);
    }

    public Venda(String idTransacao, double valor, Ativo ativo, double taxa, double idVenda) {
        super(idTransacao, valor, ativo, taxa);
        this.idVenda = idVenda;

    }

    public Venda(String idTransacao, double valor, Ativo ativo, double taxa) {
        super(idTransacao, valor, ativo, taxa);
    }

    public double getIdvenda() {
        return idVenda;
    }

    public void setIdvenda(double idvenda) {
        this.idVenda = idvenda;
    }

    @Override
    public  String getTaxaTransacao(){
        double valorDaTaxa = getValor()* getTaxa();
        String taxaFormatada = String.format("%.2f", (getValor() * getTaxa()));
        return "Taxa aplicada: R$ " + taxaFormatada;
    }
    @Override
    public String getexecutarTransacao() {
        double valorComTaxa = getValor() - (getValor()* getTaxa());
        String valorFormatado = String.format("%.2f", valorComTaxa);


        return "Venda " + idVenda + " de valor " + valorFormatado+ " executada em " + getData().toString() +
               " para o ativo " + getAtivo().getNome()+  "\n" +
              getTaxaTransacao();

   }
    @Override
            public String getcancelarTransacao() {
            return "Venda" + idVenda+ " valor: "+ getValor() + " Data: " + getData()+ " cancelada.";

       }


    }
