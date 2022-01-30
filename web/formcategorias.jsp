<%@page import="aplicacao.Login"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="aplicacao.Categoria" %>
<!DOCTYPE html>

<html lang="pt">
    
    <head>
        <%@include file="cabecalho.html" %>
        <title>Formulário de Categorias</title>
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
            else
            {
        %>
        
        <%
            Categoria aux = (Categoria)request.getAttribute("categoriaAtributo");
        %>
        <div class="container-fluid">
            <div class="row justify-content-center">
                <div class="col-md-4">
                    <div class="py-4 text-center">
                        <h2>Formulário de Categorias</h2>
                    </div>
                    <form method="POST" action="ControllerCategoria" onsubmit="return validaCategoria()">
                        <input type="hidden" class="form-control" name="id" value="<%= aux.getId() %>">
                        
                        <div class="form-group">
                            <label for="descricao">Descrição: </label>
                            <input required type="text" class="form-control" name="descricao" id="descricao" value="<%= aux.getDescricao() %>">
                        </div>
                        <input type="submit" class="btn btn-primary btn-block my-4" value="Enviar">
                    </form>
                </div>
            </div>
        </div>
                        
        <script>
            function validaCategoria(){
                const categoria = document.getElementById("descricao").value;
                if(categoria.length > 20)
                {
                    alert("Erro!Descrição deve conter no máximo 20 caracteres.");
                    return false;
                }
                
                const charArray = categoria.split("");
                const charValidos = "ÀÁÁÂÃÈÉÊÌÍÎÒÓÔÕÙÚÛàáâãèéêìíîòóôõùúûç";
                for (let i = 0; i < charArray.length; i++) 
                {
                   let ch = charArray[i];
                   if(ch !== ' ')
                   {
                       if (!((ch >= 'a' && ch <= 'z') || ((ch >= 'A' && ch <= 'Z')) || (charValidos.indexOf(ch) != -1))) 
                        {
                            alert("Erro!Descrição só pode conter letras maiúsculas, minúsculas e com acento.");
                            return false;
                        }
                   }
                }
                return true;
            }
        </script>                

        <%@include file="scriptsBasicos.html" %>
        
        <%
        }
        %>
        
    </body>
    
</html>