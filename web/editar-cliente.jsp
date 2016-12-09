<%@ page import="model.cliente.Cliente" %><%--
  Created by IntelliJ IDEA.
  User: aluno
  Date: 18/11/16
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iTrip - Editar Cliente</title>
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/css/materialize.min.css">
    <link href="https://fonts.googleapis.com/css?family=Lato|Open+Sans" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>
<body>
<% Cliente cliente = (Cliente) request.getAttribute("cliente"); %>
    <%@ include file="nav.jsp" %>

    <div class="container fluid" style="margin-top: 20px;">
        <% if (cliente == null) { %>
            <h1>Cliente não encontrado</h1>
        <% } else { %>
            <form method="POST" action="/editar-cliente-post">
                <input value="<%=cliente.getId() %>" type="hidden" name="id" />
            <p>
                Nome
                <input value="<%=cliente.getNome() %>" type="text" name="nome" />
            </p>
            <p>
                Data de nascimento
                <input value="<%=cliente.getNascimento() %>" type="date" name="data_nascimento" />
            </p>
            <p>
                Sexo
                <input type="radio" name="sexo" value="1" checked="<%=cliente.getSexo() ? "checked" : "" %>"/> Masculino
                <input type="radio" name="sexo" value="0" checked="<%=cliente.getSexo() ? "checked" : "" %>" /> Feminino
            </p>
            <p>
                CPF
                <input value="<%=cliente.getCpf() %>" type="text" name="cpf" />
            </p>
            <p>
                Email
                <input value="<%=cliente.getEmail() %>" type="text" name="email" />
            </p>
            <p>
                Telefone
                <input value="<%=cliente.getTelefone() %>" type="text" name="telefone" />
            </p>
            <p>
                <input type="submit" value="Salvar" />
            </p>

            </form>
        <% }%>
    </div>
</body>
</html>
