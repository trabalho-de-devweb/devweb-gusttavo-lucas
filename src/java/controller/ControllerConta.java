package controller;

import aplicacao.Conta;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ContaDAO;

@WebServlet(name = "ControllerConta", urlPatterns = {"/ControllerConta"})
public class ControllerConta extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        
        String mensagem;
        String servletDeRetorno = "ControllerConta?acao=mostrar";
        ContaDAO contaController = new ContaDAO();
        String acao = (String) request.getParameter("acao");
        Conta contaAtributo;
        int id;

        switch (acao) {
            case "mostrar":
                ArrayList<Conta> contaLista;
                contaLista = contaController.getLista();
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
                
                id = Integer.parseInt(request.getParameter("id"));
                try
                {
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
                contaListaAtualizada = contaController.getLista();
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
        Conta contaAux = new Conta();
        contaAux.setId(Integer.parseInt(request.getParameter("id")));
        contaAux.setIdUsuario(Integer.parseInt(request.getParameter("idUsuario")));
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
    

