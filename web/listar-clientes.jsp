<%@ page import="model.cliente.Cliente" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: aluno
  Date: 18/11/16
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iTrip - Listar Clientes</title>
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/css/materialize.min.css">
    <link href="https://fonts.googleapis.com/css?family=Lato|Open+Sans" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>
<body>
    <%@ include file="nav.jsp" %>

    <div class="container fluid" style="margin-top: 20px;">
        <h2>Lista de clientes</h2>
    <%
        ArrayList<Cliente> clientes = (ArrayList<Cliente>) request.getAttribute("clientes");
        for (Cliente cliente : clientes) { %>
            <div class="">
                <p>Nome:
                    <a href="/editar-cliente?id=<%=cliente.getId() %>">
                        <b><%= cliente.getNome() %></b>
                    </a>

                </p>
                <p>CPF: <b><%= cliente.getCpf() %></b></p>
                <form method="POST" action="/deletar-cliente-post?id=<%=cliente.getId() %>">
                    <input type="submit" value="X" />
                </form>
            </div>
            <hr>
        <% } %>
    </div>

</body>
</html>
