<%@page import="aplicacao.Conta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="pt-br">
    
    <head>
        <%@include file="cabecalho.html" %>
        <title>Formulário de Contas</title>
    </head>
    
    <body class="bg-light">
        
        <div class="container-fluid">
            <div class="row justify-content-center">
                <div class="col-md-4">
                    <div class="py-4 text-center">
                        <h2>Formulário de Contas</h2>
                    </div>
                    <form method="POST" action="ControllerConta" accept-charset="utf-8" onsubmit="return isNome()">
                        <input type="hidden" name="id" id="id" value="<%= ((Conta) request.getAttribute("contaAtributo")).getId() %>">
                        <div class="form-group">
                            <label for="nomeContaCorrente">Nome:</label>
                            <input required type="text" class="form-control" id="nomeContaCorrente" name="nome" value="<%= ((Conta)request.getAttribute("contaAtributo")).getNome() %>">
                        </div>
                        <div class="form-group">
                            <label for="numeroBanco">Número do banco:</label>
                            <input required type="text" class="form-control" id="numeroBanco" name="numBanco" value="<%= ((Conta)request.getAttribute("contaAtributo")).getNumBanco() %>">
                            <small id="numeroBancoHelp" class="form-text text-muted">Exemplo: 001 – Banco do Brasil, 341 – Itaú, etc.</small>
                        </div>
                        <div class="form-group">
                            <label for="numeroAgencia">Agência:</label>
                            <input required type="text" class="form-control" id="numeroAgencia" name="numAgencia" value="<%= ((Conta)request.getAttribute("contaAtributo")).getNumAgencia() %>">
                        </div>
                        <div class="form-group">
                            <label for="numeroContaCorrente">Conta corrente:</label>
                            <input required type="text" class="form-control" id="numeroContaCorrente" name="numContaCorrente" value="<%= ((Conta)request.getAttribute("contaAtributo")).getNumContaCorrente() %>">
                        </div>
                        <input type="submit" class="btn btn-primary btn-block my-4" value="Enviar">
                    </form>
                </div>
            </div>
        </div>

        <%@include file="scriptsBasicos.html" %>
        
        <script type="text/javascript">
        
                function isNome()
                {
                    let arrChar = document.getElementById("nomeContaCorrente").textContent.split();
                    let charValidos = "ÀÁÁÂÃÈÉÊÌÍÎÒÓÔÕÙÚÛàáâãèéêìíîòóôõùúûçÇ";
                    let ch;
                    alert('entrei no isNome');
                    for (int i = 0; i < arrChar.length; i++) 
                    {
                        ch = arrChar[i];
                        if(ch !== ' ')
                        {
                           if (!((ch >= 'a' && ch <= 'z') || ((ch >= 'A' && ch <= 'Z')) || (charValidos.indexOf(ch) !== -1))) 
                           {
                                alert("dados invalidos");
                                return false;
                           }
                        }
                    }
                    
                    return true;
                }
                
        </script>
        
    </body>
    
</html>