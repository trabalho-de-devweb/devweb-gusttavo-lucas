package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import aplicacao.Usuario;
import aplicacao.PesquisaNaoEncontradaException;

public class UsuarioDAO {
    
    private Connection conexao;
    
    public UsuarioDAO() 
    {
        try 
        {
            conexao = ConexaoSQL.criaConexao();
        } 
        catch(Exception e) 
        {
            System.out.println("Erro na criação da conexão DAO!");
            System.out.println(e);
        }
    }
    
    public ArrayList<Usuario> getLista() {
        ArrayList<Usuario> resultado = new ArrayList<>();
        try 
        {            
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("select * from usuarios");
            while( rs.next() ) 
            {
                Usuario usuarioLinha = new Usuario(rs.getInt("id"), 
                        rs.getString("nome"), rs.getString("cpf"), 
                        rs.getString("senha"), rs.getString("suspenso"), 
                        "correntista");
                resultado.add(usuarioLinha);
            }
        } catch( SQLException e ) 
        {
            System.out.println("Erro de SQL: " + e.getMessage());
        }
        
        return resultado;
    }
    
    public Usuario getUsuarioPorLogin( String cpf, String senha ) throws PesquisaNaoEncontradaException
    {        
        try 
        {
            String sql = "SELECT * FROM usuarios WHERE cpf= ? AND senha= ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, cpf);
            ps.setString(2, senha);
            
            ResultSet rs = ps.executeQuery();
            
            if ( rs.next() ) 
            {   
                Usuario usuarioEncontrado = new Usuario(rs.getInt("id"), 
                        rs.getString("nome"), rs.getString("cpf"), 
                        rs.getString("senha"), rs.getString("suspenso"), "correntista");
                return usuarioEncontrado;                
            }            
        } 
        catch( SQLException e ) 
        {
            System.out.println("Erro de SQL: " + e.getMessage());
        }
        throw new PesquisaNaoEncontradaException();
        
    }
    
    public Usuario getUsuarioPorID( int codigo ) throws PesquisaNaoEncontradaException
    {        
        try 
        {
            String sql = "SELECT * FROM usuarios WHERE id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, codigo);
            
            ResultSet rs = ps.executeQuery();
            
            if ( rs.next() ) 
            {   
                Usuario usuarioEncontrado = new Usuario();
                usuarioEncontrado.setId(rs.getInt("id"));
                usuarioEncontrado.setNome(rs.getString("nome"));
                usuarioEncontrado.setCpf(rs.getString("cpf"));
                usuarioEncontrado.setSenha(rs.getString("senha"));
                usuarioEncontrado.setSuspenso(rs.getString("suspenso"));
                usuarioEncontrado.setTipo("correntista");
                return usuarioEncontrado;
            }            
        } 
        catch( SQLException e ) 
        {
            System.out.println("Erro de SQL: " + e.getMessage());
        }
        throw new PesquisaNaoEncontradaException();
        
    }
    
    public boolean gravar( Usuario gravaUsuario ) {
        try 
        {
            String sql;
            if ( gravaUsuario.getId() == 0 ) 
            {
                sql = "INSERT INTO usuarios (nome, cpf, senha, suspenso) VALUES (?,?,?,?)";
            } 
            else 
            {
                sql = "UPDATE usuarios SET nome=?, cpf=?, senha=?, suspenso=? WHERE id=?";
            }
            
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, gravaUsuario.getNome());
            ps.setString(2, gravaUsuario.getCpf());
            ps.setString(3, gravaUsuario.getSenha());
            ps.setString(4, gravaUsuario.getStatus());
            
            if ( gravaUsuario.getId()> 0 )
            {
                ps.setInt(5, gravaUsuario.getId());
            }                
            
            ps.execute();            
            return true;
            
        } catch( SQLException e ) {
            System.out.println("Erro de SQL: " + e.getMessage());
            return false;
        }
    }
    
    public boolean excluir( int id ) throws SQLException
    {
        try 
        {
            String sql = "DELETE FROM usuarios WHERE id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } 
        catch( SQLException e ) 
        {
            if(e.getCause().getClass().toString() == "MySQLIntegrityConstraintViolationException")
            {
                throw e;
            }
            System.out.println("Erro de SQL: " + e.getMessage());
            return false;
        }
    }

}
