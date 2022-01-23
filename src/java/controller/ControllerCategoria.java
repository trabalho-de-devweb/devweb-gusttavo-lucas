package controller;

import aplicacao.Categoria;
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

@WebServlet(name = "ControllerCategoria", urlPatterns = {"/ControllerCategoria"})
public class ControllerCategoria extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
        String servletDeRetorno = "ControllerCategoria?acao=mostrar";
        CategoriaDAO categoriaController = new CategoriaDAO();
        String acao = (String) request.getParameter("acao");
        Categoria categoriaAtributo;

        switch (acao) {
            case "mostrar":
                ArrayList<Categoria> categoriaLista;
                categoriaLista = categoriaController.getLista();
                request.setAttribute("categoriaLista", categoriaLista);
                RequestDispatcher mostrar = getServletContext().getRequestDispatcher("/ListaCategoria.jsp");
                mostrar.forward(request, response);
                break;

            case "incluir":
                
                categoriaAtributo = new Categoria();
                request.setAttribute("categoriaAtributo", categoriaAtributo);
                RequestDispatcher incluir = request.getRequestDispatcher("/formcategorias.jsp");
                incluir.forward(request, response);
                break;

            case "editar":
                
                int id;
                id = Integer.parseInt(request.getParameter("id"));
                try
                {
                    categoriaAtributo = categoriaController.getCategoriaPorID(id);
                    request.setAttribute("categoriaAtributo", categoriaAtributo);
                    RequestDispatcher rs = request.getRequestDispatcher("/formcategorias.jsp");
                    rs.forward(request, response);
                }
                catch (Exception e)
                {
                    mensagem = "Erro ao editar!";
                    request.setAttribute("mensagem", mensagem);
                    request.setAttribute("servletDeRetorno", servletDeRetorno);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Mensagem.jsp");
                    rd.forward(request, response);
                }                
                break;

            case "excluir":

                id = Integer.parseInt(request.getParameter("id"));
                try
                {
                    categoriaController.excluir(id);
                }
                catch( Exception e)
                {
                    mensagem = "Erro! Lançamentos encontrados com essa categoria, necessária a previa exclusão dos mesmos.";
                    request.setAttribute("mensagem", mensagem);
                    request.setAttribute("servletDeRetorno", servletDeRetorno);
                    RequestDispatcher rd = request.getRequestDispatcher("/Mensagem.jsp");
                    rd.forward(request, response);
                }
                
                ArrayList<Categoria> categoriaListaAtualizada;
                categoriaListaAtualizada = categoriaController.getLista();
                request.setAttribute("categoriaLista", categoriaListaAtualizada);
                RequestDispatcher mostrarAtualizado = getServletContext().getRequestDispatcher("/ListaCategoria.jsp");
                mostrarAtualizado.forward(request, response);
                break;
                
            default:
                mensagem = "Erro!";
                request.setAttribute("mensagem", mensagem);
                request.setAttribute("servletDeRetorno", servletDeRetorno);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/Mensagem.jsp");
                rd.forward(request, response);
                break;
        }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
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
        
        request.setCharacterEncoding("UTF-8");
        Categoria categoriaAux = new Categoria();        
        categoriaAux.setId(Integer.parseInt(request.getParameter("id")));
        categoriaAux.setDescricao(request.getParameter("descricao"));

        CategoriaDAO categoriaGrava = new CategoriaDAO();
        String mensagem;
        String servletDeRetorno = "ControllerCategoria?acao=mostrar";
        
        Validador categoriaValidado = new Validador(categoriaAux.getDescricao());
        
        if(!(categoriaValidado.validaCategoria()))
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
            if (categoriaGrava.gravar(categoriaAux)) {
                mensagem = "Categoria gravada com sucesso!";
            } else {
                mensagem = "Erro ao gravar categoria!";
            }

            request.setAttribute("mensagem", mensagem);
            request.setAttribute("servletDeRetorno", servletDeRetorno);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Mensagem.jsp");
            rd.forward(request, response);

        } 
        catch (Exception e) 
        {
            mensagem = "Erro ao gravar categoria!";
            request.setAttribute("mensagem", mensagem);
            request.setAttribute("servletDeRetorno", servletDeRetorno);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Mensagem.jsp");
            rd.forward(request, response);
        }
        }
    }    

}
