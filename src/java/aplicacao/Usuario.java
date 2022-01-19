
package aplicacao;


public class Usuario 
{
    
    private int id;
    private String nome = "";
    private String cpf = "";
    private String senha = "";
    private String suspenso = "";
    private String tipo = "";
    
    
    public Usuario(int id, String nome, String cpf, String senha, String suspenso, String tipo)
    {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.suspenso = suspenso;
        this.tipo = tipo;        
    }
    
    public Usuario(int id, String nome, String cpf, String senha, String tipo)
    {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.tipo = tipo;        
    }

    public Usuario() 
    {
        this.id = 0;
    }
    
    
    public void suspendeUsuario()
    {
        this.suspenso = "S";
        return;
    }
    
    public void ativaUsuario()
    {
        this.suspenso = "N";
        return;
    }

    public int getId() 
    {
        return this.id;
    }

    public String getNome() 
    {
        return this.nome;
    }

    public String getCpf() 
    {
        return this.cpf;
    }

    public String getSenha() 
    {
        return this.senha;
    }

    public String getTipo() 
    {
        return this.tipo;
    }

    public String getStatus() 
    {
        return this.suspenso;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public void setNome(String nome) 
    {
        this.nome = nome;
    }

    public void setCpf(String cpf) 
    {
        this.cpf = cpf;
    }

    public void setSenha(String senha) 
    {
        this.senha = senha;
    }

    public void setTipo(String tipo) 
    {
        this.tipo = tipo;
    }

    public void setSuspenso(String suspenso) 
    {
        this.suspenso = suspenso;
    }  
    
}
