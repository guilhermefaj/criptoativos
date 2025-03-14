import br.com.voltz.factory.ConecctionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TesteDeConexao {
    public static void main(String[] args) {
        try{
          Connection conexao = ConecctionFactory.getConnection();
          System.out.println("Conectado com sucesso!");
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }
}
