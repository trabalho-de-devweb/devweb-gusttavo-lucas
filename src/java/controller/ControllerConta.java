package controller;

import aplicacao.PesquisaNaoEncontradaException;
import aplicacao.Conta;
import aplicacao.Usuario;
import aplicacao.Login;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ContaDAO;
import model.UsuarioDAO;

@WebServlet(name = "ControllerConta", urlPatterns = {"/ControllerConta"})
public class ControllerConta extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        
        String mensagem;
        String servletDeRetorno = "ControllerConta?acao=mostrar";
        ContaDAO contaController = new ContaDAO();
        UsuarioDAO usuarioController = new UsuarioDAO();
        String acao = (String) request.getParameter("acao");
        ArrayList<Conta> contaLista;
        Conta contaAtributo;
        int id;

        switch (acao) {
            case "mostrar":
                Login usuarioLogin = (Login) request.getSession().getAttribute("loginUsuario");
                int idUsuarioLogado = usuarioLogin.getUsuario().getId();
                contaLista = contaController.getLista(idUsuarioLogado);
                request.setAttribute("contaLista", contaLista);
                RequestDispatcher mostrar = getServletContext().getRequestDispatcher("/ListaConta.jsp");
                mostrar.forward(request, response);
                break;
                
            case "incluir":
                
                contaAtributo = new Conta();
                contaAtributo.setId(0);
                request.setAttribute("contaAtributo", contaAtributo);
                RequestDispatcher incluir = request.getRequestDispatcher("/formcontas.jsp");
                incluir.forward(request, response);
                break;

            case "editar":
                
                try
                {
                    id = Integer.parseInt(request.getParameter("id"));
                    
                    contaAtributo = contaController.getContaPorID(id);
                    request.setAttribute("contaAtributo", contaAtributo);
                    RequestDispatcher rs = request.getRequestDispatcher("/formcontas.jsp");
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
                    contaController.excluir(id);
                }
                catch( Exception e)
                {
                    mensagem = "Erro! Usuário tem contas cadastradas no sistema, necessária a previa exclusão das mesmas.";
                    request.setAttribute("mensagem", mensagem);
                    request.setAttribute("servletDeRetorno", servletDeRetorno);
                    RequestDispatcher rd = request.getRequestDispatcher("/Mensagem.jsp");
                    rd.forward(request, response);
                }                

                ArrayList<Conta> contaListaAtualizada;
                contaListaAtualizada = contaController.getLista(id);
                request.setAttribute("contaLista", contaListaAtualizada);
                RequestDispatcher mostrarAtualizado = getServletContext().getRequestDispatcher("/ListaConta.jsp");
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
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
                
        HttpSession sessionConta = request.getSession();
        Login loginUsuario = (Login) sessionConta.getAttribute("loginUsuario");
        Conta contaAux = new Conta();
        contaAux.setId(Integer.parseInt(request.getParameter("id")));
        contaAux.setIdUsuario(loginUsuario.getUsuario().getId());
        contaAux.setNome(request.getParameter("nome"));
        contaAux.setNumBanco(request.getParameter("numBanco"));
        contaAux.setNumAgencia(request.getParameter("numAgencia"));
        contaAux.setNumContaCorrente(request.getParameter("numContaCorrente"));

        ContaDAO contaGrava = new ContaDAO();
        String mensagem;
        String servletDeRetorno = "ControllerConta?acao=mostrar";
        
        if(!(contaAux.validaConta()))
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
            if (contaGrava.gravar(contaAux)) 
            {
                mensagem = "Conta cadastrada com sucesso!";
            } else 
            {
                mensagem = "Erro ao cadastrar conta!";
            }

            request.setAttribute("mensagem", mensagem);
            request.setAttribute("servletDeRetorno", servletDeRetorno);
            RequestDispatcher rd = request.getRequestDispatcher("/Mensagem.jsp");
            rd.forward(request, response);

        } 
        catch (Exception e) 
        {            
            mensagem = "Erro ao gravar conta!";
            request.setAttribute("mensagem", mensagem);
            request.setAttribute("servletDeRetorno", servletDeRetorno);
            RequestDispatcher rd = request.getRequestDispatcher("/Mensagem.jsp");
            rd.forward(request, response);
        }
        
        }
    
    }
    

