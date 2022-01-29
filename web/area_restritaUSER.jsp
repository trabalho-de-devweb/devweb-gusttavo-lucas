<%@page import="aplicacao.Login"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <%@include file="cabecalho.html" %>
        <title>Área Restrita do Usuário</title>
    </head>
    <body class="bg-light">
        
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
        %>        
        
        <%@include file="MenuUSER.jsp" %>
        
        <% 
            HttpSession usrSessao = request.getSession();
            Usuario usrLogado = ((Login)usrSessao.getAttribute("loginUsuario")).getUsuario();
        
        %>
        
        <div class="jumbotron">
            <h1 class="display-4">Bem vindo(a), <%=usrLogado.getNome()%>!</h1>
            <hr class="my-4">
            <h2>Nome: <%=usrLogado.getNome()%> <span class="badge bg-primary">Usuário</span></h2>
            <h2>CPF: <%=usrLogado.getCpf()%></h2>
        </div>
        
        <%@include file="scriptsBasicos.html" %>
    </body>
</html>
