package br.com.voltz.dao;

import br.com.voltz.enumerations.TransacaoEnum;
import br.com.voltz.exception.entidadeNaoEncontrada;
import br.com.voltz.factory.ConecctionFactory;
import br.com.voltz.model.Compra;
import br.com.voltz.model.Transacao;
import br.com.voltz.model.Venda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TransacaoDao {
    private final Connection conexao;
    public TransacaoDao()  throws SQLException {
        conexao = ConecctionFactory.getConnection();
    }
        public void inserirTransacoes(Transacao transacao ) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("INSERT INTO Transacao (idTransacao, valor, tipo, data, idAtivo, idUsuario ) " +
                "VALUES (?,?,?,?,?, ?)");
        String id = UUID.randomUUID().toString().substring(0,8);
        stm.setString(1,id);
        stm.setDouble(2,transacao.getValor());
        stm.setString(3,transacao.getTipo().name());
        stm.setDate(4,new java.sql.Date(transacao.getData().getTime()));
        stm.setString(5,transacao.getUsuario().getId());
        stm.setString(6,transacao.getAtivo().getIdAtivo());
        stm.executeUpdate();
    }
    public void fecharConexao() throws SQLException {
        conexao.close();
    }
    private Transacao parseTransacao (ResultSet result) throws SQLException{
        String id = result.getString("idTransacao");
        double valor = result.getDouble("valor");
        String tipo = result.getString("tipo");
        Date data = result.getDate("data");
        TransacaoEnum tipoEnum = TransacaoEnum.valueOf(tipo);

        if (tipoEnum == TransacaoEnum.COMPRA) {
            return new Compra(id, valor, data);
        } else {
            return new Venda(id, valor, data);
        }
    }

    public Transacao buscarTransacaoId (String id) throws SQLException, entidadeNaoEncontrada {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM Transacao WHERE idTransacao = ?");
        stm.setString(1, id);
        ResultSet resultado = stm.executeQuery();
        if(!resultado.next())//Se o resultado for vazio
            throw new entidadeNaoEncontrada("Transação não encontrado");

        return parseTransacao(resultado);
    }

    public List<Transacao> listarTransacao() throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM Transacao");
        ResultSet resultado = stm.executeQuery();
        List<Transacao> lista = new ArrayList<>();
        while (resultado.next()){
            lista.add(parseTransacao(resultado));
        }
        return lista;
    }
    public void atualizarTransacao (Transacao transacao, String id ) throws SQLException, entidadeNaoEncontrada{
        PreparedStatement stm = conexao.prepareStatement("UPDATE Transacao SET valor = ?,tipo = ?, data = ? WHERE idTransacao  = ?");
        stm.setDouble(1,transacao.getValor());
        stm.setString(2,transacao.getTipo().name());
        stm.setDate(3,new java.sql.Date(transacao.getData().getTime()));
        stm.setString(4, transacao.getIdTransacao());
        stm.executeUpdate();

    }
    public void deleteTransacao (String id) throws SQLException, entidadeNaoEncontrada{
        PreparedStatement stm = conexao.prepareStatement("DELETE FROM Transacao WHERE idTransacao = ?");
        stm.setString(1,id);
        int linha = stm.executeUpdate();
        if(linha == 0)
            throw new entidadeNaoEncontrada("Transação não encontrado, a remoção não pode ser realizada");
    }
}
