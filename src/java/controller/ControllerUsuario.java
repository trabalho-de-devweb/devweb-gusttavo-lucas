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
import model.UsuarioDAO;

@WebServlet(name = "ControllerUsuario", urlPatterns = {"/ControllerUsuario"})
public class ControllerUsuario extends HttpServlet {

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
        
        String mensagem;
        String servletDeRetorno = "ControllerUsuario?acao=mostrar";
        UsuarioDAO usuarioController = new UsuarioDAO();
        String acao = (String) request.getParameter("acao");
        Usuario usuarioAtributo;
        int id;

        switch (acao) {
            case "mostrar":
                ArrayList<Usuario> usuarioLista;
                usuarioLista = usuarioController.getLista();
                request.setAttribute("usuarioLista", usuarioLista);
                RequestDispatcher mostrar = getServletContext().getRequestDispatcher("/ListaUsuario.jsp");
                mostrar.forward(request, response);
                break;
            
            case "ativar":
                
                id = Integer.parseInt(request.getParameter("id"));
                try
                {
                    usuarioAtributo = usuarioController.getUsuarioPorID(id);
                    usuarioAtributo.ativaUsuario();
                    usuarioController.gravar(usuarioAtributo);
                    
                    ArrayList<Usuario> usuarioListaAtualizada;
                    usuarioListaAtualizada = usuarioController.getLista();
                    request.setAttribute("usuarioLista", usuarioListaAtualizada);
                    RequestDispatcher mostrarAtualizado = getServletContext().getRequestDispatcher("/ListaUsuario.jsp");
                    mostrarAtualizado.forward(request, response);                    
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
                
            case "suspender":
                
                id = Integer.parseInt(request.getParameter("id"));
                try
                {
                    usuarioAtributo = usuarioController.getUsuarioPorID(id);
                    usuarioAtributo.suspendeUsuario();
                    usuarioController.gravar(usuarioAtributo);
                    
                    ArrayList<Usuario> usuarioListaAtualizada;
                    usuarioListaAtualizada = usuarioController.getLista();
                    request.setAttribute("usuarioLista", usuarioListaAtualizada);
                    RequestDispatcher mostrarAtualizado = getServletContext().getRequestDispatcher("/ListaUsuario.jsp");
                    mostrarAtualizado.forward(request, response); 
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
                
            case "incluir":
                
                usuarioAtributo = new Usuario();
                usuarioAtributo.setId(0);
                request.setAttribute("usuarioAtributo", usuarioAtributo);
                RequestDispatcher incluir = request.getRequestDispatcher("/formusuarios.jsp");
                incluir.forward(request, response);
                break;

            case "editar":
                
                id = Integer.parseInt(request.getParameter("id"));
                try
                {
                    usuarioAtributo = usuarioController.getUsuarioPorID(id);
                    request.setAttribute("usuarioAtributo", usuarioAtributo);
                    RequestDispatcher rs = request.getRequestDispatcher("/formusuarios.jsp");
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
                try
                {
                    usuarioController.excluir(id);
                }
                catch( Exception e)
                {
                    mensagem = "Erro! Usuário tem contas cadastradas no sistema, necessária a previa exclusão das mesmas.";
                    request.setAttribute("mensagem", mensagem);
                    request.setAttribute("servletDeRetorno", servletDeRetorno);
                    RequestDispatcher rd = request.getRequestDispatcher("/Mensagem.jsp");
                    rd.forward(request, response);
                }                

                ArrayList<Usuario> usuarioListaAtualizada;
                usuarioListaAtualizada = usuarioController.getLista();
                request.setAttribute("usuarioLista", usuarioListaAtualizada);
                RequestDispatcher mostrarAtualizado = getServletContext().getRequestDispatcher("/ListaUsuario.jsp");
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
        
        
        Usuario usuarioAux = new Usuario();
        usuarioAux.setId(Integer.parseInt(request.getParameter("id")));
        usuarioAux.setNome(request.getParameter("nome"));
        usuarioAux.setCpf(request.getParameter("cpf"));
        usuarioAux.setSenha(request.getParameter("senha"));
        usuarioAux.setSuspenso(request.getParameter("suspenso"));
        usuarioAux.setTipo("correntista");

        UsuarioDAO UsuarioGrava = new UsuarioDAO();
        String mensagem;
        String servletDeRetorno = "ControllerUsuario?acao=mostrar";
        
        Validador usuarioValidado = new Validador(usuarioAux.getNome(), 
                usuarioAux.getCpf(), usuarioAux.getSenha(), usuarioAux.getStatus());
        
        if(!(usuarioValidado.validaUsuario()))
        {
            mensagem = "Erro!Dados inválidos.";
            request.setAttribute("mensagem", mensagem);
            request.setAttribute("servletDeRetorno", servletDeRetorno);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Mensagem.jsp");
            rd.forward(request, response);
            return;
        }
        
        try 
        {            
            if (UsuarioGrava.gravar(usuarioAux)) 
            {
                mensagem = "Usuário gravado com sucesso!";
            } else 
            {
                mensagem = "Erro ao gravar usuário!";
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
