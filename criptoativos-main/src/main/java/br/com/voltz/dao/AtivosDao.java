package br.com.voltz.dao;

import br.com.voltz.enumerations.AtivosEnum;
import br.com.voltz.exception.entidadeNaoEncontrada;
import br.com.voltz.factory.ConecctionFactory;
import br.com.voltz.model.Ativo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AtivosDao {
    private Connection conexao;
    public AtivosDao()  throws SQLException {
        conexao = ConecctionFactory.getConnection();
    }
    public void cadastrarAtivo(Ativo ativo) throws SQLException {
        String id = UUID.randomUUID().toString().substring(0,8);
        PreparedStatement stm = conexao.prepareStatement("INSERT INTO Ativo (idAtivo, nome, valorAtual ,volatilidade,idPlataforma) " +
                "VALUES (?,?,?,?,?)");
        stm.setString(1,id);
        stm.setString(2,ativo.getNome().name());
        stm.setDouble(3,ativo.getValorAtual());
        stm.setDouble(4, ativo.getVolatilidade());
        stm.setString(5,ativo.getPlataformaWeb().getIdPlataforma());
        stm.executeUpdate();
    }
    public void fecharConexao() throws SQLException {
        conexao.close();
    }
    private Ativo parseAtivo( ResultSet result) throws SQLException{
        String id = result.getString("idAtivo");
        String nomeStr= result.getString("nome");
        AtivosEnum nome = AtivosEnum.valueOf(nomeStr);
        double valorAtual = result.getDouble("valorDoub");
        double volatilidade = result.getDouble("volatilidade");
        return new Ativo(id,nome,valorAtual, volatilidade);
    }
    public Ativo buscarAtivoId (String id) throws SQLException, entidadeNaoEncontrada {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM Ativo WHERE idAtivo = ?");
        stm.setString(1, id);
        ResultSet resultado = stm.executeQuery();
        if(!resultado.next())//Se o resultado for vazio
            throw new entidadeNaoEncontrada("Ativo não encontrado");

        return parseAtivo(resultado);
    }

    public List<Ativo> listarAtivos() throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM Ativo");
        ResultSet resultado = stm.executeQuery();
        List<Ativo> lista = new ArrayList<>();
        while (resultado.next()){
            lista.add(parseAtivo(resultado));
        }
        return lista;
    }
    public void atualizarAtivos(Ativo ativo, String id ) throws SQLException, entidadeNaoEncontrada{
        PreparedStatement stm = conexao.prepareStatement("UPDATE Ativo SET nome = ?,valorAtual = ?, volatilidade = ? WHERE idAtivo  = ?");
        stm.setString(1,ativo.getNome().name());
        stm.setDouble(2,ativo.getValorAtual());
        stm.setDouble(3,ativo.getVolatilidade());
        stm.setString(4, ativo.getIdAtivo());
        stm.executeUpdate();

    }
    public void deleteAtivo(String id) throws SQLException, entidadeNaoEncontrada{
        PreparedStatement stm = conexao.prepareStatement("DELETE FROM Usuario WHERE idAtivo = ?");
        stm.setString(1,id);
        int linha = stm.executeUpdate();
        if(linha == 0)
            throw new entidadeNaoEncontrada("Ativo não encontrado, remoção não pode ser realizada");
    }
}
