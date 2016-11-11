package controller;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by rafa93br on 08/11/16.
 */
public class Controller extends HttpServlet {
    Gson gson = new Gson();

    /**
     * Preparar response para ser do tipo JSON
     * @param response
     */
    protected void prepareResponse(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}
