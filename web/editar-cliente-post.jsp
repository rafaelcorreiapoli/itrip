<%--
  Created by IntelliJ IDEA.
  User: aluno
  Date: 18/11/16
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Editar Cliente</title>
</head>
<body>
    <% Boolean resultado = (Boolean) request.getAttribute("resultado"); %>
    <% if (resultado) { %>
    <h1 style="color: green">Sucesso!</h1>
    <% } else { %>
    <h1 style="color: darkred">Erro!</h1>
    <% } %>
    <a href="/listar-clientes">Voltar</a>

</body>
</html>
