<%@ page import="model.roteiro.Roteiro" %>
<%@ page import="model.estadia.Estadia" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="model.cidade.Cidade" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: rafa93br
  Date: 29/11/16
  Time: 01:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% Roteiro roteiro = (Roteiro) session.getAttribute("roteiro"); %>
<% String etapa = session.getAttribute("etapa") != null ? (String) session.getAttribute("etapa") : "cidadeInicial"; %>
<% SimpleDateFormat dt = new SimpleDateFormat("dd/MM/YYYY"); %>
<% List<Cidade> opcoesCidadesIniciais = (List<Cidade>) request.getAttribute("opcoesCidadesIniciais"); %>
<% List<Cidade> opcoesCidadesOrigem = (List<Cidade>) request.getAttribute("opcoesCidadesOrigem"); %>
<% List<Cidade> opcoesCidadesDestino = (List<Cidade>) request.getAttribute("opcoesCidadesDestino"); %>

<html>
    <head>
        <title>Sugerir Roteiro</title>
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
        <nav>
            <div class="nav-wrapper">
                <a href="#" class="brand-logo center">iTrip</a>
                <ul id="nav-mobile" class="left hide-on-med-and-down">
                    <li><a href="adicionar-cliente">Adicionar Cliente</a></li>
                    <li><a href="listar-clientes">Listar Clientes</a></li>
                    <li><a href="montar-roteiro">Montar Roteiro</a></li>
                </ul>
            </div>
        </nav>
        <div class="container fluid" style="margin-top: 20px;">
            <!--Escolher cidade Inicial -->
            <% if (etapa.equals("cidadeInicial") && opcoesCidadesIniciais != null) { %>
                <div class="card-panel">
                    <form method="post" action="sugerir-roteiro">
                        <h4>Vamos selecionar a cidade inicial?</h4>
                        <div class="row">
                            <div class="input-field col s12">
                                <select  name="cidadeInicialId">
                                    <option value="" disabled selected>Escolha uma cidade</option>
                                    <% for (Cidade cidade : opcoesCidadesIniciais ) { %>
                                        <option value="<%=cidade.getId()%>"><%=cidade.getNome()%></option>
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
                </div>
            <% } %>


            <!--Escolher cidade origem/destino -->
            <% if (etapa.equals("cidadeOrigemEDestino")) { %>
                <div class="card-panel">
                    <form method="post" action="sugerir-roteiro">
                        <h4>Vamos selecionar as cidades de origem e destino?</h4>
                        <div class="row">
                            <div class="input-field col s12">
                                <select  name="cidadeOrigemId">
                                    <option value="" disabled selected>Escolha uma cidade</option>
                                    <% for (Cidade cidade : opcoesCidadesOrigem ) { %>
                                        <option value="<%=cidade.getId()%>"><%=cidade.getNome()%></option>
                                    <% } %>
                                </select>
                                <label>Cidade Origem</label>
                            </div>
                        </div>

                        <div class="row">
                            <div class="input-field col s12">
                                <select  name="cidadeDestinoId">
                                    <option value="" disabled selected>Escolha uma cidade</option>
                                    <% for (Cidade cidade : opcoesCidadesDestino ) { %>
                                        <option value="<%=cidade.getId()%>"><%=cidade.getNome()%></option>
                                    <% } %>
                                </select>
                                <label>Cidade Destino</label>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col s6">
                                <label for="dataInicio" class="">Data de início</label>
                                <input type="date" id="dataInicio" name="dataInicio" class="datepicker">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col s12">
                                <button class="btn waves-effect waves-light" type="submit" name="gerarRoteiro">
                                    Gerar roteiro
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            <% } %>


            <!--Mostrar roteiro -->
            <% if (etapa.equals("mostrarRoteiro")) { %>
                <div class="card-panel">
                    <h4>Este é o roteiro que sugerimos para você!</h4>

                    <!-- Cidade inicial -->
                    <h5>Cidade inicial</h5>
                    <p>
                        <%=roteiro.getCidadeInicial().getNome() %>
                    </p>

                    <div class="divider"></div>

                    <!-- Estadias -->

                    <% Integer i = 0; %>
                    <% for (Estadia estadia : roteiro.getEstadias() ) { %>

                        <% Double custoHotel = estadia.getHotel().getPrecoDiaria() * estadia.getDias(); %>
                        <% Double custoItinerario = estadia.getItinerario() == null ? 0 : estadia.getItinerario().getCusto(); %>
                        <% Double custoItinerarioVolta = estadia.getItinerarioVolta() == null ? 0 : estadia.getItinerarioVolta().getCusto(); %>

                        <div class="row">
                            <!-- Informações da estadia -->
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

                                        <tr>
                                            <td>Dias</td>
                                            <td><%= estadia.getDias() %></td>
                                        </tr>

                                        <tr>
                                            <td>Hotel</td>
                                            <td><%=estadia.getHotel().getNome() %></td>
                                        </tr>

                                        <% if (estadia.getItinerario() != null) { %>
                                            <tr>
                                                <td>Itinerário</td>
                                                <td><%=estadia.getItinerario().getMeioDeTransporte() + " - " + estadia.getItinerario().getId() %></td>
                                            </tr>
                                        <% } %>

                                        <% if (estadia.getItinerarioVolta() != null) { %>
                                            <tr>
                                                <td>Itinerário de volta</td>
                                                <td><%=estadia.getItinerarioVolta().getMeioDeTransporte() + " - " + estadia.getItinerarioVolta().getId() %></td>
                                            </tr>
                                        <% } %>
                                    </tbody>
                                </table>
                            </div>

                            <!-- Custos da estadia -->
                            <div class="col s6">
                                <h5>Custos</h5>

                                <table>
                                    <tbody>
                                    <tr>
                                        <td>Hotel</td>
                                        <td>R$ <%=custoHotel%></td>
                                    </tr>
                                    <% if (estadia.getItinerario() != null) { %>
                                    <tr>
                                        <td>Transporte</td>
                                        <td>R$ <%=custoItinerario%></td>
                                    </tr>
                                    <% } %>
                                    <% if (estadia.getItinerarioVolta() != null) { %>
                                    <tr>
                                        <td>Transporte de volta</td>
                                        <td>R$ <%=custoItinerarioVolta %></td>
                                    </tr>
                                    <% } %>
                                    <tr class="teal lighten-5">
                                        <td>Total</td>
                                        <td>R$ <%=estadia.getCusto()%></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div class="divider"></div>
                        <% i++; %>
                    <% } %>
                </div>
                <div class="card-panel">
                    <h5>Custo total: R$ <%=roteiro.getCusto()%></h5>
                    <a href="sugerir-roteiro">Sugerir outro roteiro...</a>
                </div>
            <% } %>


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

