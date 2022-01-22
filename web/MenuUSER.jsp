<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aplicacao.Usuario" %>
<!DOCTYPE html>

<div class="container-fluid">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">Banco</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarBanco" aria-controls="navbarBanco" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarBanco">
            <ul class="navbar-nav">
              <li class="nav-item active">
                <a class="nav-link" href="area_restritaUSER.jsp">Área Restrita <span class="sr-only">(current)</span></a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="ControllerConta?acao=mostrar">Contas</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="processarLogin?acao=sair">Sair</a>
              </li>
            </ul>
        </div>
    </nav>
</div>
