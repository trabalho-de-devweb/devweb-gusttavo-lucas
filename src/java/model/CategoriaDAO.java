package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import aplicacao.Categoria;
import aplicacao.PesquisaNaoEncontradaException;

public class CategoriaDAO {
    private Connection conexao;
    
    public CategoriaDAO() 
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
    
    public ArrayList<Categoria> getLista() {
        ArrayList<Categoria> resultado = new ArrayList<>();
        try 
        {            
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("select * from categorias");
            while( rs.next() ) 
            {
                Categoria categoriaLinha = new Categoria(rs.getInt("id"), rs.getString("descricao"));
                resultado.add(categoriaLinha);
            }
        } catch( SQLException e ) 
        {
            System.out.println("Erro de SQL: " + e.getMessage());
        }
        
        return resultado;
    }
    
    public Categoria getCategoriaPorID( int codigo ) throws PesquisaNaoEncontradaException
    {        
        try 
        {
            String sql = "SELECT * FROM categorias WHERE id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, codigo);
            
            ResultSet rs = ps.executeQuery();
            
            if ( rs.next() ) 
            {   
                Categoria categoriaLinha = new Categoria();
                categoriaLinha.setId(rs.getInt("id"));
                categoriaLinha.setDescricao(rs.getString("descricao"));
                return categoriaLinha;                
            }            
        } 
        catch( SQLException e ) 
        {
            System.out.println("Erro de SQL: " + e.getMessage());
        }
        throw new PesquisaNaoEncontradaException();
        
    }
    
    public boolean gravar(Categoria gravaCategoria ) {
        try 
        {
            String sql;
            if ( gravaCategoria.getId() == 0 ) 
            {
                sql = "INSERT INTO categorias (descricao) VALUES (?)";
            } 
            else 
            {
                sql = "UPDATE categorias SET descricao=? WHERE id=?";
            }
            
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, gravaCategoria.getDescricao());
            
            if ( gravaCategoria.getId()> 0 )
            {
                ps.setInt(2, gravaCategoria.getId());
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
            String sql = "DELETE FROM categorias WHERE id = ?";
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
