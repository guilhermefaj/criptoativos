package br.com.voltz.dao;

import br.com.voltz.exception.entidadeNaoEncontrada;
import br.com.voltz.factory.ConecctionFactory;
import br.com.voltz.service.Monitoramento;
import br.com.voltz.service.PlataformaWeb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MonitoramentoDao {
        private Connection conexao;
        public MonitoramentoDao()  throws SQLException {
            conexao = ConecctionFactory.getConnection();
        }
        public void cadastrarMonitoramento (Monitoramento monitoramento) throws SQLException {
            String id = UUID.randomUUID().toString().substring(0,8);
            PreparedStatement stm = conexao.prepareStatement("INSERT INTO Monitoramento (idMonitoramento, data, idPlataforma) " +
                    "VALUES (?,?,?)");
            stm.setString(1,id);
            stm.setDate(2,new java.sql.Date(monitoramento.getData().getTime()));
            stm.setString(3,monitoramento.getPlataformaWeb().getIdPlataforma());
            stm.executeUpdate();
        }
        public void fecharConexao() throws SQLException {
            conexao.close();
        }
        private Monitoramento parseMonitoramento( ResultSet result) throws SQLException{
            String id = result.getString("idMonitoramento");
            Date data = result.getDate("data");
            PlataformaWeb plataformaWeb = new PlataformaWeb(result.getString("idPlataforma"));
            return new Monitoramento(id,data,plataformaWeb);
        }
        public Monitoramento buscarMonitoramentoId (String id) throws SQLException, entidadeNaoEncontrada {
            PreparedStatement stm = conexao.prepareStatement("SELECT * FROM Monitoramento WHERE idMonitoramento = ?");
            stm.setString(1,id);
            ResultSet resultado = stm.executeQuery();
            if(!resultado.next())//Se o resultado for vazio
                throw new entidadeNaoEncontrada("Monitoramento não encontrado");

            return parseMonitoramento(resultado);
        }

        public List<Monitoramento> listarMonitoramentos () throws SQLException, entidadeNaoEncontrada {
            PreparedStatement stm = conexao.prepareStatement("SELECT * FROM Monitoramentos");
            ResultSet resultado = stm.executeQuery();
            List<Monitoramento> lista = new ArrayList<>();
            while (resultado.next()){
                lista.add(parseMonitoramento(resultado));
            }
            return lista;
        }
        public void atualizarDadosMonitoramento (Monitoramento monitoramento, String id ) throws SQLException, entidadeNaoEncontrada{
            PreparedStatement stm = conexao.prepareStatement("UPDATE Monitoramento SET data = ?, idPlataforma = ?  WHERE idMonitoramento = ? ");
            stm.setDate(1,new java.sql.Date(monitoramento.getData().getTime()));
            stm.setString(2,monitoramento.getPlataformaWeb().getIdPlataforma());
            stm.setString(3,monitoramento.getIdMonitoramento());
            stm.executeUpdate();

        }
        public void deleteMonitoramento (String id) throws SQLException, entidadeNaoEncontrada{
            PreparedStatement stm = conexao.prepareStatement("DELETE FROM Monitoramento WHERE idMonitoramento = ?");
            stm.setString(1,id);
            int linha = stm.executeUpdate();
            if(linha == 0)
                throw new entidadeNaoEncontrada("Monitoramento não encontrado");
        }
    }


