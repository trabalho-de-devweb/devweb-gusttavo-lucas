
package aplicacao;
import java.time.*;


public class Login {
    
    private Usuario usuario;
    private LocalDateTime dataHoraRegistro;
    private LocalDateTime dataHoraExpira;
    
    public Login(Usuario usuario)
    {
        this.usuario = usuario;
        this.dataHoraRegistro = LocalDateTime.now();
        this.dataHoraExpira = LocalDateTime.now().plusMinutes(5);
    }

    public boolean expiraLogin()
    {
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        boolean teste = dataHoraAtual.isAfter(dataHoraExpira);
        return teste;
    }
    
    public void atualizaLogin()
    {
        this.dataHoraExpira = LocalDateTime.now().plusMinutes(5);
        return;
    }
        
    public Usuario getUsuario() {
        return usuario;
    }
        
}
