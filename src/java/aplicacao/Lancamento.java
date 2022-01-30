
package aplicacao;

import java.math.BigDecimal;


public class Lancamento 
{
    private int id;
    private String categoriaDescricao = "";
    private BigDecimal valor = new BigDecimal("0.00");
    private String operacao = "";
    private String data = "";
    private String descricao = "";

    public Lancamento(int id, String categoriaDescricao, BigDecimal valor, 
            String operacao, String data, String descricao) 
    {
        this.id = id;
        this.categoriaDescricao = categoriaDescricao;
        this.valor = valor;
        this.operacao = operacao; 
        this.data = data;        
        this.descricao = descricao;
    }
    
    public Lancamento(){
        
    }

    public int getId() 
    {
        return this.id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public String getCategoriaDescricao() 
    {
        return this.categoriaDescricao;
    }

    public void setCategoriaDescricao(String categoriaDescricao) 
    {
        this.categoriaDescricao = categoriaDescricao;
    }

    public String getValor() 
    {
        return this.valor.toString();
    }
    
    public BigDecimal getValorBig() 
    {
        return this.valor;
    }
    
    public String getValorPonto() 
    {
        String aux = "";
        aux = this.valor.toString();
        aux = aux.replace(',', '.');                
        return aux;
    }

    public void setValor(String valor) 
    {
        BigDecimal aux = new BigDecimal(valor);
        this.valor = aux;
    }

    public String getOperacao() 
    {
        return this.operacao;
    }

    public void setOperacao(String operacao) 
    {
        this.operacao = operacao;
    }

    public String getData() 
    {
        return this.data;
    }
    
    public String getDataInverte() 
    {
        if (this.data.equals("")) {
            return this.data;
        }
        
        String aux;
        String[] vet = data.split("/");
        aux = vet[2] + "-" + vet[1] + "-" + vet[0];
        return aux;
    }
    
    public void setData(String data) 
    {
        this.data = data;
    }

    public void setDataInverte(String data) 
    {
        String aux;
        String[] vet = data.split("-");
        aux = vet[2] + "/" + vet[1] + "/" + vet[0];
        this.data = aux;
    }

    public String getDescricao() 
    {
        return this.descricao;
    }

    public void setDescricao(String descricao) 
    {
        this.descricao = descricao;
    } 
    
    
    
}
