<%--
  Created by IntelliJ IDEA.
  User: aluno
  Date: 18/11/16
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form method="POST" action="/adicionar-cliente-post">
        <p>
            Nome
            <input type="text" name="nome" />
        </p>
        <p>
            Data de nascimento
            <input type="date" name="data_nascimento" />
        </p>
        <p>
            Sexo
            <input type="radio" name="sexo" value="1" checked="checked"/> Masculino
            <input type="radio" name="sexo" value="0"/> Feminino
        </p>
        <p>
            CPF
            <input type="text" name="cpf" />
        </p>
        <p>
            Email
            <input type="text" name="email" />
        </p>
        <p>
            Telefone
            <input type="text" name="telefone" />
        </p>
        <p>
            <input type="submit" value="Salvar" />
        </p>

    </form>
</body>
</html>
