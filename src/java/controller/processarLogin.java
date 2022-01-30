
package controller;

import aplicacao.Login;
import aplicacao.PesquisaNaoEncontradaException;
import aplicacao.Usuario;
import aplicacao.Validador;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AdminDAO;
import model.UsuarioDAO;

@WebServlet(name = "processarLogin", urlPatterns = {"/processarLogin"})
public class processarLogin extends HttpServlet 
{
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        
        String acao = (String) request.getParameter("acao");        
        if(acao != null)
        {
            if(acao.compareTo("sair") == 0)
            {
                HttpSession sessionUsuario = request.getSession();
                sessionUsuario.invalidate();
                RequestDispatcher loginEncaminhar = getServletContext().getRequestDispatcher("/login.jsp");
                loginEncaminhar.forward(request, response);
            }
        }
        else
        {
            RequestDispatcher loginEncaminhar = getServletContext().getRequestDispatcher("/login.jsp");
            loginEncaminhar.forward(request, response);   
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
              
        request.setCharacterEncoding("UTF-8");
        String mensagem;
        String servletDeRetorno = "login.jsp";
        String tipo = request.getParameter("login");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        
        Validador loginValidado = new Validador(cpf, senha);
        
        if(!(loginValidado.validaLogin()))
        {
            mensagem = loginValidado.getMensagem();
            request.setAttribute("mensagem", mensagem);
            request.setAttribute("servletDeRetorno", servletDeRetorno);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Mensagem.jsp");
            rd.forward(request, response);
            return;
        }
        
            
        if (tipo.equals("admin")) 
        {
            AdminDAO adminDAO = new AdminDAO();
            try
            {
                Usuario adminLogado = adminDAO.getUsuarioPorLogin(cpf, senha);
                Login loginAdmin = new Login(adminLogado);
                HttpSession sessionUsuario = request.getSession();
                sessionUsuario.setAttribute("loginAdmin", loginAdmin);                
                RequestDispatcher adminEncaminhar = getServletContext().getRequestDispatcher("/area_restritaADM.jsp");
                adminEncaminhar.forward(request, response);
            }
            catch (PesquisaNaoEncontradaException e)
            {
                mensagem = "Administrador não encontrado!CPF ou senha incorretos!";
                request.setAttribute("mensagem", mensagem);
                request.setAttribute("servletDeRetorno", servletDeRetorno);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/Mensagem.jsp");
                rd.forward(request, response);
            }       

        }
        if (tipo.equals("user")) 
        {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            try
            {
                Usuario usuarioLogado = usuarioDAO.getUsuarioPorLogin(cpf, senha);
                if(usuarioLogado.getStatus().compareTo("S") == 0)
                {
                    mensagem = "Usuário suspenso! Contate o administrador.";
                    request.setAttribute("mensagem", mensagem);
                    request.setAttribute("servletDeRetorno", servletDeRetorno);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Mensagem.jsp");
                    rd.forward(request, response); 
                }
                else
                {
                    Login loginUsuario = new Login(usuarioLogado);
                    HttpSession sessionUsuario = request.getSession();
                    sessionUsuario.setAttribute("loginUsuario", loginUsuario);
                    RequestDispatcher usuarioEncaminhar = getServletContext().getRequestDispatcher("/area_restritaUSER.jsp");
                    usuarioEncaminhar.forward(request, response); 
                }                
            }
            catch (PesquisaNaoEncontradaException e)
            {
                mensagem = "Usuário não encontrado!CPF ou senha incorretos!";
                request.setAttribute("mensagem", mensagem);
                request.setAttribute("servletDeRetorno", servletDeRetorno);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/Mensagem.jsp");
                rd.forward(request, response);
            }       

        }
        
    }

}
