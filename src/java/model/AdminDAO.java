package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import aplicacao.Usuario;
import aplicacao.PesquisaNaoEncontradaException;

public class AdminDAO 
{
    
    private Connection conexao;
    
    public AdminDAO() 
    {
        try 
        {
            this.conexao = ConexaoSQL.criaConexao();
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
            ResultSet rs = stmt.executeQuery("select * from administradores");
            while( rs.next() ) 
            {
                Usuario usuarioLinha = new Usuario(rs.getInt("id"), rs.getString("nome"), 
                        rs.getString("cpf"), rs.getString("senha"), "administrador");
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
            String sql = "SELECT * FROM administradores WHERE cpf= ? AND senha= ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, cpf);
            ps.setString(2, senha);
            
            ResultSet rs = ps.executeQuery();
            
            if ( rs.next() ) 
            {   
                Usuario adminLinha = new Usuario();
                adminLinha.setId(rs.getInt("id"));
                adminLinha.setNome(rs.getString("nome"));
                adminLinha.setCpf(rs.getString("cpf"));
                adminLinha.setSenha(rs.getString("senha"));
                adminLinha.setTipo("administrador");
                return adminLinha;                
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
            String sql = "SELECT * FROM administradores WHERE id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, codigo);
            
            ResultSet rs = ps.executeQuery();
            
            if ( rs.next() ) 
            {   
                Usuario adminLinha = new Usuario();
                adminLinha.setId(rs.getInt("id"));
                adminLinha.setNome(rs.getString("nome"));
                adminLinha.setCpf(rs.getString("cpf"));
                adminLinha.setSenha(rs.getString("senha"));
                adminLinha.setTipo("administrador");
                return adminLinha;                
            }            
        } 
        catch( SQLException e ) 
        {
            System.out.println("Erro de SQL: " + e.getMessage());
        }
        throw new PesquisaNaoEncontradaException();
        
    }
    
    public boolean gravar( Usuario adminAux ) {
        try 
        {
            String sql;
            if ( adminAux.getId() == 0 ) 
            {
                sql = "INSERT INTO administradores (nome, cpf, senha) VALUES (?,?,?)";
            } 
            else 
            {
                sql = "UPDATE administradores SET nome=?, cpf=?, senha=? WHERE id=?";
            }
            
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, adminAux.getNome());
            ps.setString(2, adminAux.getCpf());
            ps.setString(3, adminAux.getSenha());
            
            if ( adminAux.getId()> 0 )
            {
                ps.setInt(4, adminAux.getId());
            }                
            
            ps.execute();            
            return true;
            
        } 
        catch( SQLException e ) 
        {
            System.out.println("Erro de SQL: " + e.getMessage());
            return false;
        }
    }
    
    public boolean excluir( int id ) 
    {
        try 
        {
            String sql = "DELETE FROM administradores WHERE id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } 
        catch( SQLException e ) 
        {
            System.out.println("Erro de SQL: " + e.getMessage());
            return false;
        }
    }

}
