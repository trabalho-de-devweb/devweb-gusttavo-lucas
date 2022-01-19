
package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.io.IOException;
import java.io.PrintWriter;

public class ConexaoSQL 
{

    private static Connection conexao = null;
    
    public static Connection criaConexao() throws SQLException 
    {
        if (conexao == null) 
        {
            try 
            {
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("Driver foi carregado!");
                conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/financeiro", "root", "");
                System.out.println("Conexão realizada com sucesso!");
            } 
            catch (ClassNotFoundException e ) 
            {
                System.out.println("Driver não foi localizado!");
            }
        }
        
        return conexao;
    }

}
