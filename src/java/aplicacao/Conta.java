
package aplicacao;


public class Conta 
{
    private int id;
    private String nome;
    private String numBanco;
    private String numAgencia;
    private String numContaCorrente;

    public Conta(int id, String nome, String numBanco, String numAgencia, 
                    String numContaCorrente) 
    {
        this.id = id;
        this.nome = nome;
        this.numBanco = numBanco;
        this.numAgencia = numAgencia;
        this.numContaCorrente = numContaCorrente;
    }

    public Conta() 
    {
        
    }

    public int getId() 
    {
        return this.id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public String getNome() 
    {
        return this.nome;
    }

    public void setNome(String nome) 
    {
        this.nome = nome;
    }

    public String getNumBanco() 
    {
        return this.numBanco;
    }

    public void setNumBanco(String numBanco) 
    {
        this.numBanco = numBanco;
    }

    public String getNumAgencia() 
    {
        return this.numAgencia;
    }

    public void setNumAgencia(String numAgencia) 
    {
        this.numAgencia = numAgencia;
    }

    public String getNumContaCorrente() 
    {
        return this.numContaCorrente;
    }

    public void setNumContaCorrente(String numContaCorrente) 
    {
        this.numContaCorrente = numContaCorrente;
    }
    
    
    
}
