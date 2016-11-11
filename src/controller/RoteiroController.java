package controller;

import model.estadia.Estadia;
import model.itnerario.ItnerarioDAO;
import model.roteiro.Roteiro;
import model.roteiro.RoteiroDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by rafa93br on 08/11/16.
 */
@WebServlet(name = "RoteiroController")
public class RoteiroController extends Controller {
    RoteiroDAO dao = RoteiroDAO.getInstance();

    class EstadiaJson {
        String hotelId;
        String cidadeId;
        Date dataChegada;
        Date dataSaida;
        Double custo;
        Integer dias;
        String itinerarioid;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        this.prepareResponse(response);

        switch (servletPath) {
            case "/salvar-roteiro": this.salvarRoteiro(request, response); break;
        }
    }

    protected void salvarRoteiro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer cidadeInicial = Integer.parseInt(request.getParameter("cidadeInicial"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
