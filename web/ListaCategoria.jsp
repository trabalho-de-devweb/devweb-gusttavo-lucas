<%@page import="aplicacao.Login"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="aplicacao.Categoria" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="cabecalho.html" %>
    </head>
    <body>

        <%
            HttpSession sessionUsuario = request.getSession();
            if(sessionUsuario.getAttribute("loginAdmin") == null)
            {
        %>
                <script>
                    window.location.replace("processarLogin");
                </script>
        <%
            }
            else if(((Login)sessionUsuario.getAttribute("loginAdmin")).expiraLogin())
            {
        %>
                <script>
                    window.location.replace("processarLogin");
                </script>
        <% 
            }
            else
            {
        %>
        
        

            <jsp:include page="MenuADM.jsp" />

                   
            <h1>Lista de Categorias</h1>     
            <p></p>
            <a href="ControllerCategoria?acao=incluir" class="btn btn-outline-primary">Incluir</a>
            <p></p>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th scope="col">Categoria</th>
                            <th scope="col"><div class="float-right">Ações</div><br></th>
                        </tr>
                    </thead> 
                    <tbody>
                        <%
                            ArrayList<Categoria> ListaCategoria = (ArrayList<Categoria>) request.getAttribute("categoriaLista");
                            for (int i = 0; i < ListaCategoria.size(); i++) {
                                Categoria aux = ListaCategoria.get(i);
                                String link_editar = "ControllerCategoria?acao=editar&id="+aux.getId();
                                String link_excluir = "ControllerCategoria?acao=excluir&id="+aux.getId();
                        %>
                        <tr>
                            <td><%=aux.getDescricao()%></td>
                            <td><a href="<%=link_excluir%>" onclick="return alerta()" class="btn btn-outline-danger float-right">Excluir</a> <a href="<%=link_editar%>" class="btn btn-outline-secondary float-right">Editar</a> 
                            </td> 
                           
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        
        <%@include file="scriptsBasicos.html" %>
        
        <script>
        function alerta()
        {
            var verificacao = confirm("Tem certeza que deseja excluir?");
            if(verificacao)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        </script>
        
        <%
        }
        %>
        
    </body>
</html>
