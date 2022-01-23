<%@page import="aplicacao.Login"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>

<html>
    <head>
        <%@include file="cabecalho.html" %>
    </head>
    <body>
        
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
   
            <jsp:include page="MenuUSER.jsp" />
                  
            <h1>Lista de Lançamentos</h1>     
            <p></p>
            <a href="ControllerLancamento?acao=incluir" class="btn btn-outline-primary">Incluir</a>
            <p></p>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th scope="col">Nome</th>
                            <th scope="col">CPF</th>
                            <th scope="col">Senha</th>
                            <th scope="col">Suspenso</th>
                            <th scope="col"><div class="float-right">Ações</div><br></th>
                        </tr>
                    </thead> 
                    <tbody>
                        <%
                            ArrayList<Usuario> ListaUsuario = (ArrayList<Usuario>) request.getAttribute("usuarioLista"); %>
                            <script type="text/javascript">let status = ""</script>
                            <%
                            for (int i = 0; i < ListaUsuario.size(); i++) {
                                
                                Usuario aux = ListaUsuario.get(i);
                                String link_ativar = "ControllerUsuario?acao=ativar&id="+aux.getId();
                                String link_suspender = "ControllerUsuario?acao=suspender&id="+aux.getId();
                                String link_editar = "ControllerUsuario?acao=editar&id="+aux.getId();
                                String link_excluir = "ControllerUsuario?acao=excluir&id="+aux.getId();%>
                                <script type='text/javascript'>
                                    
                                </script>
                         
                        
                        <tr>
                            <td><%=aux.getNome()%></td>
                            <td><%=aux.getCpf()%></td> 
                            <td><%=aux.getSenha()%></td>
                            <td><%=aux.getStatus()%></td> 
                            <td>
                            <a href="<%=link_excluir%>" onclick="return alerta()" class="btn btn-outline-danger float-right">Excluir</a> 
                            <a href="<%=link_editar%>" class="btn btn-outline-secondary float-right">Editar</a>
                            <a href="<%=link_suspender%>" style="" class="btn btn-outline-secondary float-right btnSuspender" id="">Suspender</a>
                            <script type="text/javascript">
                                document.querySelector('.btnSuspender').id = 'botaoSuspender-' + <%=i%>;
                                status = "<%= aux.getStatus() %>";
                                mudaBotaoSuspender(status, <%=i%>, <%=aux.getId()%>);
                            </script>
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

