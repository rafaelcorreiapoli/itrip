<%@ page import="model.cliente.Cliente" %>
<%@ page import="model.roteiro.Roteiro" %>
<%@ page import="model.cidade.Cidade" %>
<%@ page import="java.util.List" %>
<%@ page import="model.itnerario.Itinerario" %>
<%@ page import="model.hotel.Hotel" %>
<%@ page import="model.estadia.Estadia" %><%--
  Created by IntelliJ IDEA.
  User: rafa93br
  Date: 20/11/16
  Time: 01:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Montar Roteiro</title>
</head>
<body>
    <% Cliente cliente = (Cliente) session.getAttribute("cliente"); %>
    <% Roteiro roteiro = (Roteiro) session.getAttribute("roteiro"); %>
    <% Estadia estadia = (Estadia) session.getAttribute("estadia"); %>
    <% List<Cidade> opcoesCidadesIniciais = (List<Cidade>) request.getAttribute("opcoesCidadesIniciais"); %>
    <% List<Cidade> opcoesCidades = (List<Cidade>) request.getAttribute("opcoesCidades"); %>
    <% List<Cidade> opcoesCidadesOrigem = (List<Cidade>) request.getAttribute("opcoesCidadesOrigem"); %>
    <% List<Cidade> opcoesCidadesDestino = (List<Cidade>) request.getAttribute("opcoesCidadesDestino"); %>
    <% List<Hotel> opcoesHoteis = (List<Hotel>) request.getAttribute("opcoesHoteis"); %>
    <% List<Itinerario> opcoesItinerarios = (List<Itinerario>) request.getAttribute("opcoesItinerarios"); %>
    <% List<Itinerario> opcoesItinerariosParaDestino = (List<Itinerario>) request.getAttribute("opcoesItinerariosParaDestino"); %>

    <% Boolean podeFinalizar = (Boolean) request.getAttribute("podeFinalizar"); %>
    <% Boolean clienteNaoEncontrado = (Boolean) request.getAttribute("clienteNaoEncontrado"); %>
    <% Boolean roteiroFinalizado = (Boolean) request.getAttribute("roteiroFinalizado"); %>

    <% if (cliente == null) { %>
        <!-- Não tenho o cliente -->
        <% if (clienteNaoEncontrado != null) { %>
            <div>Cliente não encontrado</div>
        <% }%>
        <form method="POST" action="/montar-roteiro">
            <input type="text" name="cpf" />
            <input type="submit" name="selecionarCliente" value="Selecionar cliente" />
        </form>

    <%} else { %>
            <!-- Tenho o cliente -->
            <div>
                <p>Nome: <%=cliente.getNome() %></p>
                <p>CPF: <%=cliente.getCpf() %></p>
                <p>Telefone: <%=cliente.getTelefone() %></p>
                <p>E-mail: <%=cliente.getEmail() %></p>
                <hr />
            </div>


            <% if (roteiro != null) { %>
                <% if (roteiro.getCidadeInicial() == null) { %>
                    <!-- Não tenho a cidade inicial -->
                    <form method="post" action="/montar-roteiro">
                        <select name="cidadeInicialId">
                            <% for (Cidade cidadeInicial : opcoesCidadesIniciais ) { %>
                            <option value="<%=cidadeInicial.getId()%>"><%=cidadeInicial.getNome()%></option>
                            <% } %>
                        </select>
                        <input type="submit" name="selecionarCidadeInicial" value="Selecionar cidade inicial" />
                    </form>
                <%} else { %>
                    <div>
                        <p>Cidade inicial: <%=roteiro.getCidadeInicial().getNome() %></p>
                        <hr />
                    </div>

                    <% if (!roteiroFinalizado) { %>
                        <form method="post" action="/montar-roteiro">
                            <% Boolean configurandoDestino = roteiro.getEstadias().size() == 1; %>
                            <% Boolean configurandoOrigem = roteiro.getEstadias().size() == 0; %>
                            <% Boolean configurandoIntermediaria = !configurandoDestino && !configurandoOrigem; %>

                            <% if (configurandoOrigem) { %>
                            <h3>Cidade Origem</h3>
                            <% } else if (configurandoDestino) { %>
                            <h3>Cidade Destino</h3>
                            <% } else { %>
                            <h3>Estadia #<%=roteiro.getEstadias().size() - 1 %></h3>
                            <% } %>

                            <% if (estadia.getCidade() == null) { %>
                            Escolher cidade:
                            <select name="cidadeId">
                                <% for (Cidade cidade : opcoesCidades ) { %>
                                <option value="<%=cidade.getId()%>"><%=cidade.getNome()%></option>
                                <% } %>
                            </select>
                            <input type="submit" name="selecionarCidade" value="Selecionar cidade" />
                            <% } else { %>
                            <p>Cidade: <%=estadia.getCidade().getNome() %></p>
                            Escolher hotel:
                            <select name="hotelId">
                                <% for (Hotel hotel : opcoesHoteis ) { %>
                                <option value="<%=hotel.getId()%>"><%=hotel.getNome()%></option>
                                <% } %>
                            </select>
                            <br>

                            <% if (configurandoOrigem) { %>
                            Escolher itinerário entre cidade inicial e esta cidade:
                            <% } else if (configurandoDestino) { %>
                            Escolher itinerário entre cidade destino e cidade inicial:
                            <% } else { %>
                            Escolher itinerário entre última cidade e esta cidade
                            <% } %>

                            <select name="<%= configurandoDestino ? "itinerarioVoltaId" : "itinerarioId" %>">
                                <% for (Itinerario itinerario : opcoesItinerarios ) { %>
                                    <option value="<%=itinerario.getId()%>"><%=itinerario.getMeioDeTransporte() + " - " + itinerario.getId() %></option>
                                <% } %>
                            </select>
                            <br>
                            <input type="submit" name="finalizarEstadia" value="Adicionar estadia" />
                            <% } %>
                        </form>

                        <% if (podeFinalizar) { %>
                        <div>
                            -- OU FINALIZE JÁ--
                            <form action="montar-roteiro" method="POST">
                                Escolha o itinerário até a cidade de destino
                                <select name="itinerarioParaDestinoId" %>">
                                    <% for (Itinerario itinerario : opcoesItinerariosParaDestino ) { %>
                                    <option value="<%=itinerario.getId()%>"><%=itinerario.getMeioDeTransporte() + " - " + itinerario.getId() %></option>
                                    <% } %>
                                </select>
                                <br>
                                <input type="submit" name="finalizarRoteiro" value="Finalizar roteiro" />
                            </form>
                        </div>
                        <% } %>
                    <% } else { %>
                        <h1>Roteiro finalizado!</h1>
                    <% } %>
                    <hr>
                    <div>
                        <h2>Roteiro até agora:</h2>
                        <% Integer i = 0; %>
                        <% for (Estadia estadiaI : roteiro.getEstadias() ) { %>
                        <div>
                            <% if (i.equals(0)) { %>
                            <h3>Cidade origem</h3>
                            <% } else if (i.equals(roteiro.getEstadias().size() - 1)) { %>
                            <h3>Cidade destino</h3>
                            <% } else { %>
                            <h3>Estadia #<%=i%></h3>
                            <% } %>
                            <p>Cidade: <%=estadiaI.getCidade().getNome() %></p>
                            <p>Hotel: <%=estadiaI.getHotel().getNome() %></p>
                            <% if (estadiaI.getItinerarioVolta() != null) { %>
                                <p>Itinerário de volta: <%=estadiaI.getItinerarioVolta().getMeioDeTransporte() %></p>
                            <% } %>
                            <% if (estadiaI.getItinerario() != null) { %>
                                <p>Itinerário: <%=estadiaI.getItinerario().getMeioDeTransporte() %></p>
                            <% } %>
                        </div>
                        <hr />
                        <% i++; %>
                        <% } %>
                    </div>
                <% } %>
            <%} else { %>
            <% } %>
    <% } %>

</body>
</html>

<!--
<!-- Já tenho a cidade inicial -->
<%--<% if (roteiro.getOrigem() == null) { %>--%>
<%--<!-- Preciso escolher cidade origem -->--%>
<%--<form action="montar-roteiro" method="POST">--%>
    <%--Cidade Origem:--%>
    <%--<select name="cidadeOrigemId">--%>
        <%--<% for (Cidade cidadeOrigem : opcoesCidadesOrigem ) { %>--%>
        <%--<option value="<%=cidadeOrigem.getId()%>"><%=cidadeOrigem.getNome()%></option>--%>
        <%--<% } %>--%>
    <%--</select>--%>
    <%--<input type="submit" name="selecionarCidadeOrigem" value="Selecionar cidade origem" />--%>
<%--</form>--%>
<%--<% } else { %>--%>
<%--<!-- Já tenho cidade origem -->--%>
<%--<% if (roteiro.getDestino() == null) { %>--%>
<%--<!-- Preciso escolher cidade destino -->--%>
<%--<form action="montar-roteiro" method="POST">--%>
    <%--<select name="cidadeDestinoId">--%>
        <%--<% for (Cidade cidadeDestino : opcoesCidadesDestino ) { %>--%>
        <%--<option value="<%=cidadeDestino.getId()%>"><%=cidadeDestino.getNome()%></option>--%>
        <%--<% } %>--%>
    <%--</select>--%>
    <%--<input type="submit" name="selecionarCidadeDestino" value="Selecionar cidade destino"--%>
<%--</form>--%>
<%--<% } else { %>>--%>
<%--<!-- Já tenho origem e destino, posso ir montando o roteiro -->--%>
<%--<p>Cidade inicial: <%=roteiro.getCidadeInicial().getNome() %></p>--%>
<%--<% Integer i = 0; %>--%>
<%--<% for (Estadia estadia : roteiro.getEstadias() ) { %>--%>
<%--<div>--%>
    <%--<% if (i.equals(0)) { %>--%>
    <%--<h3>Cidade origem</h3>--%>
    <%--<% } else if (i.equals(roteiro.getEstadias().size())) { %>--%>
    <%--<h3>Cidade destino</h3>--%>
    <%--<% } else { %>--%>
    <%--<h3>Estadia #<%=i%></h3>--%>
    <%--<% } %>--%>
    <%--<p>Cidade: <%=estadia.getCidade().getNome() %></p>--%>
    <%--<p>Hotel: <%=estadia.getHotel().getNome() %></p>--%>
    <%--<p>Itinerário: <%=estadia.getItinerario().getCusto() %></p>--%>
<%--</div>--%>
<%--<hr />--%>
<%--<% i++; %>--%>
<%--<% } %>--%>


<%--<form action="montar-roteiro" method="POST">--%>
    <%--<select name="cidadeId">--%>
        <%--<% for (Cidade cidadeInicial : opcoesCidades ) { %>--%>
        <%--<option value="<%=cidadeInicial.getId()%>"><%=cidadeInicial.getNome()%></option>--%>
        <%--<% } %>--%>
    <%--</select>--%>
    <%--<select name="hotelId">--%>
        <%--<% for (Hotel hotel : opcoesHoteis ) { %>--%>
        <%--<option value="<%=hotel.getId()%>"><%=hotel.getNome()%></option>--%>
        <%--<% } %>--%>
    <%--</select>--%>
    <%--<select name="itinerarioId">--%>
        <%--<% for (Itinerario itinerario : opcoesItinerarios ) { %>--%>
        <%--<option value="<%=itinerario.getId()%>"><%=itinerario.getCusto() %></option>--%>
        <%--<% } %>--%>
    <%--</select>--%>
    <%--<input type="submit" name="adicionarEstadia" value="Adicionar estadia" />--%>
<%--</form>--%>
<%--<!-- Se é possível finalizar o roteiro nesta viagem -->--%>
<%--<% if (podeFinalizar) { %>--%>
<%--<p>--- OU ---</p>--%>
<%--<form action="montar-roteiro" method="POST">--%>
    <%--<input type="submit" name="finalizarRoteiro" value="Finalizar roteiro" />--%>
<%--</form>--%>
<%--<% } %>--%>
<%--<% } %>--%>
<%--<% } %>--%>
