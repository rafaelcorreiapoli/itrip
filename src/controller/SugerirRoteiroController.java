package controller;

import lib.montador.Montador;
import model.cidade.Cidade;
import model.cidade.CidadeDAO;
import model.estadia.Estadia;
import model.itnerario.Itinerario;
import model.itnerario.ItinerarioDAO;
import model.roteiro.Roteiro;
import model.roteiro.RoteiroDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by rafa93br on 29/11/16.
 */
@WebServlet(name = "SugerirRoteiroController")
public class SugerirRoteiroController extends HttpServlet {
    private static final String ETAPA_CIDADE_INICIAL = "cidadeInicial";
    private static final String ETAPA_CIDADE_ORIGEM_E_DESTINO = "cidadeOrigemEDestino";
    private static final String ETAPA_MOSTRAR_ROTEIRO = "mostrarRoteiro";

    CidadeDAO cidadeDAO = CidadeDAO.getInstance();
    RoteiroDAO roteiroDAO = RoteiroDAO.getInstance();
    ItinerarioDAO itinerarioDAO = ItinerarioDAO.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String etapa = (String) session.getAttribute("etapa");
        Roteiro roteiro = (Roteiro) session.getAttribute("roteiro");

        if (roteiro == null) {
            throw new Error("Roteiro não instanciado");
        }

        switch (etapa) {
            case ETAPA_CIDADE_INICIAL:
                Integer cidadeInicialId = Integer.parseInt(request.getParameter("cidadeInicialId"));
                Cidade cidadeInicial = CidadeDAO.getInstance().getCidadeById(cidadeInicialId, false);
                roteiro.setCidadeInicial(cidadeInicial);

                List<Cidade> opcoesCidadesOrigem = cidadeDAO.getCidadesAcessiveisPorCidade(cidadeInicial.getId());
                List<Cidade> opcoesCidadesDestino = cidadeDAO.getCidadesQueAcessamCidade(cidadeInicial.getId());

                request.setAttribute("opcoesCidadesOrigem", opcoesCidadesOrigem);
                request.setAttribute("opcoesCidadesDestino", opcoesCidadesDestino);
                session.setAttribute("etapa", ETAPA_CIDADE_ORIGEM_E_DESTINO);

                break;
            case ETAPA_CIDADE_ORIGEM_E_DESTINO:
                Integer cidadeOrigemId = Integer.parseInt(request.getParameter("cidadeOrigemId"));
                Integer cidadeDestinoId = Integer.parseInt(request.getParameter("cidadeDestinoId"));
                String dataInicio = request.getParameter("dataInicio");
                Date dataInicioDate = null;

                try {
                    dataInicioDate = new SimpleDateFormat("yyyy-MM-dd").parse(dataInicio);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Cidade cidadeOrigem = cidadeDAO.getCidadeById(cidadeOrigemId, false);
                Cidade cidadeDestino = cidadeDAO.getCidadeById(cidadeDestinoId, false);

                Montador montador = new Montador();

                List<Estadia> estadias = montador.gerarEstadias(cidadeOrigem, cidadeDestino, dataInicioDate);

                roteiro.setEstadias(estadias);
                Itinerario itinerarioInicialOrigem = itinerarioDAO.getMenorCustoAviao(roteiro.getCidadeInicial(), estadias.get(0).getCidade());
                roteiro.getEstadias().get(0).setItinerario(itinerarioInicialOrigem);

                Itinerario itinerarioDestinoInicial = itinerarioDAO.getMenorCustoAviao(roteiro.getDestino().getCidade(), roteiro.getCidadeInicial());
                roteiro.getDestino().setItinerarioVolta(itinerarioDestinoInicial);

                session.setAttribute("etapa", ETAPA_MOSTRAR_ROTEIRO);
                break;
        }

        request.getRequestDispatcher("/sugerir-roteiro.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("etapa", ETAPA_CIDADE_INICIAL);
        session.setAttribute("roteiro", new Roteiro());
        request.setAttribute("opcoesCidadesIniciais", cidadeDAO.getAll());
        request.getRequestDispatcher("/sugerir-roteiro.jsp").forward(request, response);
    }
}

