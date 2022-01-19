package aplicacao;


public class Categoria 
{
    private int id;
    private String descricao = "";

    public Categoria(int id, String descricao) 
    {
        this.id = id;
        this.descricao = descricao;
    }
    
    public Categoria() {
        this.id = 0;
    }

    public int getId() {
        return this.id;
    }
    
    public String getDescricao() 
    {
        return this.descricao;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setDescricao(String descricao) 
    {
        this.descricao = descricao;
    }    
    
}
