package controller;

import com.google.gson.reflect.TypeToken;
import model.cidade.Cidade;
import model.cidade.CidadeDAO;
import model.estadia.Estadia;
import model.hotel.Hotel;
import model.hotel.HotelDAO;
import model.itnerario.Itinerario;
import model.itnerario.ItnerarioDAO;
import model.roteiro.Roteiro;
import model.roteiro.RoteiroDAO;
import sun.misc.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
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
        }
    }

    protected void salvarRoteiro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //  Parse request body
        String bodyString = reqBody(request);
        RequestJson body = gson.fromJson(bodyString, RequestJson.class);

        // Pegar instancias dos DAOs
        CidadeDAO cidadeDAO = CidadeDAO.getInstance();
        HotelDAO hotelDAO = HotelDAO.getInstance();
        ItnerarioDAO itinerarioDAO = ItnerarioDAO.getInstance();
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

        // Inserir roteiro na db
        roteiroDAO.inserirRoteiro(roteiro);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
