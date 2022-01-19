
package aplicacao;

public class PesquisaNaoEncontradaException extends Exception 
{
    @Override
    public String getMessage()
    {
        return "Falha";
    }
}
