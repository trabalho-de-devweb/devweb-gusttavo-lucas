package controller;

import aplicacao.Login;
import aplicacao.Usuario;
import aplicacao.Validador;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AdminDAO;

@WebServlet(name = "ControllerAdmin", urlPatterns = {"/ControllerAdmin"})
public class ControllerAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
                
        HttpSession sessionUsuario = request.getSession();
        if(sessionUsuario.getAttribute("loginAdmin") == null)
        {
            RequestDispatcher validaLogin = getServletContext().getRequestDispatcher("/processarLogin");
            validaLogin.forward(request, response);
        }
        else if(((Login)sessionUsuario.getAttribute("loginAdmin")).expiraLogin())
        {
            RequestDispatcher validaLogin = getServletContext().getRequestDispatcher("/processarLogin");
            validaLogin.forward(request, response);
        }
        else
        {
            
        Login loginAtualizado = (Login)sessionUsuario.getAttribute("loginAdmin");
        loginAtualizado.atualizaLogin();
        sessionUsuario.setAttribute("loginAdmin", loginAtualizado);
        
        String mensagem;
        String servletDeRetorno = "ControllerAdmin?acao=mostrar";
        AdminDAO adminController = new AdminDAO();
        String acao = (String) request.getParameter("acao");
        Usuario adminAtributo;

        switch (acao) {
            case "mostrar":
                ArrayList<Usuario> admLista;
                admLista = adminController.getLista();
                request.setAttribute("admLista", admLista);
                RequestDispatcher mostrar = getServletContext().getRequestDispatcher("/ListaADM.jsp");
                mostrar.forward(request, response);
                break;

            case "incluir":
                
                adminAtributo = new Usuario();
                adminAtributo.setId(0);
                request.setAttribute("adminAtributo", adminAtributo);
                RequestDispatcher incluir = request.getRequestDispatcher("/formadm.jsp");
                incluir.forward(request, response);
                break;

            case "editar":
                
                int id;
                id = Integer.parseInt(request.getParameter("id"));
                try
                {
                    adminAtributo = adminController.getUsuarioPorID(id);
                    request.setAttribute("adminAtributo", adminAtributo);
                    RequestDispatcher rs = request.getRequestDispatcher("/formadm.jsp");
                    rs.forward(request, response);
                }
                catch (Exception e)
                {
                    mensagem = "Erro ao editar!";
                    request.setAttribute("mensagem", mensagem);
                    request.setAttribute("servletDeRetorno", servletDeRetorno);
                    RequestDispatcher rd = request.getRequestDispatcher("/Mensagem.jsp");
                    rd.forward(request, response);
                }                
                break;

            case "excluir":

                id = Integer.parseInt(request.getParameter("id"));
                adminController.excluir(id);

                ArrayList<Usuario> admListaAtualizada;
                admListaAtualizada = adminController.getLista();
                request.setAttribute("admLista", admListaAtualizada);
                RequestDispatcher mostrarAtualizado = getServletContext().getRequestDispatcher("/ListaADM.jsp");
                mostrarAtualizado.forward(request, response);
                break;
                
            default:
                mensagem = "Erro!";
                request.setAttribute("mensagem", mensagem);
                request.setAttribute("servletDeRetorno", servletDeRetorno);
                RequestDispatcher rd = request.getRequestDispatcher("/Mensagem.jsp");
                rd.forward(request, response);
                break;
        }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        
        HttpSession sessionUsuario = request.getSession();
        if(sessionUsuario.getAttribute("loginAdmin") == null)
        {
            RequestDispatcher validaLogin = getServletContext().getRequestDispatcher("/processarLogin");
            validaLogin.forward(request, response);
        }
        else if(((Login)sessionUsuario.getAttribute("loginAdmin")).expiraLogin())
        {
            RequestDispatcher validaLogin = getServletContext().getRequestDispatcher("/processarLogin");
            validaLogin.forward(request, response);
        }
        else
        {
            
        Login loginAtualizado = (Login)sessionUsuario.getAttribute("loginAdmin");
        loginAtualizado.atualizaLogin();
        sessionUsuario.setAttribute("loginAdmin", loginAtualizado);
        
        request.setCharacterEncoding("UTF-8");
        Usuario adminAux = new Usuario();
        adminAux.setId(Integer.parseInt(request.getParameter("id")));
        adminAux.setNome(request.getParameter("nome"));
        adminAux.setCpf(request.getParameter("cpf"));
        adminAux.setSenha(request.getParameter("senha"));
        adminAux.setTipo("administrador");

        AdminDAO adminGrava = new AdminDAO();
        String mensagem;
        String servletDeRetorno = "ControllerAdmin?acao=mostrar";
        
        Validador adminValidado = new Validador(adminAux.getNome(), 
                adminAux.getCpf(), adminAux.getSenha());
        
        if(!(adminValidado.validaAdmin()))
        {
            mensagem = adminValidado.getMensagem();
            request.setAttribute("mensagem", mensagem);
            request.setAttribute("servletDeRetorno", servletDeRetorno);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Mensagem.jsp");
            rd.forward(request, response);
            return;
        }
        
        try 
        {            
            if (adminGrava.gravar(adminAux)) 
            {
                mensagem = "Administrador gravado com sucesso!";
            } else 
            {
                mensagem = "Erro ao gravar administrador!";
            }

            request.setAttribute("mensagem", mensagem);
            request.setAttribute("servletDeRetorno", servletDeRetorno);
            RequestDispatcher rd = request.getRequestDispatcher("/Mensagem.jsp");
            rd.forward(request, response);

        } 
        catch (Exception e) 
        {            
            mensagem = "Erro ao gravar usuário!";
            request.setAttribute("mensagem", mensagem);
            request.setAttribute("servletDeRetorno", servletDeRetorno);
            RequestDispatcher rd = request.getRequestDispatcher("/Mensagem.jsp");
            rd.forward(request, response);
        }
        }
    }   

}
