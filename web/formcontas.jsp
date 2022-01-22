<%@page import="aplicacao.Conta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="pt-br">
    
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css\bootstrap.min.css">
        <title>Formulário de Contas</title>
    </head>
    
    <body class="bg-light">
        
        <div class="container-fluid">
            <div class="row justify-content-center">
                <div class="col-md-4">
                    <div class="py-4 text-center">
                        <h2>Formulário de Contas</h2>
                    </div>
                    <form method="POST" action="ControllerConta">
                        <input type="hidden" name="id" id="id" value="<%= ((Conta) request.getAttribute("contaAtributo")).getId() %>">
                        <div class="form-group">
                            <label for="nomeContaCorrente">Nome:</label>
                            <input type="text" class="form-control" id="nomeContaCorrente" name="nome" value="<%= ((Conta)request.getAttribute("contaAtributo")).getNome() %>">
                        </div>
                        <div class="form-group">
                            <label for="numeroBanco">Número do banco:</label>
                            <input type="text" class="form-control" id="numeroBanco" name="numBanco" value="<%= ((Conta)request.getAttribute("contaAtributo")).getNumBanco() %>">
                            <small id="numeroBancoHelp" class="form-text text-muted">Exemplo: 001 – Banco do Brasil, 341 – Itaú, etc.</small>
                        </div>
                        <div class="form-group">
                            <label for="numeroAgencia">Agência:</label>
                            <input type="text" class="form-control" id="numeroAgencia" name="numAgencia" value="<%= ((Conta)request.getAttribute("contaAtributo")).getNumAgencia() %>">
                        </div>
                        <div class="form-group">
                            <label for="numeroContaCorrente">Conta corrente:</label>
                            <input type="text" class="form-control" id="numeroContaCorrente" name="numContaCorrente" value="<%= ((Conta)request.getAttribute("contaAtributo")).getNumContaCorrente() %>">
                        </div>
                        <input type="submit" class="btn btn-primary btn-block my-4" value="Enviar">
                    </form>
                </div>
            </div>
        </div>

        <%@include file="scriptsBasicos.html" %>
        
    </body>
    
</html>