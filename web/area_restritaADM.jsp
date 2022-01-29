<%@page import="aplicacao.Login"%>
<%@page import="aplicacao.Usuario"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <%@include file="cabecalho.html" %>
        <title>Área Restrita do Administrador</title>
    </head>
    <body class="bg-light">
        
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
        %>
        
        <% 
            HttpSession admSessao = request.getSession();
            Usuario admLogado = ((Login)admSessao.getAttribute("loginAdmin")).getUsuario();
        
        %>
        
        <%@include file="MenuADM.jsp" %>
        <div class="jumbotron">
            <h1 class="display-4">Bem vindo(a), <%=admLogado.getNome()%>!</h1>
            <hr class="my-4">
            <h2>Nome: <%=admLogado.getNome()%> <span class="badge bg-primary">Administrador</span></h2>
            <h2>CPF: <%=admLogado.getCpf()%></h2>
        </div>
        <%@include file="scriptsBasicos.html" %>
    </body>
</html>
