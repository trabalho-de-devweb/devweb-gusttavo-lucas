package model;

import aplicacao.Lancamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import aplicacao.PesquisaNaoEncontradaException;

public class LancamentoDAO 
{
    
    private Connection conexao;
    
    public LancamentoDAO() 
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
    
    public ArrayList<Lancamento> getLista(int idConta) 
    {
        ArrayList<Lancamento> resultado = new ArrayList<>();
        try 
        {            
            String sql = "select l.id, c.descricao as categoriaDescricao, valor, operacao, data, l.descricao " +
                            "from lancamentos as l, categorias as c " +
                            "where id_conta = ? and c.id = l.id_categoria";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idConta);
            ResultSet rs = ps.executeQuery();
            while( rs.next() ) 
            {
                Lancamento lancamentoLinha = new Lancamento();
                lancamentoLinha.setId(rs.getInt("id"));
                lancamentoLinha.setCategoriaDescricao(rs.getString("categoriaDescricao"));
                lancamentoLinha.setValor(rs.getString("valor"));
                lancamentoLinha.setOperacao(rs.getString("operacao"));
                lancamentoLinha.setDataInverte(rs.getString("data"));
                lancamentoLinha.setDescricao(rs.getString("descricao"));                
                resultado.add(lancamentoLinha);
            }
        } catch( SQLException e ) 
        {
            System.out.println("Erro de SQL: " + e.getMessage());
        }
        
        return resultado;
    }
    
    public Lancamento getLancamentoPorID( int codigo ) throws PesquisaNaoEncontradaException
    {        
        try 
        {
            String sql = "select l.id, c.descricao as categoriaDescricao, valor, operacao, data, l.descricao " +
                            "from lancamentos as l, categorias as c " +
                            "where l.id = ? and c.id = l.id_categoria";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, codigo);
            
            ResultSet rs = ps.executeQuery();
            
            if ( rs.next() ) 
            {   
                Lancamento lancamentoEncontrado = new Lancamento();
                lancamentoEncontrado.setId(rs.getInt("id"));
                lancamentoEncontrado.setCategoriaDescricao(rs.getString("categoriaDescricao"));
                lancamentoEncontrado.setValor(rs.getString("valor"));
                lancamentoEncontrado.setOperacao(rs.getString("operacao"));
                lancamentoEncontrado.setDataInverte(rs.getString("data"));
                lancamentoEncontrado.setDescricao(rs.getString("descricao"));
                return lancamentoEncontrado;
            }            
        } 
        catch( SQLException e ) 
        {
            System.out.println("Erro de SQL: " + e.getMessage());
        }
        throw new PesquisaNaoEncontradaException();
        
    }
    
    public boolean gravar( Lancamento gravaLancamento, int idConta) 
    {
        try 
        {
            String sql;
            PreparedStatement ps;
            if ( gravaLancamento.getId() == 0 ) 
            {
                sql = "INSERT INTO lancamentos (id_conta, id_categoria, valor, "
                    + "operacao, data, descricao) VALUES (?,"
                    + "(select id from categorias where descricao=? limit 1),?,?,?,?)";
                ps = conexao.prepareStatement(sql);
                ps.setInt(1, idConta);
                ps.setString(2, gravaLancamento.getCategoriaDescricao());
                ps.setString(3, gravaLancamento.getValorPonto());
                ps.setString(4, gravaLancamento.getOperacao());
                ps.setString(5, gravaLancamento.getDataInverte());
                ps.setString(6, gravaLancamento.getDescricao());
            } 
            else 
            {
                sql = "UPDATE lancamentos SET id_categoria="
                    + "(select id from categorias where descricao=? limit 1), "
                    + "valor=?, operacao=?, data=?, descricao=? WHERE id=?";
                ps = conexao.prepareStatement(sql);
                ps.setString(1, gravaLancamento.getCategoriaDescricao());
                ps.setString(2, gravaLancamento.getValorPonto());
                ps.setString(3, gravaLancamento.getOperacao());
                ps.setString(4, gravaLancamento.getDataInverte());
                ps.setString(5, gravaLancamento.getDescricao());
                ps.setInt(6, gravaLancamento.getId());
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
            String sql = "DELETE FROM lancamentos WHERE id = ?";
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
