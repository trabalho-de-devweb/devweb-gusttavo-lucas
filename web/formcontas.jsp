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
            
            $(document).ready(function()
            {
                $('#numeroContaCorrente').mask('0000-0', {reverse: true});
            });
        
            function validaConta() 
            {
                if (isNome() && isNumBanco() && isNumAgencia() && isNumConta()) 
                {
                    return true;
                } 
                else 
                {
                    return false;
                }
            }
    
            function isNome()
            {
                let arrChar = document.getElementById("nomeContaCorrente").value.split("");
                if(arrChar.length > 20)
                {
                    alert("Nome deve ter no máximo de 20 caracteres no total.");
                    return false;
                }
                let charValidos = "ÀÁÁÂÃÈÉÊÌÍÎÒÓÔÕÙÚÛàáâãèéêìíîòóôõùúûçÇ";
                let ch;
                for (let i = 0; i < arrChar.length; i++) 
                {
                    ch = arrChar[i];
                    if(ch !== ' ')
                    {
                       if (!((ch >= 'a' && ch <= 'z') || ((ch >= 'A' && ch <= 'Z')) || (charValidos.indexOf(ch) !== -1))) 
                       {
                            alert("Nome só pode conter letras maiúsculas, minúsculas e com acento.");
                            return false;
                       }
                    }
                }

                return true;
            }
                
            function isNumBanco()
            {
                
                const numBanco = document.getElementById("numeroBanco").value;
                var numero;
                if (numBanco.length !== 3) 
                {
                    alert("Número de banco deve ter exatamente 3 digitos!");
                    return false;
                } 
                for (let i = 0; i < numBanco.length; i++)
                {
                    numero = parseInt(numBanco.substring(i, i+1));
                    if (isNaN(numero)) 
                    {
                        alert("Campo de Banco deve conter apenas números!");
                        return false;
                    }
                }
                

                return true;
            }
            
            function isNumAgencia(){
                const numAgencia = document.getElementById("numeroAgencia").value;
                var numero;
                if (numAgencia.length > 6) 
                {
                    alert("Número da agência deve ter menos de 7 digitos!");
                    return false;
                }
                for (let i = 0; i < numAgencia.length; i++)
                {
                    numero = parseInt(numAgencia.substring(i, i+1));
                    if (isNaN(numero)) 
                    {
                        alert("Campo de Agência deve conter apenas números!");
                        return false;
                    }
                }                
                return true;
            }
            
            function isNumConta(){
                const numConta = document.getElementById("numeroContaCorrente").value;
                
                let valorBool = true;
                
                if (numConta.length !== 6) 
                {
                    valorBool = false;
                }
                
                if (numConta.indexOf("-") !== 4){
                    valorBool = false;
                }
                
                const numContaFormat = numConta.replace('-', '');
                
                if (numContaFormat.length !== 5) {
                    valorBool = false;
                } 
                for (let i = 0; i < numContaFormat.length; i++)
                {
                    numero = parseInt(numContaFormat.substring(i, i+1));
                    if (isNaN(numero)) 
                    {
                        valorBool = false;
                    }
                }  
                if(!(valorBool))
                {
                    alert("Número de conta deve ser no formato XXXX-X onde os X devem ser apenas números.");
                }
                return valorBool;
            }
                
        </script>
        
    </body>
    
</html>