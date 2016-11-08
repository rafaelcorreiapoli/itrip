import model.cliente.Cliente;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by rafa93br on 04/11/16.
 */
@WebServlet(name = "InserirCPF")
public class InserirCPF extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log(request.getParameter("cpf"));
        Gson gson = new Gson();
        ArrayList<Cliente> lista = new ArrayList<Cliente>();


        Cliente cliente = new Cliente("41649179898", "Rafael Ribeiro Correias");
        lista.add(cliente);
        lista.add(cliente);
        String json = gson.toJson(lista);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        ArrayList<Cliente> lista = new ArrayList<Cliente>();


        Cliente cliente = new Cliente("41649179898", "Rafael Ribeiro Correiassss");
        lista.add(cliente);
        lista.add(cliente);
        String json = gson.toJson(lista);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}
