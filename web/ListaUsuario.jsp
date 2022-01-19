<%@page import="aplicacao.Login"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="aplicacao.Usuario" %>
<!DOCTYPE html>

<script type="text/javascript">
            const mudaBotaoSuspender = function(status, ind, id) {
                const botaoSuspender = document.getElementById(`botaoSuspender-` + ind);
                console.log(botaoSuspender);
                
                if (status === 'N') {
                    console.log("if");
                    const link_suspender = "ControllerUsuario?acao=suspender&id=" + id;
                    botaoSuspender.setAttribute("class", "btn btn-danger");
                    botaoSuspender.setAttribute("href", link_suspender);
                    botaoSuspender.textContent = "Suspender";
                } else {
                    console.log("else");
                    const link_ativar = "ControllerUsuario?acao=ativar&id=" + id;
                    botaoSuspender.setAttribute("class", "btn btn-success");
                    botaoSuspender.setAttribute("href", link_ativar);
                    botaoSuspender.textContent = "Ativar";
                }
            };
</script>

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

        

            <jsp:include page="MenuADM.jsp" />

                   
            <h1>Lista de Usuarios</h1>     
            <p></p>
            <a href="ControllerUsuario?acao=incluir" class="btn btn-outline-primary">Incluir</a>
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
                            <a href="<%=link_excluir%>" class="btn btn-outline-danger float-right">Excluir</a> 
                            <a href="<%=link_editar%>" class="btn btn-outline-secondary float-right">Editar</a>
                            <a href="<%=link_suspender%>" style="" class="btn btn-outline-secondary float-right btnSuspender" id="">Suspender</a>
                            <script type="text/javascript">
                                console.log(<%=i%>);
                                document.querySelector('.btnSuspender').id = 'botaoSuspender-' + <%=i%>;
                                console.log("<%= aux.getStatus() %>");
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
        
        <%
        }
        %>
             
    </body>
</html>

