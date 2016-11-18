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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        System.out.println("TESTE");
        String servletPath = request.getServletPath();
        System.out.println(servletPath);

        switch(servletPath) {
            case "/adicionar-cliente-post":
                String nome = (String) request.getParameter("nome");
                Date dataDeNascimento = null;
                try {
                     dataDeNascimento = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("data_nascimento"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Boolean sexo = request.getParameter("sexo").equals("1") ? true : false;
                String cpf = (String) request.getParameter("cpf");
                String email = (String) request.getParameter("email");
                String telefone = (String) request.getParameter("telefone");

                System.out.println("n" + nome);
                System.out.println(dataDeNascimento.toString());
                System.out.println("s" + sexo.toString());
                System.out.println(cpf);
                System.out.println("e" + email);
                System.out.println(telefone);

                Cliente novoCliente = new Cliente(cpf, nome, dataDeNascimento, sexo, email, telefone);
                Boolean sucesso = null;
                try {
                    ClienteDAO.getInstance().inserirCliente(novoCliente);
                    System.out.println("sucessoo");
                    sucesso = true;
                }  catch(Error e) {
                    System.out.println("erro :(");
                    sucesso = false;
                }

                request.setAttribute("resultado", sucesso);
                request.getRequestDispatcher("/adicionar-cliente-post.jsp").forward(request, response);
                break;
            case "/editar-cliente-post": this.editarClientePost(request, response); break;
            case "/deletar-cliente-post": this.deletarClientePost(request, response); break;

        }
    }

    private void deletarClientePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));

        Boolean resultado = ClienteDAO.getInstance().deletaCliente(id);
        request.setAttribute("resultado", resultado);
        request.getRequestDispatcher("/deletar-cliente-post.jsp").forward(request, response);
    }

    private void editarClientePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        String nome = (String) request.getParameter("nome");
        Date dataDeNascimento = null;
        try {
            dataDeNascimento = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("data_nascimento"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Boolean sexo = request.getParameter("sexo").equals("1") ? true : false;
        String cpf = (String) request.getParameter("cpf");
        String email = (String) request.getParameter("email");
        String telefone = (String) request.getParameter("telefone");

        System.out.println("n" + nome);
        System.out.println(dataDeNascimento.toString());
        System.out.println("s" + sexo.toString());
        System.out.println(cpf);
        System.out.println("e" + email);
        System.out.println(telefone);

        Cliente clienteEditado = new Cliente(id, cpf, nome, dataDeNascimento, sexo, email, telefone);

        Boolean resultado = ClienteDAO.getInstance().editarCliente(clienteEditado);
        request.setAttribute("resultado", resultado);
        request.getRequestDispatcher("/editar-cliente-post.jsp").forward(request, response);
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
            case "/listar-clientes":
                request.setAttribute("clientes", ClienteDAO.getInstance().getAll());
                request.getRequestDispatcher("/listar-clientes.jsp").forward(request, response);
                break;
            case "/adicionar-cliente":
                request.getRequestDispatcher("/adicionar-cliente.jsp").forward(request, response);
                break;
            case "/editar-cliente":
                Integer id = Integer.parseInt((String) request.getParameter("id"));
                Cliente cliente =  ClienteDAO.getInstance().getClienteById(id);
                request.setAttribute("cliente", cliente);
                request.getRequestDispatcher("/editar-cliente.jsp").forward(request, response);
                break;
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
        log(cpf);
        writer.write(gson.toJson(cliente));
    }
}
