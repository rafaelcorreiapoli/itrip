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
public class ClienteController extends Controller {
    ClienteDAO dao = ClienteDAO.getInstance();

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
            case "/clientes": this.clientes(request, response); break;
            case "/cliente": this.cliente(request, response); break;
            case "/cliente-by-cpf": this.clienteByCpf(request, response); break;
        }
    }

    /**
     * Devolver lista de clientes
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void clientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        List<Cliente> lista = dao.getAll();
        writer.write(gson.toJson(lista));
    }

    /**
     * Devolver um cliente especifico, por id
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void cliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = (String) request.getParameter("id");
        PrintWriter writer = response.getWriter();
        Cliente cliente = null;
        if (id != null) {

            cliente = dao.getClienteById(Integer.parseInt(id));
        }
        writer.write(gson.toJson(cliente));
    }

    /**
     * Devolver um cliente pelo CPF
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    private void clienteByCpf(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cliente cliente = null;
        PrintWriter writer = response.getWriter();
        String cpf = (String) request.getParameter("cpf");
        if (cpf != null) {
            cliente = dao.getClienteByCpf(cpf);
        }
        writer.write(gson.toJson(cliente));
    }
}
