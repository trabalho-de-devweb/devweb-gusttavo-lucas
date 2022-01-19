<%@page import="aplicacao.Login"%>
<%@page import="aplicacao.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="pt-br">
    
    <head>
        <%@include file="cabecalho.html" %>
        <title>Formulário de Usuário</title>
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
                        <h2>Formulário de Usuário</h2>
                    </div>            
                    <form method="POST" action="ControllerUsuario" onsubmit="return validaFormUsuarios()">
                        <input type="hidden" name="id" id="id" value="<%= ((Usuario)request.getAttribute("usuarioAtributo")).getId() %>">
                        <div class="form-group">
                            <label for="nome">Nome:</label>
                            <input type="text" class="form-control" name="nome" id="nome" value="<%= ((Usuario)request.getAttribute("usuarioAtributo")).getNome() %>">
                        </div>
                        <div class="form-group">
                            <label for="cpf">CPF:</label>
                            <input type="text" class="form-control" name="cpf" id="cpf" value="<%= ((Usuario)request.getAttribute("usuarioAtributo")).getCpf() %>">
                        </div>
                        <div class="form-group">
                            <label for="senha">Senha:</label>
                            <input type="password" class="form-control" name="senha" id="senha">
                        </div>
                        <div class="form-group">
                            <label for="suspenso">Suspenso:</label>
                            <input type="text" class="form-control" name="suspenso" id="suspenso" value="<%= ((Usuario)request.getAttribute("usuarioAtributo")).getStatus() %>">
                        </div>
                        <button type="submit" class="btn btn-primary btn-block my-4">Enviar</button>
                    </form>
                </div>
            </div>
        </div>
        
        <script type="text/javascript">
            function validaFormUsuarios() {
                if (alertaCPF() && validaSenha()) {
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

                if (strCPF === "00000000000") return false;

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
                    document.getElementById('cpf').focus();
                    return false;
                }
            }
            
        function validaSenha() {
            const senha = document.getElementById('senha').value;
            const caracteresEspeciais = [',', ';', '=', '/', '"', "(", ")"];
            const valor = caracteresEspeciais.some(el => senha.includes(el));
            
            if (valor) {
                alert('Senha inválida!');
                document.getElementById('senha').focus();
            }
            
            return !valor;
        }
        </script>
        
        <%@include file="scriptsBasicos.html" %>
        <script>
            $(document).ready(function(){           
               $('#cpf').mask('000.000.000-00', {reverse: true});
            });
        </script>
                
        <%
        }
        %>
                
    </body>
    
</html>