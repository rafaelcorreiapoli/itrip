package controller;

import model.cidade.Cidade;
import model.cliente.Cliente;
import com.google.gson.Gson;
import model.cliente.ClienteDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafa93br on 04/11/16.
 */
@WebServlet(name = "ClienteController")
public class ClienteController extends HttpServlet {

    private void prepareResponse(HttpServletResponse response) {
        response.setContentType("application/json");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        PrintWriter writer = response.getWriter();


        String servletPath = request.getServletPath();
        log(servletPath);
        Cliente cliente = null;
        this.prepareResponse(response);

        ClienteDAO dao = ClienteDAO.getInstance();

        switch (servletPath) {
            case "/clientes":
                List<Cliente> lista = dao.getAll();
                writer.write(gson.toJson(lista));
                break;
            case "/cliente":
                String id = (String) request.getParameter("id");

                if (id != null) {
                    cliente = dao.getClienteById(Integer.parseInt(id));
                }
                writer.write(gson.toJson(cliente));
                break;
            case "/cliente-by-cpf":
                String cpf = (String) request.getParameter("cpf");
                if (cpf != null) {
                    cliente = dao.getClienteByCpf(cpf);
                }
                writer.write(gson.toJson(cliente));
                break;
        }
    }



}
