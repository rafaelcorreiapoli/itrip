package controller;

import model.cidade.Cidade;
import model.cidade.CidadeDAO;
import model.hotel.Hotel;
import model.hotel.HotelDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by rafa93br on 09/11/16.
 */
public class HotelController extends Controller {
    HotelDAO dao = HotelDAO.getInstance();

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
            case "/hoteis-por-cidade": this.hoteisByCidade(request, response); break;
        }
    }

    private void hoteisByCidade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        Integer cidadeId = Integer.parseInt(request.getParameter("cidade"));
        List<Hotel> hoteis = dao.getHoteisByCidade(cidadeId);
        writer.write(gson.toJson(hoteis));
    }
}
