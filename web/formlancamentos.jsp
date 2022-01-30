<%@page import="aplicacao.Lancamento"%>
<%@page import="java.util.ArrayList"%>
<%@page import="aplicacao.Categoria"%>
<%@page import="aplicacao.Login"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="pt-br">
    
    <head>
        <%@include file="cabecalho.html" %>
        <title>Formulário de Lançamentos</title>
    </head>
    
    <body class="bg-light">
        
        <%
            HttpSession sessionUsuario = request.getSession();
            if(sessionUsuario.getAttribute("loginUsuario") == null)
            {
        %>
                <script>
                    window.location.replace("processarLogin");
                </script>
        <%
            }
            else if(((Login)sessionUsuario.getAttribute("loginUsuario")).expiraLogin())
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
                        <h2>Formulário de Lançamentos</h2>
                    </div>
                    <form method="POST" action="ControllerLancamento" onsubmit="return validaLancamento()">                        
                        <div class="form-group">
                            <input type="hidden" name="id" id="id" value="<%= ((Lancamento) request.getAttribute("lancamentoAtributo")).getId() %>">
                            <input type="hidden" name="idConta" id="idConta" value="<%=request.getParameter("idConta")%>">
                            <label for="descricaoCategoria">Categoria:</label>
                            <select required class="custom-select" id="descricaoCategoria" name="descricaoCategoria">
                                <option>Escolha...</option>
                                <%
                                    ArrayList<Categoria> listaCategorias = (ArrayList<Categoria>) request.getAttribute("listaCategorias");
                                    String descricaoCatAux;
                                    for (int i = 0; i < listaCategorias.size(); i++) 
                                    {
                                        descricaoCatAux = listaCategorias.get(i).getDescricao();                                    
                                %>
                                <option value="<%=descricaoCatAux%>"><%=descricaoCatAux%></option>
                                <%
                                    }
                                %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="valor">Valor:</label>
                            <input required type="text" class="form-control money2" id="valor" name="valor" value="<%= ((Lancamento) request.getAttribute("lancamentoAtributo")).getValorPonto() %>">
                        </div>
                        <div class="form-group">
                            <label for="operacao">Operação:</label>
                            <select required class="custom-select" id="operacao" name="operacao">
                                <option>Escolha...</option>
                                <option value="C">Crédito</option>
                                <option value="D">Débito</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="data">Data:</label>
                            <input required type="date" class="form-control" id="data" name="data" value="<%= ((Lancamento) request.getAttribute("lancamentoAtributo")).getDataInverte() %>">
                        </div>
                        <div class="form-group">
                            <label for="descricao">Descrição:</label>
                            <input type="text" class="form-control" id="descricao" name="descricao" value="<%= ((Lancamento) request.getAttribute("lancamentoAtributo")).getDescricao() %>">
                        </div>
                        <input type="submit" class="btn btn-primary btn-block my-4" value="Enviar">
                    </form>
                </div>
            </div>
        </div>
        
        <%@include file="scriptsBasicos.html" %>
        
        <script type="text/javascript">
            function validaLancamento(){
                if (isValor() && isAnoValido() && isNome()) {
                    return true;
                } else {
                    return false;
                }
            }
    
            function isValor()
            {
                const valor = document.getElementById("valor").value;                
                if (valor <= 0 || valor > 99999999.99) 
                {
                    alert("Valor inserido deve ser maior que 0 e menor que 100000000.00 com apenas 2 digitos decimais de precisão.");
                    return false;
                }                
                return true;
            }
            
            function isAnoValido(){
                const data = document.getElementById("data").value;
                const dataInt = parseInt(data.slice(0, 4));
                
                if (dataInt < 1900 || dataInt > 2100)
                {
                    alert("Data deve estar entre 01/01/1900 e 31/12/2100.");
                    return false;
                }                
                return true;
            }
            
            function isNome()
            {
                let arrChar = document.getElementById("descricao").value.split("");
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
        
        </script>
        
        <script>
            
            $(document).ready(function()
            {
                $('#valor').mask('00000000.00', {reverse: true});
            });
            
            var elementoSelect = document.getElementById("descricaoCategoria");            
            var teste = true;
            for(let i = 0; i < elementoSelect.children.length; i++)
            {
                if(elementoSelect.children[i].value === "<%= ((Lancamento) request.getAttribute("lancamentoAtributo")).getCategoriaDescricao()%>")
                {
                    elementoSelect.children[i].setAttribute("selected", "selected");
                    teste = false;
                }
            };
            if(teste)
            {
                elementoSelect.children[0].setAttribute("selected", "selected");
            }

            elementoSelect = document.getElementById("operacao");
            teste = true;            
            for(let i = 0; i < elementoSelect.children.length; i++)
            {
                if(elementoSelect.children[i].value === "<%=((Lancamento) request.getAttribute("lancamentoAtributo")).getOperacao()%>")
                {
                    elementoSelect.children[i].setAttribute("selected", "selected");
                    teste = false;
                }
            }
            if(teste)
            {
                elementoSelect.children[0].setAttribute("selected", "selected");
            }
            
        </script>
        
        
        <%
        }
        %>
        
    </body>
    
</html>