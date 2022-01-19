<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="pt-br">
    
    <head>        
        <%@include file="cabecalho.html" %>
        <title>Index</title>
    </head>
    
    <body class="bg-light">
        
        <div class="container-fluid">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <a class="navbar-brand" href="#">Banco</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarBanco" aria-controls="navbarBanco" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                
                <div class="collapse navbar-collapse" id="navbarBanco">
                    <ul class="navbar-nav">
                      <li class="nav-item active">
                        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                      </li>
                      <li class="nav-item">
                        <a class="nav-link" href="login.jsp">Login</a>
                      </li>
                    </ul>
                </div>
            </nav>
               
            <jsp:include page="layoutp.jsp" />
        </div>
        
        <script>            
           function framefuncao(texto)
           {
               document.getElementById("tela").setAttribute('style', "visibility:hidden");
               document.getElementById("tela").setAttribute('src', texto);
               document.getElementById("tela").setAttribute('style', "visibility:visible");
           }
        </script>           
         
        <%@include file="scriptsBasicos.html"%>
        
    </body>
    
</html>
