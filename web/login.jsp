<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>

<html lang="pt-br">
    
    <head>
        <%@include file="cabecalho.html" %>
        <title>Login</title>
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
                      <li class="nav-item">
                        <a class="nav-link" href="index.jsp">Home</a>
                      </li>
                      <li class="nav-item active">
                        <a class="nav-link" href="#">Login <span class="sr-only">(current)</span></a>
                      </li>
                    </ul>
                </div>
            </nav>
            
            <div class="row justify-content-center">
                <div class="col-md-4">
                    <div class="py-4 text-center">
                        <h2>Login</h2>
                    </div>            
                    <form method="POST" action="processarLogin" onsubmit="return validaLogin()">
                        <div class="form-group">
                            <label for="cpf">CPF: </label>
                            <input required type="text" class="form-control" required name="cpf" id="cpf">
                        </div>
                        <div class="form-group">
                            <label for="senha">Senha: </label>
                            <input required type="password" class="form-control" required name="senha" id="senha">
                        </div>
                        <div class="form-check form-check-inline">
                            <input required class="form-check-input" type="radio" id="admin" name="login" value="admin">
                            <label for="admin" class="form-check-label">Administrador</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input required class="form-check-input" type="radio" id="user" name="login" value="user">
                            <label for="user" class="form-check-label">Usuário</label>
                        </div>
                        <input type="submit" class="btn btn-primary btn-block my-4" value="Entrar">
                    </form>
                </div>
            </div>
        </div>

        <%@include file="scriptsBasicos.html"%>        
        <script>
            $(document).ready(function() {
                $('#cpf').mask('000.000.000-00', {reverse: true});
            });
        </script>
        <script type="text/javascript">
            function validaLogin() {
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
            
            function alertaCPF()
            {
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
            
        function validaSenha() 
        {            
            var senha = document.getElementById('senha').value;
            const caracteresEspeciais = [',', ';', '=', '/', '"', "(", ")"];
            var valor = caracteresEspeciais.some(el => senha.includes(el));            
            if(senha.length > 255)
            {
                valor = true;
            }
            
            if (valor) 
            {
                alert('Senha inválida!');
                document.getElementById('senha').focus();
            }
            
            return !valor;
        }
        </script>

    </body>
    
</html>