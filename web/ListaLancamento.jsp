<%@page import="aplicacao.Lancamento"%>
<%@page import="aplicacao.Login"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>

<html>
    <head>
        <%@include file="cabecalho.html" %>
        <title>Listagem de Lançamentos</title>
    </head>
    <body>
        
        <%
            HttpSession sessionUsuario = request.getSession();
            if(sessionUsuario.getAttribute("loginUsuario") == null)
            {
        %>
                <script>
                    window.location.replace("processarLogin");
                </script>
        <%
            }
            else if(((Login)sessionUsuario.getAttribute("loginUsuario")).expiraLogin())
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
   
            <jsp:include page="MenuUSER.jsp" />
                  
            <h1>Lista de Lançamentos</h1>     
            <p></p>
            <%
                String linkincluir = "ControllerLancamento?acao=incluir&idConta="
                                        + request.getParameter("idConta");
            %>
            <a href="<%=linkincluir%>" class="btn btn-outline-primary">Incluir</a>
            <p></p>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th scope="col">Categoria</th>
                            <th scope="col">Valor</th>
                            <th scope="col">Operação</th>
                            <th scope="col">Data</th>
                            <th scope="col">Descrição</th>
                            <th scope="col"><div class="float-right">Ações</div><br></th>
                        </tr>
                    </thead> 
                    <tbody>
                        <%
                            ArrayList<Lancamento> ListaLancamento = (ArrayList<Lancamento>) request.getAttribute("lancamentoLista"); %>
                            <script type="text/javascript">let status = ""</script>
                            <%
                            for (int i = 0; i < ListaLancamento.size(); i++) {
                                
                                Lancamento aux = ListaLancamento.get(i);
                                String link_editar = "ControllerLancamento?acao=editar&id="+aux.getId()+"&idConta="+request.getParameter("idConta");
                                String link_excluir = "ControllerLancamento?acao=excluir&id="+aux.getId()+"&idConta="+request.getParameter("idConta");%>
                            
                        <tr>
                            <td><%=aux.getCategoriaDescricao()%></td>
                            <td><%=aux.getValor()%></td> 
                            <td><%=aux.getOperacao()%></td>
                            <td><%=aux.getData()%></td>
                            <td><%=aux.getDescricao()%></td>
                            <td>
                                <a href="<%=link_excluir%>" onclick="return alerta()" class="btn btn-outline-danger float-right">Excluir</a> 
                                <a href="<%=link_editar%>" class="btn btn-outline-secondary float-right">Editar</a>
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

