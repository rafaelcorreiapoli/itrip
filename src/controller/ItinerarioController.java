package controller;

import model.itnerario.Itinerario;
import model.itnerario.ItinerarioDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by rafa93br on 09/11/16.
 */
public class ItinerarioController extends Controller {
    ItinerarioDAO dao = ItinerarioDAO.getInstance();

    /**
     * POST
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * GET
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        this.prepareResponse(response);

        switch (servletPath) {
            case "/itnerarios-entre-cidades": this.itnerariosEntreCidades(request, response); break;
        }
    }

    private void itnerariosEntreCidades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        Integer cidadeA = Integer.parseInt(request.getParameter("cidadeA"));
        Integer cidadeB = Integer.parseInt(request.getParameter("cidadeB"));
        List<Itinerario> itnerarios = dao.getItnerariosEntreCidades(cidadeA, cidadeB);
        writer.write(gson.toJson(itnerarios));
    }
}
