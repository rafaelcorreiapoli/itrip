<%--
  Created by IntelliJ IDEA.
  User: rafa93br
  Date: 04/11/16
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Insira o CPF do cliente</title>
</head>
<body>
    <p>Inserir o cpf do cliente</p>
    <form action="/inserir-cpf" method="POST">
        <input type="text" name="cpf" />
        <button type="submit">Consultar</button>
    </form>

</body>
</html>
