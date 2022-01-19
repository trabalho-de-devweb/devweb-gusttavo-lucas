<%@page import="aplicacao.Login"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="cabecalho.html" %>
    </head>
    <body>
        
        <div class="container mt-2">            

            <div class="col-8 mt-5">

                <div class="alert alert-success" role="alert">
                    <h5>
                        <%= request.getAttribute("mensagem") %>
                    </h5>
                </div>

                <p></p>
                <div><a href="<%= request.getAttribute("servletDeRetorno") %>">Retorna</a></div>
            </div>
        </div>

        <%@include file="scriptsBasicos.html" %>
    </body>
</html>




