
package aplicacao;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.InputMismatchException;


public class Validador 
{
    private String nome;
    private String cpf;
    private String senha;
    private String suspenso;
    private String descricao;
    private String categoriaDescricao;
    private BigDecimal valor;
    private String operacao;
    private String data;
    private String numBanco;
    private String numAgencia;
    private String numContaCorrente;
    private String mensagem;    
    

    public Validador(String cpf, String senha) 
    {
        this.cpf = cpf;
        this.senha = senha;
        this.mensagem = "";
    }

    public Validador(String nome, String cpf, String senha) 
    {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.mensagem = "";
    }

    public Validador(String nome, String cpf, String senha, String suspenso) 
    {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.suspenso = suspenso;
        this.mensagem = "";
    }

    public Validador(String descricao) 
    {
        this.descricao = descricao;
    }  
    
    public Validador(String categoriaDescricao, BigDecimal valor, 
            String operacao, String data, String descricao) 
    {
        this.categoriaDescricao = categoriaDescricao;
        this.valor = valor;
        this.operacao = operacao;
        this.data = data;
        this.descricao = descricao;
        this.mensagem = "";
    }
    
    public Validador(String nome, String numBanco, String numAgencia, String numContaCorrente, String msg) 
    {
        this.nome = nome;
        this.numBanco = numBanco;
        this.numAgencia = numAgencia;
        this.numContaCorrente = numContaCorrente;
        this.mensagem = msg;
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
        if(!(isNome(this.categoriaDescricao)))
        {           
           return false; 
        } 
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
            this.mensagem = "Erro!Campo Suspenso só aceita valores iguais a 'S' ou 'N'.";
            return false;
        }
    }
    
    private boolean isNome(String nome) 
    {
        if(nome.length() > 20)
        {
            this.mensagem = "Erro!Nome deve conter no máximo 20 caracteres.";
            return false;
        }
        char[] charArray = nome.toCharArray();
        String charValidos = "ÀÁÁÂÃÈÉÊÌÍÎÒÓÔÕÙÚÛàáâãèéêìíîòóôõùúûç";
        for (int i = 0; i < charArray.length; i++) 
        {
           char ch = charArray[i];
           if(ch != ' ')
           {
               if (!((ch >= 'a' && ch <= 'z') || ((ch >= 'A' && ch <= 'Z')) || (charValidos.indexOf(ch) != -1))) 
                {
                    this.mensagem = "Erro!Nome só pode conter letras maiúsculas, minúsculas e com acento.";
                    return false;
                }
           }
        }
        return true;
   }
    
    private boolean isSenha(String senha)
    {
        if(senha.length() > 255)
        {
            this.mensagem = "Erro!Senha deve ter no máximo 255 caracteres.";
            return false;
        }
        else if(senha.contains(",") || senha.contains(";") || senha.contains("=") || senha.contains("\"") || senha.contains("'") || senha.contains("(") || senha.contains(")"))
        {
            this.mensagem = "Erro!Senha não deve conter ',', ';', '=', '\', ''', '(' e ')'.";
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
        {
            this.mensagem = "Erro!CPF inválido.";
            return(false);
        }
            

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
            else 
            {
                this.mensagem = "Erro!CPF inválido.";
                return(false);
            }
        } 
        catch (InputMismatchException erro) 
        {
            this.mensagem = "Erro!CPF inválido.";
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

    private boolean isValor(BigDecimal valor) 
    {
        if((valor.compareTo(new BigDecimal("0")) <= 0) || (valor.compareTo(new BigDecimal("99999999.99")) > 0)) 
        {
            this.mensagem = "Erro!Campo Valor só aceita valores maiores que 0 e menores que 100.000.000,00 com uma precisão de 2 casas decimais.";
            return false;
        }        
        return true;
    }

    private boolean validaData(String data)
    {
        int ano = Integer.parseInt(data.substring(6, 10));        
        if (ano < 1900 || ano > 2100)
        {
            this.mensagem = "Erro!Data deve estar entre 01/01/1900 e 31/12/2100.";
            return false;
        }        
        return true;
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
    
    private boolean isNumBanco(String numBanco)
    {
        if (numBanco.length() != 3) 
        {
            this.mensagem = "Erro!Número de banco deve ter exatamente 3 digitos.";
            return false;
        } 
        if (!(isInteger(numBanco))) 
        {
            this.mensagem = "Erro!Campo de Número do Banco deve conter apenas números.";
            return false;
        }        
        return true;
    }
    
    private boolean isNumAgencia(String numAgencia)
    {
        boolean teste = true;
        if(numAgencia.length() > 6)
        {
            this.mensagem = "Erro!Número da agência deve ter menos de 7 digitos.";
            teste = false;
        }
        else if(!(isInteger(numAgencia)))
        {
            this.mensagem = "Erro!Campo de Agência deve conter apenas números.";
            teste = false;
        }
        return teste;
    }
    
    private boolean isNumContaCorrente(String numContaCorrente)
    {
        if (numContaCorrente.length() != 6) 
        {
            this.mensagem = "Erro!Número de conta deve ser no formato XXXX-X onde os X devem ser apenas números.";
            return false;
        }
        
        String numContaFormatada = removeMascaraConta(numContaCorrente);
        
        if (numContaFormatada.length() != 5)
        {
            this.mensagem = "Erro!Número de conta deve ser no formato XXXX-X onde os X devem ser apenas números.";
            return false;
        } 
        if (!(isInteger(numContaFormatada))) 
        {
            this.mensagem = "Erro!Número de conta deve ser no formato XXXX-X onde os X devem ser apenas números.";
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
    

    public String getMensagem() 
    {
        return this.mensagem;
    }      
    
}
