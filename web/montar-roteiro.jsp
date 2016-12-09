<%@ page import="model.cliente.Cliente" %>
<%@ page import="model.roteiro.Roteiro" %>
<%@ page import="model.cidade.Cidade" %>
<%@ page import="java.util.List" %>
<%@ page import="model.itnerario.Itinerario" %>
<%@ page import="model.hotel.Hotel" %>
<%@ page import="model.estadia.Estadia" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
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
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/css/materialize.min.css">
    <link href="https://fonts.googleapis.com/css?family=Lato|Open+Sans" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <style>
        h4 {
            margin-top: 0px;
        }
    </style>
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
    <% SimpleDateFormat dt = new SimpleDateFormat("dd/MM/YYYY"); %>
    <% Boolean podeFinalizar = (Boolean) request.getAttribute("podeFinalizar"); %>
    <% Boolean clienteNaoEncontrado = (Boolean) request.getAttribute("clienteNaoEncontrado"); %>
    <% Boolean roteiroFinalizado = (Boolean) request.getAttribute("roteiroFinalizado"); %>
    <% Boolean roteiroSalvo = (Boolean) request.getAttribute("roteiroSalvo"); %>

    <%@ include file="nav.jsp" %>
    <div class="container fluid" style="margin-top: 20px;">
        <div class="row">
            <div class="col s6">
                <% if (cliente == null) { %>
                <!-- Não tenho o cliente -->
                    <div class="row">
                    <div class="col s12">
                        <div class="card-panel">
                            <h4>Selecione um cliente pelo CPF</h4>
                            <form method="POST" action="/montar-roteiro">
                                <% if (clienteNaoEncontrado != null) { %>
                                <h5 class="red-text">Cliente não encontrado</h5>
                                <% }%>
                                <div class="row">
                                    <div class="input-field col s6">
                                        <input placeholder="999.999.999-99" id="cpf" name="cpf" type="text">
                                        <label for="cpf">CPF do cliente</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12">
                                        <button class="btn waves-effect waves-light" type="submit" name="selecionarCliente">
                                            Selecionar Cliente
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <%} else { %>
                    <!-- Tenho o cliente -->
                    <div class="card-panel">
                    <h4>Cliente</h4>
                    <table>
                        <tbody>
                        <tr>
                            <td>Nome</td>
                            <td><%=cliente.getNome() %></td>
                        </tr>
                        <tr>
                            <td>CPF</td>
                            <td><%=cliente.getCpf() %></td>
                        </tr>
                        <tr>
                            <td>Telefone</td>
                            <td><%=cliente.getTelefone() %></td>
                        </tr>
                        <tr>
                            <td>E-mail</td>
                            <td><%=cliente.getEmail() %></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                    <% if (roteiro != null) { %>
                        <% if (roteiro.getCidadeInicial() == null) { %>
                            <!-- Não tenho a cidade inicial -->
                            <form class="card-panel" method="post" action="/montar-roteiro">
                                <h4>Vamos selecionar a cidade inicial?</h4>
                                <div class="row">
                                    <div class="input-field col s12">
                                        <select  name="cidadeInicialId">
                                            <option value="" disabled selected>Escolha uma cidade</option>
                                            <% for (Cidade cidadeInicial : opcoesCidadesIniciais ) { %>
                                            <option value="<%=cidadeInicial.getId()%>"><%=cidadeInicial.getNome()%></option>
                                            <% } %>
                                        </select>
                                        <label>Cidade Inicial</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12">
                                        <button class="btn waves-effect waves-light" type="submit" name="selecionarCidadeInicial">
                                            Selecionar Cidade Inicial
                                        </button>
                                    </div>
                                </div>
                            </form>
                        <%} else { %>
                            <% if (!roteiroFinalizado) { %>
                                <form class="card-panel" method="post" action="/montar-roteiro">
                                    <% Boolean configurandoDestino = roteiro.getEstadias().size() == 1; %>
                                    <% Boolean configurandoOrigem = roteiro.getEstadias().size() == 0; %>
                                    <% Boolean configurandoIntermediaria = !configurandoDestino && !configurandoOrigem; %>

                                    <% if (configurandoOrigem) { %>
                                        <h4>Qual é a cidade de origem?</h4>
                                    <% } else if (configurandoDestino) { %>
                                        <h4>Qual é a cidade de destino?</h4>
                                    <% } else { %>
                                        <h4>Quer adicionar nova estadia? (#<%=roteiro.getEstadias().size() - 1 %>)</h4>
                                    <% } %>

                                    <% if (estadia.getCidade() == null) { %>
                                        <div class="row">
                                            <div class="input-field col s12">
                                                <select name="cidadeId">
                                                    <% for (Cidade cidade : opcoesCidades ) { %>
                                                    <option value="<%=cidade.getId()%>"><%=cidade.getNome()%></option>
                                                    <% } %>
                                                </select>
                                                <label>Cidade</label>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col s6">
                                                <label for="dataChegada" class="">Data de chegada</label>
                                                <input type="date" id="dataChegada" name="dataChegada" class="datepicker">
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col s6">
                                                <label for="dataSaida" class="">Data de saída</label>
                                                <input type="date" id="dataSaida" name="dataSaida" class="datepicker">
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col s12">
                                                <button class="btn waves-effect waves-light" type="submit" name="selecionarCidade">
                                                    Selecionar Cidade
                                                </button>
                                            </div>
                                        </div>
                                    <% } else { %>
                                        <table style="margin-bottom: 20px;">
                                            <tbody>
                                            <tr>
                                                <td>Cidade</td>
                                                <td><%=estadia.getCidade().getNome() %></td>
                                            </tr>
                                            <tr>
                                                <td>Data chegada</td>
                                                <td><%=dt.format(estadia.getDataChegada())%></td>
                                            </tr>
                                            <tr>
                                                <td>Data saída</td>
                                                <td><%=dt.format(estadia.getDataSaida())%></td>
                                            </tr>
                                            </tbody>
                                        </table>

                                        <div class="row">
                                            <div class="input-field col s12">
                                                <select name="hotelId">
                                                    <% for (Hotel hotel : opcoesHoteis ) { %>
                                                    <option value="<%=hotel.getId()%>"><%=hotel.getNome() + " - R$ " + hotel.getPrecoDiaria() + "/dia"%></option>
                                                    <% } %>
                                                </select>
                                                <label>Escolha o hotel</label>
                                            </div>
                                        </div>



                                        <div class="row">
                                            <div class="input-field col s12">
                                                <select name="<%= configurandoDestino ? "itinerarioVoltaId" : "itinerarioId" %>">
                                                    <% for (Itinerario itinerario : opcoesItinerarios ) { %>
                                                    <option value="<%=itinerario.getId()%>"><%=itinerario.getMeioDeTransporte() + " - R$" + itinerario.getCusto() + " (" + itinerario.getDuracao() + " min)" %></option>
                                                    <% } %>
                                                </select>
                                                <label>
                                                    <% if (configurandoOrigem) { %>
                                                    Escolher itinerário entre cidade inicial e esta cidade:
                                                    <% } else if (configurandoDestino) { %>
                                                    Escolher itinerário entre cidade destino e cidade inicial:
                                                    <% } else { %>
                                                    Escolher itinerário entre última cidade e esta cidade
                                                    <% } %>
                                                </label>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col s12">
                                                <button class="btn waves-effect waves-light" type="submit" name="finalizarEstadia">
                                                    Adicionar estadia
                                                </button>
                                            </div>
                                        </div>
                                    <% } %>
                                </form>

                                <% if (podeFinalizar) { %>
                                    <form class="card-panel" action="montar-roteiro" method="POST">
                                        <h4>Quer finalizar o roteiro?</h4>
                                        <div class="row">
                                            <div class="input-field col s12">
                                                <select name="itinerarioParaDestinoId" %>">
                                                    <% for (Itinerario itinerario : opcoesItinerariosParaDestino ) { %>
                                                    <option value="<%=itinerario.getId()%>"><%=itinerario.getMeioDeTransporte() + " - " + itinerario.getId() %></option>
                                                    <% } %>
                                                </select>
                                                <label>Escolha o itinerário até a cidade de destino</label>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col s12">
                                                <button class="btn waves-effect waves-light" type="submit" name="finalizarRoteiro">
                                                    Finalizar roteiro
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                                <% } %>
                            <% } else { %>
                                <div class="card-panel">
                                    <h4>Roteiro finalizado!</h4>
                                    <h5>Custo total: R$<%=roteiro.getCusto()%></h5>
                                    <div class="divider" ></div>
                                    <% if (!roteiroSalvo) { %>
                                        <div class="row">
                                            <form action="montar-roteiro" method="POST">
                                                <div class="col s6">
                                                    <button class="btn waves-effect waves-light" type="submit" name="confirmarCriacao">
                                                        Confirmar
                                                    </button>
                                                </div>
                                                <div class="col s6">
                                                    <button class="btn waves-effect waves-light red" type="submit" name="recomecarRoteiro">
                                                        Recomeçar
                                                    </button>
                                                </div>
                                            </form>
                                        </div>
                                    <% } else { %>
                                        <h5 style="color: green;">Roteiro Salvo!</h5>
                                        <a href="/montar-roteiro">Montar outro roteiro...</a>
                                    <% } %>
                                </div>
                            <% } %>
                        <% } %>
                    <% } %>
                <% } %>
            </div>
            <div class="col s6">
                <div class="card-panel">
                    <h4>Este é o seu roteiro até agora</h4>
                    <% if (roteiro != null) { %>
                        <% if (roteiro.getCidadeInicial() != null) { %>
                            <h5>Cidade inicial</h5>
                            <p><%=roteiro.getCidadeInicial().getNome() %></p>
                            <div class="divider"></div>
                        <% } %>

                        <% Integer i = 0; %>
                        <% for (Estadia estadiaI : roteiro.getEstadias() ) { %>
                            <% Double custoHotel = estadiaI.getHotel().getPrecoDiaria() * estadiaI.getDias(); %>
                            <% Double custoItinerario = estadiaI.getItinerario() == null ? 0 : estadiaI.getItinerario().getCusto(); %>
                            <% Double custoItinerarioVolta = estadiaI.getItinerarioVolta() == null ? 0 : estadiaI.getItinerarioVolta().getCusto(); %>

                            <div class="row">
                                <div class="col s6">
                                    <% if (i.equals(0)) { %>
                                    <h5>Cidade origem</h5>
                                    <% } else if (i.equals(roteiro.getEstadias().size() - 1)) { %>
                                    <h5>Cidade destino</h5>
                                    <% } else { %>
                                    <h5>Estadia #<%=i%></h5>
                                    <% } %>
                                    <table>
                                        <tbody>
                                        <tr>
                                            <td>Cidade</td>
                                            <td><%=estadiaI.getCidade().getNome() %></td>
                                        </tr>
                                        <tr>
                                            <td>Data chegada</td>
                                            <td><%=dt.format(estadiaI.getDataChegada())%></td>
                                        </tr>
                                        <tr>
                                            <td>Data saída</td>
                                            <td><%=dt.format(estadiaI.getDataSaida())%></td>
                                        </tr>
                                        <tr>
                                            <td>Dias</td>
                                            <td><%= estadiaI.getDias() %></td>
                                        </tr>
                                        <tr>
                                            <td>Hotel</td>
                                            <td><%=estadiaI.getHotel().getNome() %></td>
                                        </tr>
                                        <% if (estadiaI.getItinerario() != null) { %>
                                        <tr>
                                            <td>Itinerário</td>
                                            <td><%=estadiaI.getItinerario().getMeioDeTransporte() %></td>
                                        </tr>
                                        <% } %>
                                        <% if (estadiaI.getItinerarioVolta() != null) { %>
                                        <tr>
                                            <td>Itinerário de volta</td>
                                            <td><%=estadiaI.getItinerarioVolta().getMeioDeTransporte() %></td>
                                        </tr>
                                        <% } %>
                                        </tbody>
                                    </table>
                                </div>

                                <div class="col s6">
                                    <h5>Custos</h5>
                                    <table>
                                        <tbody>
                                            <tr>
                                                <td>Hotel</td>
                                                <td>R$ <%=custoHotel%></td>
                                            </tr>
                                            <% if (estadiaI.getItinerario() != null) { %>
                                                <tr>
                                                    <td>Transporte</td>
                                                    <td>R$ <%=custoItinerario%></td>
                                                </tr>
                                            <% } %>
                                            <% if (estadiaI.getItinerarioVolta() != null) { %>
                                                <tr>
                                                    <td>Transporte de volta</td>
                                                    <td>R$ <%=custoItinerarioVolta %></td>
                                                </tr>
                                            <% } %>
                                            <tr class="teal lighten-5">
                                                <td>Total</td>
                                                <td>R$ <%=estadiaI.getCusto()%></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>

                            <div class="divider"></div>
                            <% i++; %>
                        <% } %>
                    <% } %>
                </div>
            </div>
        </div>

    </div>
    <!-- Compiled and minified JavaScript -->
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/js/materialize.min.js"></script>

    <script>
        $(document).ready(function() {
            $('select').material_select();
        });
        $('.datepicker').pickadate({
            selectMonths: true, // Creates a dropdown to control month
            selectYears: 15, // Creates a dropdown of 15 years to control year
            format: "yyyy-mm-dd"
        });
    </script>
</body>
</html>

