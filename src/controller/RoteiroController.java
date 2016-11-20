package controller;

import model.cidade.Cidade;
import model.cidade.CidadeDAO;
import model.cliente.Cliente;
import model.cliente.ClienteDAO;
import model.estadia.Estadia;
import model.hotel.Hotel;
import model.hotel.HotelDAO;
import model.itnerario.Itinerario;
import model.itnerario.ItinerarioDAO;
import model.roteiro.Roteiro;
import model.roteiro.RoteiroDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rafa93br on 08/11/16.
 */
@WebServlet(name = "RoteiroController")
public class RoteiroController extends Controller {
    RoteiroDAO dao = RoteiroDAO.getInstance();

    class RequestJson {
        class EstadiaJson {
            public Integer hotelId;
            public Integer cidadeId;
            public Date dataChegada;
            public Date dataSaida;
            public Integer itinerarioId;
            public Integer itinerarioVoltaId;
        }

        public Integer cidadeInicial;
        public List<EstadiaJson> estadias;

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        this.prepareResponse(response);


        switch (servletPath) {
            case "/salvar-roteiro": this.salvarRoteiro(request, response); break;
            case "/montar-roteiro": this.montarRoteiroPost(request, response); break;
        }
    }

    protected void montarRoteiroPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Parameters
        String cpf = request.getParameter("cpf");
        String cidadeInicialId = request.getParameter("cidadeInicialId");
        String hotelId = request.getParameter("hotelId");
        String itinerarioId = request.getParameter("itinerarioId");
        String itinerarioVoltaId = request.getParameter("itinerarioVoltaId");
        String cidadeId = request.getParameter("cidadeId");
        String itinerarioParaDestinoId = request.getParameter("itinerarioParaDestinoId");

        // Control
        String selecionarCliente = request.getParameter("selecionarCliente");
        String selecionarCidade = request.getParameter("selecionarCidade");
        String finalizarRoteiro = request.getParameter("finalizarRoteiro");
        String finalizarEstadia = request.getParameter("finalizarEstadia");
        String selecionarCidadeInicial = request.getParameter("selecionarCidadeInicial");

        HttpSession session = request.getSession();

        Roteiro roteiro = null;
        Estadia estadia = null;

        request.setAttribute("roteiroFinalizado", false);
        request.setAttribute("podeFinalizar", false);

        Boolean configurandoOrigem = null;
        Boolean configurandoDestino = null;
        Boolean configurandoIntermediaria = null;

        if (session.getAttribute("roteiro") != null) {
            roteiro = (Roteiro) session.getAttribute("roteiro");
            configurandoOrigem = roteiro.getEstadias().size() == 0;
            configurandoDestino = roteiro.getEstadias().size() == 1;
            configurandoIntermediaria = !configurandoOrigem && !configurandoDestino;
        }
        if (session.getAttribute("estadia") != null) {
            estadia = (Estadia) session.getAttribute("estadia");
        }

        //  Selecionou cliente por cpf
        if (selecionarCliente != null) {
            Cliente cliente = ClienteDAO.getInstance().getClienteByCpf(cpf);

            if (cliente != null) {
                Roteiro novoRoteiro = new Roteiro();
                session.setAttribute("roteiro", novoRoteiro);
                session.setAttribute("cliente", cliente);
                List<Cidade> opcoesCidadesIniciais = CidadeDAO.getInstance().getAll();
                request.setAttribute("opcoesCidadesIniciais", opcoesCidadesIniciais);
            } else {
                request.setAttribute("clienteNaoEncontrado", true);
            }
        } else
        // Selecionou cidade inicial
        if (selecionarCidadeInicial != null && roteiro != null) {
            Integer intCidadeInicialId = Integer.parseInt(cidadeInicialId);
            Cidade cidadeInicial = CidadeDAO.getInstance().getCidadeById(intCidadeInicialId, true);
            roteiro.setCidadeInicial(cidadeInicial);

            List<Cidade> opcoesCidades = CidadeDAO.getInstance().getCidadesAcessiveisPorCidade(intCidadeInicialId);

            request.setAttribute("opcoesCidades", opcoesCidades);
            session.setAttribute("estadia", new Estadia());
        }
        // Selecionou uma cidade para a estadia atual, devo mandar lista de hoteis e itinerarios
        else if (selecionarCidade != null && roteiro != null && estadia != null) {
            Integer intCidadeId = Integer.parseInt(cidadeId);
            Cidade cidade = CidadeDAO.getInstance().getCidadeById(intCidadeId, true);
            List<Hotel> opcoesHoteis = HotelDAO.getInstance().getHoteisByCidade(intCidadeId);
            List<Itinerario> opcoesItinerarios = new ArrayList<Itinerario>();

            if (configurandoOrigem) { // está setand origem, devo configurar itinerarios entre inicio e origem
                opcoesItinerarios = ItinerarioDAO.getInstance().getItnerariosEntreCidades(roteiro.getCidadeInicial().getId(), intCidadeId);
            } else if (configurandoDestino){  // está setando a cidade destino, devo configurar itinerarios entre destino e inicio
                opcoesItinerarios = ItinerarioDAO.getInstance().getItnerariosEntreCidades(intCidadeId, roteiro.getCidadeInicial().getId());
            } else if (configurandoIntermediaria) {    //  está setando alguma estadia intermediaria, devo observar a ultima estadia intermediaria
                opcoesItinerarios = ItinerarioDAO.getInstance().getItnerariosEntreCidades(roteiro.getUltimaEstadiaIntermediaria().getCidade().getId(), intCidadeId);
            }

            request.setAttribute("opcoesHoteis", opcoesHoteis);
            request.setAttribute("opcoesItinerarios", opcoesItinerarios);
            estadia.setCidade(cidade);
        }
        // Selecionou o hotel e itinerario para estadia, vou adicioná-la ao roteiro
        else if (finalizarEstadia != null && roteiro != null && estadia != null) {
            Integer intHotelId = Integer.parseInt(hotelId);


            if (configurandoDestino) {
                Integer intItinerarioVoltaId = Integer.parseInt(itinerarioVoltaId);
                Itinerario itinerarioVolta = ItinerarioDAO.getInstance().getItinerarioById(intItinerarioVoltaId);
                estadia.setItinerarioVolta(itinerarioVolta);
            } else {
                Integer intItinerarioId = Integer.parseInt(itinerarioId);
                Itinerario itinerario = ItinerarioDAO.getInstance().getItinerarioById(intItinerarioId);
                estadia.setItinerario(itinerario);
            }

            Hotel hotel = HotelDAO.getInstance().getHotelById(intHotelId);
            estadia.setHotel(hotel);
            roteiro.adicionarEstadia(estadia);

            List<Cidade> opcoesCidades;

            if (configurandoOrigem) { // setou origem, agora as opcoes são os possiveis destinos
                opcoesCidades = CidadeDAO.getInstance().getCidadesQueAcessamCidade(roteiro.getCidadeInicial().getId());
            } else {// setou destino ou intermediaria, agora as opções são as entre a penultima cidade
                opcoesCidades = CidadeDAO.getInstance().getCidadesAcessiveisPorCidade(roteiro.getUltimaEstadiaIntermediaria().getCidade().getId());
            }


            Boolean podeFinalizar = false;
            if (roteiro.getEstadias().size() >= 2) {
                Cidade ultimaIntermediaria = roteiro.getUltimaEstadiaIntermediaria().getCidade();
                Cidade destino = roteiro.getDestino().getCidade();
                podeFinalizar = ultimaIntermediaria.temItinerarioPara(destino);

                if (podeFinalizar) {
                    List<Itinerario> itinerariosParaDestino = ItinerarioDAO.getInstance().getItnerariosEntreCidades(ultimaIntermediaria.getId(), destino.getId());
                    request.setAttribute("opcoesItinerariosParaDestino", itinerariosParaDestino);
                }
            }


            request.setAttribute("opcoesCidades", opcoesCidades);
            request.setAttribute("podeFinalizar", podeFinalizar);
            session.setAttribute("estadia", new Estadia());
        }
        // Finalizou Roteiro
        else if (finalizarRoteiro != null && roteiro != null) {
            Integer intItinerarioParaDestinoId = Integer.parseInt(itinerarioParaDestinoId);
            Itinerario itinerario = ItinerarioDAO.getInstance().getItinerarioById(intItinerarioParaDestinoId);
            roteiro.getDestino().setItinerario(itinerario);
            request.setAttribute("roteiroFinalizado", true);
        }


        request.getRequestDispatcher("/montar-roteiro.jsp").forward(request, response);

    }
    protected void montarRoteiro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        request.getRequestDispatcher("/montar-roteiro.jsp").forward(request, response);
    }

    protected void salvarRoteiro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //  Parse request body
        String bodyString = reqBody(request);
        RequestJson body = gson.fromJson(bodyString, RequestJson.class);

        // Pegar instancias dos DAOs
        CidadeDAO cidadeDAO = CidadeDAO.getInstance();
        HotelDAO hotelDAO = HotelDAO.getInstance();
        ItinerarioDAO itinerarioDAO = ItinerarioDAO.getInstance();
        RoteiroDAO roteiroDAO = RoteiroDAO.getInstance();

        // Instanciar Roteiro...
        Cidade cidadeInicial = cidadeDAO.getCidadeById(body.cidadeInicial, true);
        Roteiro roteiro = new Roteiro(cidadeInicial);

        if (body.estadias.size() < 2) {
            throw new Error("Precisa de no mínimo 2 estadias (origem e destino)");
        }
        // Iterar nas estadias recebidas no request
        Integer i = 0;
        for (RequestJson.EstadiaJson estadiaJson : body.estadias) {
            // Instanciar cidade, hotel, itinerario (?????)
            Cidade cidade = cidadeDAO.getCidadeById(estadiaJson.cidadeId, true);
            Hotel hotel = hotelDAO.getHotelById(estadiaJson.hotelId);
            Date dataChegada = estadiaJson.dataChegada;
            Date dataSaida = estadiaJson.dataSaida;
            Itinerario itinerario = itinerarioDAO.getItinerarioById(estadiaJson.itinerarioId);

            // Instanciar estadia....
            Estadia estadia = new Estadia(cidade, hotel, itinerario, dataChegada, dataSaida);

            // Adicionar estadia ao roteiro
            // Se for o primeiro, é origem
            // Se for o ultimo, é destino
            // Se não, é uma estadia
            // Motivo: Há checagens especiais na origem e destino
            // Poderia deixar separado apenas o destino, mas por questão de organização, separei também a origem
            if (i == 0) {
                roteiro.setOrigem(estadia);
            } else if (i == body.estadias.size() - 1) {
                Itinerario itinerarioVolta = itinerarioDAO.getInstance().getItinerarioById(estadiaJson.itinerarioVoltaId);
                estadia.setItinerarioVolta(itinerarioVolta);
                roteiro.setDestino(estadia);
            } else {
                roteiro.adicionarEstadia(estadia);
            }

            i++;
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        switch (servletPath) {
            case "/montar-roteiro": this.montarRoteiro(request, response); break;
        }
    }
}
