package aplicacao;

import java.text.ParsePosition;
import java.text.NumberFormat;

public class Conta 
{
    private int id;
    private int idUsuario;
    private String nome = "";
    private String numBanco = "";
    private String numAgencia = "";
    private String numContaCorrente = "";

    public Conta(int id, int idUsuario, String nome, String numBanco, String numAgencia, 
                    String numContaCorrente) 
    {
        this.id = id;
        this.idUsuario = idUsuario;
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
    
    public int getIdUsuario()
    {
        return this.idUsuario;
    }
    
    public void setIdUsuario(int idUsuario){
        this.idUsuario = idUsuario;
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
    
    public boolean validaConta()
    {
        if(!(isNome(this.nome)))
        {
           return false; 
        }
        if(!(isNumBanco(this.numBanco)))
        {
           return false; 
        }
        if(!(isNumAgencia(this.numAgencia)))
        {
           return false; 
        }
        if(!(isNumContaCorrente(this.numContaCorrente)))
        {
           return false; 
        }
        return true;
    }
    
    private boolean isNome(String nome) 
    {
        char[] charArray = nome.toCharArray();
        String charValidos = "ÀÁÁÂÃÈÉÊÌÍÎÒÓÔÕÙÚÛàáâãèéêìíîòóôõùúçÇ";
        char ch;
        for (int i = 0; i < charArray.length; i++) 
        {
            ch = charArray[i];
            if(ch != ' ')
            {
               if (!((ch >= 'a' && ch <= 'z') || ((ch >= 'A' && ch <= 'Z')) || (charValidos.indexOf(ch) != -1))) 
               {
                    System.out.println((int)ch);
                    return false;
               }
            }
        }
        return true;
   }
    
    private boolean isNumBanco(String numBanco)
    {
        if (numBanco.length() != 3) 
        {
            return false;
        } if (!(isInteger(numBanco))) 
        {
            return false;
        }
        
        return true;
    }
    
    private boolean isNumAgencia(String numAgencia)
    {
        return isInteger(numAgencia);
    }
    
    private boolean isNumContaCorrente(String numContaCorrente)
    {
        String numContaFormatada = removeMascaraConta(numContaCorrente);
        
        if (numContaFormatada.length() != 5)
        {
            return false;
        } 
        if (!(isInteger(numContaFormatada))) 
        {
            return false;
        }
        
        return true;
    }
    
    private static boolean isInteger(String str) 
    {
        ParsePosition pos = new ParsePosition(0);
        NumberFormat.getIntegerInstance().parse(str, pos);
        return str.length() == pos.getIndex();
    }
    
    private static String removeMascaraConta(String conta)
    {
        return conta.substring(0, 4) + conta.substring(5, 6);
    }
}
