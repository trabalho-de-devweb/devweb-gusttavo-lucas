<%@page import="java.math.BigDecimal"%>
<%@page import="aplicacao.Lancamento"%>
<%@page import="aplicacao.Login"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>

<html>
    <head>
        <%@include file="cabecalho.html" %>
        <title>Listagem de Lançamentos</title>
    </head>
    <body>
        
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
   
            <jsp:include page="MenuUSER.jsp" />
                  
            <h1>Lista de Lançamentos</h1>     
            <p></p>
            <%
                String linkincluir = "ControllerLancamento?acao=incluir&idConta="
                                        + request.getParameter("idConta");
                
                BigDecimal credito = new BigDecimal("0");
                BigDecimal debito = new BigDecimal("0");
                ArrayList<Lancamento> ListaLancamento = (ArrayList<Lancamento>) request.getAttribute("lancamentoLista");                
                for (int i = 0; i < ListaLancamento.size(); i++)
                {
                    if((ListaLancamento.get(i).getOperacao()).compareTo("D") == 0)
                    {
                        debito = debito.add(ListaLancamento.get(i).getValorBig());
                    }
                    else if((ListaLancamento.get(i).getOperacao()).compareTo("C") == 0)
                    {
                        credito = credito.add(ListaLancamento.get(i).getValorBig());
                    }
                }                
                BigDecimal saldo = credito.subtract(debito);
            %>
            <div class="container-fluid" style="font-size:2vw">
                <a href="<%=linkincluir%>" class="btn btn-outline-primary">Incluir</a>
                <span class="badge badge-primary">Saldo
                    <span class="badge badge-light"><%=saldo.toString().replace(',', '.')%></span>
                </span>
                <span class="badge badge-success">Total de Créditos
                    <span class="badge badge-light"><%=credito.toString().replace(',', '.')%></span>
                </span>
                <span class="badge badge-danger">Total de Débitos
                    <span class="badge badge-light"><%=debito.toString().replace(',', '.')%></span>
                </span>
            </div>
            <p></p>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th scope="col">Categoria</th>
                            <th scope="col">Valor</th>
                            <th scope="col">Operação</th>
                            <th scope="col">Data</th>
                            <th scope="col">Descrição</th>
                            <th scope="col"><div class="float-right">Ações</div><br></th>
                        </tr>
                    </thead> 
                    <tbody>  
                            <%
                            for (int i = 0; i < ListaLancamento.size(); i++) 
                            {                                
                                Lancamento aux = ListaLancamento.get(i);
                                String link_editar = "ControllerLancamento?acao=editar&id="+aux.getId()+"&idConta="+request.getParameter("idConta");
                                String link_excluir = "ControllerLancamento?acao=excluir&id="+aux.getId()+"&idConta="+request.getParameter("idConta");%>
                            
                        <tr>
                            <td><%=aux.getCategoriaDescricao()%></td>
                            <td><%=aux.getValorPonto()%></td> 
                            <td><%=aux.getOperacao()%></td>
                            <td><%=aux.getData()%></td>
                            <td><%=aux.getDescricao()%></td>
                            <td>
                                <a href="<%=link_excluir%>" onclick="return alerta()" class="btn btn-outline-danger float-right">Excluir</a> 
                                <a href="<%=link_editar%>" class="btn btn-outline-secondary float-right">Editar</a>
                            </td> 
                           
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        
        <%@include file="scriptsBasicos.html" %>
        
        <script>
        function alerta()
        {
            var verificacao = confirm("Tem certeza que deseja excluir?");
            if(verificacao)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        </script>
        
        <%
        }
        %>
             
    </body>
</html>

