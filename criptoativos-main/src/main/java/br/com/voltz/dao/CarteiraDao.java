package br.com.voltz.dao;

import br.com.voltz.exception.entidadeNaoEncontrada;
import br.com.voltz.factory.ConecctionFactory;
import br.com.voltz.model.Ativo;
import br.com.voltz.model.Carteira;
import br.com.voltz.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CarteiraDao {
    private Connection conexao;

    public CarteiraDao()  throws SQLException {
        conexao = ConecctionFactory.getConnection();
        UsuarioDao usuarioDao = new UsuarioDao();
        AtivosDao ativosDao = new AtivosDao();
    }
    public void cadastrarNovaCarteira(Carteira carteira) throws SQLException {
        String id = UUID.randomUUID().toString().substring(0,8);
        PreparedStatement stm = conexao.prepareStatement("INSERT INTO Carteira(idCarteira, idUsuario, idAtivo ,quantidade, dataAquisicao) " +
                "VALUES (?,?,?,?,?)");
        stm.setString(1,id);
        stm.setString(2,carteira.getUsuario().getId());
        stm.setString(3,carteira.getAtivo().getIdAtivo());
        stm.setDouble(4,carteira.getQuantidade());
        stm.setDate(5,new java.sql.Date(carteira.getData().getTime()));
        stm.executeUpdate();
    }
    public void fecharConexao() throws SQLException {
        conexao.close();
    }
    private Carteira parseCarteira( ResultSet result) throws SQLException{
        String id = result.getString("idCarteira");
        String idUsuario = result.getString("idUsuario");
        String idAtivo = result.getString("idAtivo");
        double quantidade = result.getDouble("quantidade");
        Date dataAquisicao = result.getDate("dataAquisicao");
        return new Carteira(id, null, null, quantidade,dataAquisicao);
    }
    public Carteira buscarCarteiraPorId (String id) throws SQLException, entidadeNaoEncontrada {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM Carteira WHERE idCarteira = ?");
        stm.setString(1,id);
        ResultSet resultado = stm.executeQuery();
        if(!resultado.next())//Se o resultado for vazio
            throw new entidadeNaoEncontrada("Carteira não encontrado");

        return parseCarteira(resultado);
    }

    public List<Carteira> listarCarteiras() throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM Carteira");
        ResultSet resultado = stm.executeQuery();
        List<Carteira> lista = new ArrayList<>();
        while (resultado.next()){
            lista.add(parseCarteira(resultado));
        }
        return lista;
    }
    public void atualizarDadosCarteira(Carteira carteira, String id) throws SQLException, entidadeNaoEncontrada{
        PreparedStatement stm = conexao.prepareStatement("UPDATE Carteira SET quantidade =?, dataAquisicao = ?  WHERE idCarteira =?");
        stm.setDouble(1,carteira.getQuantidade());
        stm.setDate(2,carteira.getData());
        stm.setString(3, id);
        stm.executeUpdate();

    }
    public void deleteCarteira (String id) throws SQLException, entidadeNaoEncontrada{
        PreparedStatement stm = conexao.prepareStatement("DELETE FROM Carteira WHERE idCarteira= ?");
        stm.setString(1,id);
        int linha = stm.executeUpdate();
        if(linha == 0)
            throw new entidadeNaoEncontrada("Carteira não encontrada");
    }

}
