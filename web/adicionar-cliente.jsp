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
    <title>iTrip - Adicionar Cliente</title>
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/css/materialize.min.css">
    <link href="https://fonts.googleapis.com/css?family=Lato|Open+Sans" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>
<body>
    <%@ include file="nav.jsp" %>
    <div class="container fluid" style="margin-top: 20px;">
        <h1>Adicionar Cliente</h1>
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
    </div>
</body>
</html>
