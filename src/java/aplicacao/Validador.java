
package aplicacao;

import java.util.InputMismatchException;


public class Validador 
{
    private String nome;
    private String cpf;
    private String senha;
    private String suspenso;
    private String descricao;
    private String categoriaDescricao;
    private float valor;
    private String operacao;
    private String data;
    

    public Validador(String cpf, String senha) 
    {
        this.cpf = cpf;
        this.senha = senha;
    }

    public Validador(String nome, String cpf, String senha) 
    {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
    }

    public Validador(String nome, String cpf, String senha, String suspenso) 
    {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.suspenso = suspenso;
    }

    public Validador(String descricao) 
    {
        this.descricao = descricao;
    }  
    
    public Validador(String categoriaDescricao, float valor, 
            String operacao, String data, String descricao) {
        this.categoriaDescricao = categoriaDescricao;
        this.valor = valor;
        this.operacao = operacao;
        this.data = data;
        this.descricao = descricao;
    }
    
    public boolean validaCategoria()
    {
        if(!(isNome(this.descricao)))
        {
           return false; 
        }        
        return true;
    }
    
    public boolean validaUsuario()
    {
        if(!(isNome(this.nome)))
        {
           return false; 
        }
        if(!(isCPF(removeMascaraCPF(this.cpf))))
        {
           return false; 
        }
        if(!(isSenha(this.senha)))
        {
           return false; 
        }
        if(!(isSuspenso(this.suspenso)))
        {
           return false; 
        }
        return true;
    }
    
    public boolean validaAdmin()
    {
        if(!(isNome(this.nome)))
        {
           return false; 
        }
        if(!(isCPF(removeMascaraCPF(this.cpf))))
        {
           return false; 
        }
        if(!(isSenha(this.senha)))
        {
           return false; 
        }
        return true;
    }
    
    public boolean validaLogin()
    {
        if(!(isCPF(removeMascaraCPF(this.cpf))))
        {
           return false; 
        }
        if(!(isSenha(this.senha)))
        {
           return false; 
        }
        return true;
    }
    
    public boolean validaLancamento()
    {
        if (!(isValor(this.valor))) {
            return false;
        }
        if (!(validaData(this.data))) {
            return false;
        }
        
        return true;
    }
    
    private boolean isSuspenso(String suspensoValida)
    {
        if((suspensoValida.compareTo("N") == 0) || (suspensoValida.compareTo("S") == 0))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private boolean isNome(String nome) 
    {
      char[] charArray = nome.toCharArray();
      String charValidos = "ÀÁÁÂÃÈÉÊÌÍÎÒÓÔÕÙÚÛàáâãèéêìíîòóôõùúûcç";
      for (int i = 0; i < charArray.length; i++) 
      {
         char ch = charArray[i];
         if(ch != ' ')
         {
             if (!((ch >= 'a' && ch <= 'z') || ((ch >= 'A' && ch <= 'Z')) || (charValidos.indexOf(ch) != -1))) 
            {
               return false;
            }
         }
      }
      return true;
   }
    
    private boolean isSenha(String senha)
    {
        if(senha.contains(",") || senha.contains(";") || senha.contains("=") || senha.contains("\"") || senha.contains("'") || senha.contains("(") || senha.contains(")"))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    private boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
            CPF.equals("11111111111") ||
            CPF.equals("22222222222") || CPF.equals("33333333333") ||
            CPF.equals("44444444444") || CPF.equals("55555555555") ||
            CPF.equals("66666666666") || CPF.equals("77777777777") ||
            CPF.equals("88888888888") || CPF.equals("99999999999") ||
            (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
            num = (int)(CPF.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); 

            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
            num = (int)(CPF.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                 dig11 = '0';
            else dig11 = (char)(r + 48);

            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                 return(true);
            else return(false);
                } catch (InputMismatchException erro) {
                return(false);
            }
        }

    private String removeMascaraCPF(String CPF) 
    {
        String cpfSemMascara = "";
        cpfSemMascara = cpfSemMascara + CPF.substring(0, 3);
        cpfSemMascara = cpfSemMascara + CPF.substring(4, 7);
        cpfSemMascara = cpfSemMascara + CPF.substring(8, 11);
        cpfSemMascara = cpfSemMascara + CPF.substring(12, 14);
        return cpfSemMascara;
        
    }

    private boolean isValor(float valor) {
        if (valor <= 0) {
            return false;
        }
        
        return true;
    }

    private boolean validaData(String data){
        System.out.println(data);
        int ano = Integer.parseInt(data.substring(6, 10));
        
        if (ano < 1900 || ano > 2100){
            return false;
        }
        
        return true;
    }
    
}
