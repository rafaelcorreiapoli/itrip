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
    <title>Title</title>
</head>
<body>
    <div>
        <a href="adicionar-cliente">Adicionar Cliente</a> |
        <a href="listar-clientes">Listar Clientes</a>
    </div>

    <hr>
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
    <% }
    %>

</body>
</html>
