package br.com.voltz.dao;

import br.com.voltz.exception.entidadeNaoEncontrada;
import br.com.voltz.factory.ConecctionFactory;
import br.com.voltz.service.PlataformaWeb;
import br.com.voltz.service.Seguranca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SegurancaDao {
    private Connection conexao;
    public SegurancaDao()  throws SQLException {
        conexao = ConecctionFactory.getConnection();
    }
    public void cadastrarSeguranca (Seguranca seguranca) throws SQLException {
        String id = UUID.randomUUID().toString().substring(0,8);
        PreparedStatement stm = conexao.prepareStatement("INSERT INTO Seguranca (idSeguranca, nivelCriptografia, tentativasFalhas, idPlataforma) " +
                "VALUES (?,?,?,?)");
        stm.setString(1,id);
        stm.setString(2, seguranca.getNivelCriptografia());
        stm.setInt(3,seguranca.getTentativasFalhas());
        stm.setString(4, seguranca.getPlataforma().getIdPlataforma());
        stm.executeUpdate();
    }
    public void fecharConexao() throws SQLException {
        conexao.close();
    }
    private Seguranca parseSeguranca ( ResultSet result) throws SQLException{
        String id = result.getString("idSeguranca");
        String nivelCriptografia = result.getString("nivelCriptografia");
        int tentativasFalhas = result.getInt("tentativasFalhas");
        PlataformaWeb plataformaWeb = new PlataformaWeb(result.getString("idPlataforma"));
        return new Seguranca(id,nivelCriptografia,tentativasFalhas,plataformaWeb);
    }
    public Seguranca buscarSegurancaId (String id) throws SQLException, entidadeNaoEncontrada {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM Seguranca WHERE idSeguranca = ?");
        stm.setString(1,id);
        ResultSet resultado = stm.executeQuery();
        if(!resultado.next())//Se o resultado for vazio
            throw new entidadeNaoEncontrada("Segurança não encontrado");

        return parseSeguranca(resultado);
    }

    public List<Seguranca> listarSeguranca () throws SQLException, entidadeNaoEncontrada {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM Seguranca");
        ResultSet resultado = stm.executeQuery();
        List<Seguranca> lista = new ArrayList<>();
        while (resultado.next()){
            lista.add(parseSeguranca(resultado));
        }
        return lista;
    }
    public void atualizarDadosSeguranca (Seguranca seguranca, String id ) throws SQLException, entidadeNaoEncontrada{
        PreparedStatement stm = conexao.prepareStatement("UPDATE Seguranca SET nivelCriptografia = ?, tentativasFalhas = ? , idPlataforma =?  WHERE idSeguranca = ? ");
        stm.setString(1,seguranca.getNivelCriptografia());
        stm.setInt(2,seguranca.getTentativasFalhas());
        stm.setString(3,seguranca.getPlataforma().getIdPlataforma());
        stm.executeUpdate();

    }
    public void deleteSeguranca (String id) throws SQLException, entidadeNaoEncontrada{
        PreparedStatement stm = conexao.prepareStatement("DELETE FROM Seguranca WHERE idSeguranca = ?");
        stm.setString(1,id);
        int linha = stm.executeUpdate();
        if(linha == 0)
            throw new entidadeNaoEncontrada("Segurança não encontrado");
    }
}
