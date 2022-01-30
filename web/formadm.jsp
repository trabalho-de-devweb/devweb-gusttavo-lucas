<%@page import="aplicacao.Login"%>
<%@page import="aplicacao.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="pt-br">
    
    <head>
        <%@include file="cabecalho.html" %>
        <title>Formulário de Administrador</title>
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
        
        <div class="container-fluid">
            <div class="row justify-content-center">
                <div class="col-md-4">
                    <div class="py-4 text-center">
                        <h2>Formulário de Administrador</h2>
                    </div>            
                    <form method="POST" action="ControllerAdmin" onsubmit="return validaFormAdm()">
                        <input type="hidden" name="id" id="id" value="<%= ((Usuario)request.getAttribute("adminAtributo")).getId() %>">
                        <div class="form-group row">
                            <label class="col-sm-2" for="nome">Nome:</label>
                            <div class="col-sm-10">
                                   <input required type="text" class="form-control" name="nome" id="nome" value="<%= ((Usuario)request.getAttribute("adminAtributo")).getNome() %>">
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-2" for="cpf">CPF:</label>
                            <div class="col-sm-10">
                                   <input required type="text" class="form-control" name="cpf" id="cpf" value="<%= ((Usuario)request.getAttribute("adminAtributo")).getCpf() %>">
                            </div>
                        </div>  

                        <div class="form-group row">
                            <label class="col-sm-2" for="senha">Senha:</label>
                            <div class="col-sm-10">
                                   <input required type="password" class="form-control" name="senha" id="senha">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary btn-block my-4">Enviar</button>
                    </form>
                </div>
            </div>
        </div>
        
        <%@include file="scriptsBasicos.html" %>
        <script>
            $(document).ready(function(){           
               $('#cpf').mask('000.000.000-00', {reverse: true});
            });
        </script>
        
        <script type="text/javascript">
            function validaFormAdm() {
                if (alertaCPF() && validaSenha() && isNome()) {
                    return true;
                } else {
                    return false;
                }
            }
    
            function formataCPF(cpf) {
                return cpfFormatado = cpf.replace('-', '.').split('.').join('');
            }

            function validaCPF() {
                var soma;
                var resto;
                soma = 0;
                
                const strCPF = formataCPF(document.getElementById('cpf').value);

                if (strCPF === "00000000000" || strCPF === "11111111111" || strCPF === "22222222222" ||
                        strCPF === "33333333333" || strCPF ==="44444444444" || strCPF === "55555555555" ||
                        strCPF === "66666666666" || strCPF ==="77777777777" || strCPF === "88888888888" ||
                        strCPF === "99999999999") return false;

                for (i=1; i<=9; i++) soma = soma + parseInt(strCPF.substring(i-1, i)) * (11 - i);
                resto = (soma * 10) % 11;

                if ((resto === 10) || (resto === 11))  resto = 0;
                if (resto !== parseInt(strCPF.substring(9, 10)) ) return false;

                soma = 0;
                for (i = 1; i <= 10; i++) soma = soma + parseInt(strCPF.substring(i-1, i)) * (12 - i);
                resto = (soma * 10) % 11;

                if ((resto === 10) || (resto === 11))  resto = 0;
                if (resto !== parseInt(strCPF.substring(10, 11) ) ) return false;

                return true;
            }
            
            function alertaCPF(){
                const CPFeValido = validaCPF();
                console.log(CPFeValido);
                
                if (CPFeValido) {
                    return true;
                } else {
                    alert('CPF inválido!');
                    return false;
                }
            }
            
            function validaSenha() {
                const senha = document.getElementById('senha').value;
                const caracteresEspeciais = [',', ';', '=', '/', '"', "(", ")"];
                const valor = caracteresEspeciais.some(el => senha.includes(el));

                if (valor) {
                    alert("Erro!Senha não deve conter ',', ';', '=', '\', ''', '(' e ')'.");
                }

                return !valor;
            }
            
            function isNome()
            {
                let arrChar = document.getElementById("nome").value.split("");
                if(arrChar.length > 20)
                {
                    alert("Nome só pode conter letras maiúsculas, minúsculas e com acento no limite de 20 caracteres no total.");
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
                            alert("Nome só pode conter letras maiúsculas, minúsculas e com acento no limite de 20 caracteres no total.");
                            return false;
                       }
                    }
                }

                return true;
            }
        
        
        </script>
        
        <%
        }
        %>
        
    </body>
    
</html>
