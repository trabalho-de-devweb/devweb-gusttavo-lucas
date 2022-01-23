<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="aplicacao.Conta" %>
<%@ page import="aplicacao.Usuario" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="cabecalho.html" %>
    </head>
    <body>
        <jsp:include page="MenuUSER.jsp" />

        <h1>Lista de Contas</h1>     
        <p></p>
        <a href="ControllerConta?acao=incluir" class="btn btn-outline-primary">Incluir</a>
        <p></p>
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th scope="col">Nome da Conta</th>
                        <th scope="col">Código do Banco</th>
                        <th scope="col">Agência</th>
                        <th scope="col">Conta Corrente</th>
                        <th scope="col"><div class="float-right">Ações</div><br></th>
                    </tr>
                </thead> 
                <tbody>
                    <%
                        ArrayList<Conta> ListaConta = (ArrayList<Conta>) request.getAttribute("contaLista");
                        for (int i = 0; i < ListaConta.size(); i++) {
                            Conta aux = ListaConta.get(i);
                            String link_editar = "ControllerConta?acao=editar&id="+aux.getId();
                            String link_excluir = "ControllerConta?acao=excluir&id="+aux.getId();
                    %>
                    <tr>
                        <td><%=aux.getNome()%></td>
                        <td><%=aux.getNumBanco()%></td>
                        <td><%=aux.getNumAgencia()%></td>
                        <td><%=aux.getNumContaCorrente()%></td>
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
        
    </body>
</html>
