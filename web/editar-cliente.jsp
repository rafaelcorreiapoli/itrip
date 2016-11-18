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
    <title>Editar Cliente</title>
</head>
<body>
<% Cliente cliente = (Cliente) request.getAttribute("cliente"); %>
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

</body>
</html>
