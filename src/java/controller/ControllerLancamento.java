package controller;

import aplicacao.Categoria;
import aplicacao.Lancamento;
import aplicacao.Login;
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
import model.CategoriaDAO;
import model.LancamentoDAO;

@WebServlet(name = "ControllerLancamento", urlPatterns = {"/ControllerLancamento"})
public class ControllerLancamento extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
                
        HttpSession sessionUsuario = request.getSession();
        if(sessionUsuario.getAttribute("loginUsuario") == null)
        {
            RequestDispatcher validaLogin = getServletContext().getRequestDispatcher("/processarLogin");
            validaLogin.forward(request, response);
        }
        else if(((Login)sessionUsuario.getAttribute("loginUsuario")).expiraLogin())
        {
            RequestDispatcher validaLogin = getServletContext().getRequestDispatcher("/processarLogin");
            validaLogin.forward(request, response);
        }
        else
        {
        
        String mensagem;
        String servletDeRetorno = "ControllerLancamento?acao=mostrar&idConta=" 
                + request.getParameter("idConta");
        LancamentoDAO lancamentoController = new LancamentoDAO();
        CategoriaDAO categoriaController = new CategoriaDAO();
        String acao = (String) request.getParameter("acao");
        Lancamento lancamentoAtributo;
        int idConta = Integer.parseInt(request.getParameter("idConta"));
        ArrayList<Categoria> listaCategorias;

        switch (acao) 
        {
            case "mostrar":
                ArrayList<Lancamento> lancamentoLista;                
                lancamentoLista = lancamentoController.getLista(idConta);
                request.setAttribute("lancamentoLista", lancamentoLista);
                RequestDispatcher mostrar = getServletContext().getRequestDispatcher("/ListaLancamento.jsp");
                mostrar.forward(request, response);
                break;

            case "incluir":
                
                lancamentoAtributo = new Lancamento();
                lancamentoAtributo.setId(0);
                listaCategorias = categoriaController.getLista();
                request.setAttribute("listaCategorias", listaCategorias);
                request.setAttribute("lancamentoAtributo", lancamentoAtributo);                
                RequestDispatcher incluir = request.getRequestDispatcher("/formlancamentos.jsp");
                incluir.forward(request, response);
                break;

            case "editar":
                
                int id;
                id = Integer.parseInt(request.getParameter("id"));
                try
                {
                    lancamentoAtributo = lancamentoController.getLancamentoPorID(id);
                    listaCategorias = categoriaController.getLista();
                    request.setAttribute("listaCategorias", listaCategorias);
                    request.setAttribute("lancamentoAtributo", lancamentoAtributo);
                    RequestDispatcher rs = request.getRequestDispatcher("/formlancamentos.jsp");
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
                lancamentoController.excluir(id);

                ArrayList<Lancamento> lancamentoListaAtualizada;
                lancamentoListaAtualizada = lancamentoController.getLista(idConta);
                request.setAttribute("lancamentoLista", lancamentoListaAtualizada);
                RequestDispatcher mostrarAtualizado = getServletContext().getRequestDispatcher("/ListaLancamento.jsp");
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
        if(sessionUsuario.getAttribute("loginUsuario") == null)
        {
            RequestDispatcher validaLogin = getServletContext().getRequestDispatcher("/processarLogin");
            validaLogin.forward(request, response);
        }
        else if(((Login)sessionUsuario.getAttribute("loginUsuario")).expiraLogin())
        {
            RequestDispatcher validaLogin = getServletContext().getRequestDispatcher("/processarLogin");
            validaLogin.forward(request, response);
        }
        else
        {
        
        request.setCharacterEncoding("UTF-8");
        Lancamento lancamentoAux = new Lancamento();
        lancamentoAux.setId(Integer.parseInt(request.getParameter("id")));
        lancamentoAux.setCategoriaDescricao(request.getParameter("descricaoCategoria"));
        lancamentoAux.setValor(Float.parseFloat(request.getParameter("valor")));
        lancamentoAux.setOperacao(request.getParameter("operacao"));
        lancamentoAux.setDataInverte(request.getParameter("data"));
        lancamentoAux.setDescricao(request.getParameter("descricao"));

        LancamentoDAO lancamentoGrava = new LancamentoDAO();
        String mensagem;
        String servletDeRetorno = "ControllerLancamento?acao=mostrar&idConta=" 
                + request.getParameter("idConta");
        
        Validador lancamentoValidado = new Validador(lancamentoAux.getDescricao(), lancamentoAux.getValor(), 
                lancamentoAux.getOperacao(), lancamentoAux.getData(), 
                lancamentoAux.getDescricao());
        
        if(!(lancamentoValidado.validaLancamento()))
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
            if (lancamentoGrava.gravar(lancamentoAux, 
                    Integer.parseInt(request.getParameter("idConta")))) 
            {
                mensagem = "Lançamento gravado com sucesso!";
            } else 
            {
                mensagem = "Erro ao gravar lançamento!";
            }

            request.setAttribute("mensagem", mensagem);
            request.setAttribute("servletDeRetorno", servletDeRetorno);
            RequestDispatcher rd = request.getRequestDispatcher("/Mensagem.jsp");
            rd.forward(request, response);

        } 
        catch (Exception e) 
        {            
            mensagem = "Erro ao gravar lançamento!";
            request.setAttribute("mensagem", mensagem);
            request.setAttribute("servletDeRetorno", servletDeRetorno);
            RequestDispatcher rd = request.getRequestDispatcher("/Mensagem.jsp");
            rd.forward(request, response);
        }
        }
    }   

}
