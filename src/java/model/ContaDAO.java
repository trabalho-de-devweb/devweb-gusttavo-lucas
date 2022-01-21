package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import aplicacao.Conta;
import aplicacao.PesquisaNaoEncontradaException;

public class ContaDAO {
    
    private Connection conexao;
    
    public ContaDAO() 
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
    
    public ArrayList<Conta> getLista() {
        ArrayList<Conta> resultado = new ArrayList<>();
        try 
        {            
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("select * from contas");
            while( rs.next() ) 
            {
                Conta contaLinha = new Conta(rs.getInt("id"), rs.getInt("idUsuario"),
                        rs.getString("nome"), rs.getString("numBanco"), 
                        rs.getString("numAgencia"), rs.getString("numContaCorrente"));
                resultado.add(contaLinha);
            }
        } catch( SQLException e ) 
        {
            System.out.println("Erro de SQL: " + e.getMessage());
        }
        
        return resultado;
    }
    
    public Conta getContaPorID( int codigo ) throws PesquisaNaoEncontradaException
    {        
        try 
        {
            String sql = "SELECT * FROM contas WHERE id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, codigo);
            
            ResultSet rs = ps.executeQuery();
            
            if ( rs.next() ) 
            {   
                Conta contaEncontrada = new Conta();
                contaEncontrada.setId(rs.getInt("id"));
                contaEncontrada.setIdUsuario(rs.getInt("id_usuario"));
                contaEncontrada.setNome(rs.getString("nome"));
                contaEncontrada.setNumBanco(rs.getString("banco"));
                contaEncontrada.setNumAgencia(rs.getString("agencia"));
                contaEncontrada.setNumContaCorrente(rs.getString("conta_corrente"));
                return contaEncontrada;
            }            
        } 
        catch( SQLException e ) 
        {
            System.out.println("Erro de SQL: " + e.getMessage());
        }
        throw new PesquisaNaoEncontradaException();
        
    }
    
    public boolean gravar( Conta gravaConta ) {
        try 
        {
            String sql;
            if ( gravaConta.getId() == 0 ) 
            {
                sql = "INSERT INTO contas (id_usuario, nome_conta, banco, agencia, conta_corrente) VALUES (?,?,?,?,?)";
            } 
            else 
            {
                sql = "UPDATE contas SET id_usuario=?, nome_conta=?, banco=?, agencia=?, conta_corrente=? WHERE id=?";
            }
            
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, gravaConta.getIdUsuario());
            ps.setString(2, gravaConta.getNome());
            ps.setString(3, gravaConta.getNumBanco());
            ps.setString(4, gravaConta.getNumAgencia());
            ps.setString(5, gravaConta.getNumContaCorrente());
            
            if ( gravaConta.getId()> 0 )
            {
                ps.setInt(6, gravaConta.getId());
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
            String sql = "DELETE FROM contas WHERE id = ?";
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

