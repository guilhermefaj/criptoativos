package br.com.voltz.dao;

import br.com.voltz.exception.entidadeNaoEncontrada;
import br.com.voltz.factory.ConecctionFactory;
import br.com.voltz.model.Usuario;
import br.com.voltz.service.PlataformaWeb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsuarioDao {
   private Connection conexao;
   public UsuarioDao()  throws SQLException {
       conexao = ConecctionFactory.getConnection();
  }
  public void cadastrarUsuario(Usuario usuario) throws SQLException {
      String id = UUID.randomUUID().toString().substring(0,8);
      PreparedStatement stm = conexao.prepareStatement("INSERT INTO Usuario(idUsuario, nome, email,senhaHash,saldoTotal) " +
              "VALUES (?,?,?,?,?)");
      stm.setString(1,id);
      stm.setString(2,usuario.getNome());
      stm.setString(3,usuario.getEmail());
      stm.setString(4,usuario.getSenha());
      stm.setDouble(5,usuario.getSaldoTotal());
      stm.executeUpdate();
  }
  public void fecharConexao() throws SQLException {
      conexao.close();
  }
    private Usuario parseUsuario( ResultSet result) throws SQLException{
        String id = result.getString("idUsuario");
        String nome = result.getString("nome");
        String email= result.getString("email");
        String senha = result.getString("senhaHash");
        String saldo = result.getString("saldoTotal");
        return new Usuario(id,nome,email,senha);
    }
  public Usuario buscarUsuarioPorEmail(String email) throws SQLException, entidadeNaoEncontrada {
       PreparedStatement stm = conexao.prepareStatement("SELECT * FROM Usuario WHERE email = ?");
       stm.setString(1,email);
      ResultSet resultado = stm.executeQuery();
      if(!resultado.next())//Se o resultado for vazio
          throw new entidadeNaoEncontrada("Usuario não encontrado");

      return parseUsuario(resultado);
  }

 public List<Usuario> listarDadosUsuario(String email) throws SQLException, entidadeNaoEncontrada {
       PreparedStatement stm = conexao.prepareStatement("SELECT * FROM Usuario WHERE email = ?");
       stm.setString(1,email);
       ResultSet resultado = stm.executeQuery();

       List<Usuario> lista = new ArrayList<>();
       while (resultado.next()){
           lista.add(parseUsuario(resultado));
       }
       return lista;
 }
 public void atualizarDadosUsuario(Usuario usuario, String emailOriginal) throws SQLException, entidadeNaoEncontrada{
       PreparedStatement stm = conexao.prepareStatement("UPDATE Usuario SET nome = ?,email= ?,senhaHash= ? WHERE email =?");
       stm.setString(1,usuario.getNome());
       stm.setString(2,usuario.getEmail());
       stm.setString(3,usuario.getSenha());
       stm.setString(4, emailOriginal);
       stm.executeUpdate();

 }
 public void deleteUsuario(String email) throws SQLException, entidadeNaoEncontrada{
       PreparedStatement stm = conexao.prepareStatement("DELETE FROM Usuario WHERE email= ?");
       stm.setString(1,email);
       int linha = stm.executeUpdate();
       if(linha == 0)
           throw new entidadeNaoEncontrada("Usuario não encontrado");
 }
}
