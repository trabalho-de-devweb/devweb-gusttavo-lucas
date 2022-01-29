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
                    <form method="POST" action="ControllerConta" accept-charset="utf-8" onsubmit="return validaConta()">
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
        
            function validaConta() 
            {
                if (isNome() && alertaNumBanco() && isNumAgencia() && alertaNumConta()) {
                    return true;
                } else {
                    return false;
                }
            }
    
            function isNome()
            {
                let arrChar = document.getElementById("nomeContaCorrente").value.split("");
                let charValidos = "ÀÁÁÂÃÈÉÊÌÍÎÒÓÔÕÙÚÛàáâãèéêìíîòóôõùúûçÇ";
                let ch;
                for (let i = 0; i < arrChar.length; i++) 
                {
                    ch = arrChar[i];
                    if(ch !== ' ')
                    {
                       if (!((ch >= 'a' && ch <= 'z') || ((ch >= 'A' && ch <= 'Z')) || (charValidos.indexOf(ch) !== -1))) 
                       {
                            alert("Nome não pode conter números.");
                            return false;
                       }
                    }
                }

                return true;
            }
            
            function alertaNumBanco() {
                if (isNumBanco()) {
                    return isNumBanco();
                } else {
                    alert("Número de Banco inválido!");
                    return isNumBanco();
                }
            }
            
            function alertaNumConta(){
                if (isNumConta()){
                    return isNumConta();
                } else {
                    alert("Número de conta deve ser no formato XXXX-X.");
                    return isNumConta();
                }
            }
    
            function isNumBanco()
            {
                
                const numBanco = document.getElementById("numeroBanco").value;
                alert(numBanco);
                if (numBanco.length !== 3) 
                {
                    return false;
                } if (!(Number.isInteger(parseInt(numBanco)))) 
                {
                    return false;
                }

                return true;
            }
            
            function isNumAgencia(){
                const numAgencia = document.getElementById("numeroAgencia").value;
                if (!(Number.isInteger(parseInt(numAgencia)))) {
                    alert("Número de agência inválido!");
                    return false;
                }
                
                return true;
            }
            
            function isNumConta(){
                const numConta = document.getElementById("numeroContaCorrente").value;
                
                let valorBool;
                
                if (numConta.length !== 6) {
                    valorBool = false;
                }
                
                if (numConta.indexOf("-") !== 4){
                    valorBool = false;
                }
                
                const numContaFormat = numConta.replace('-', '');
                
                if (numContaFormat.length !== 5) {
                    valorBool = false;
                } 
                if (!(Number.isInteger(parseInt(numContaFormat)))) {
                    valorBool = false;
                }
                
                return valorBool;
            }
                
        </script>
        
    </body>
    
</html>