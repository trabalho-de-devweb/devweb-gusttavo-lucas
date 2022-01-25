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
                    <form method="POST" action="ControllerLancamento">                        
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
                            <input required type="text" class="form-control money2" id="valor" name="valor" value="<%= ((Lancamento) request.getAttribute("lancamentoAtributo")).getValor() %>">
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
                            <input required type="date" class="form-control" id="data" name="data">
                        </div>
                        <div class="form-group">
                            <label for="descricao">Descrição:</label>
                            <input type="text" class="form-control" id="descricao" name="descricao">
                        </div>
                        <input type="submit" class="btn btn-primary btn-block my-4" value="Enviar">
                    </form>
                </div>
            </div>
        </div>
        
        <%@include file="scriptsBasicos.html" %>
        
        <script>
            $(document).ready(function()
            {
                $('#valor').mask('0000000000.00', {reverse: true});
                
                var elementoSelect = document.getElementById("descricaoCategoria");
                var teste = true;
                for(let i = 0; i < elementoSelect.children.length; i++)
                {
                    if(myElement.children[i].value === "<%= ((Lancamento) request.getAttribute("lancamentoAtributo")).getOperacao()%>")
                    {
                        myElement.children[i].setAttribute("selected", "selected");
                        teste = false;
                    }
                }
                if(teste)
                {
                    myElement.children[0].setAttribute("selected", "selected");
                }
                
                elementoSelect = document.getElementById("operacao");
                teste = true;
                for(let i = 0; i < elementoSelect.children.length; i++)
                {
                    if(myElement.children[i].value === "<%=((Lancamento) request.getAttribute("lancamentoAtributo")).getCategoriaDescricao()%>")
                    {
                        myElement.children[i].setAttribute("selected", "selected");
                        teste = false;
                    }
                }
                if(teste)
                {
                    myElement.children[0].setAttribute("selected", "selected");
                }
            });
            
            
            
        </script>
        
        
        <%
        }
        %>
        
    </body>
    
</html>