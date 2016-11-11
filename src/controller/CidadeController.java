package controller;

import model.cidade.Cidade;
import model.cidade.CidadeDAO;
import model.cliente.Cliente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by rafa93br on 08/11/16.
 */
@WebServlet(name = "CidadeController")
public class CidadeController extends Controller {
    CidadeDAO dao = CidadeDAO.getInstance();

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

        log(servletPath);
        switch (servletPath) {
            case "/cidades": this.cidades(request, response); break;
            case "/cidade": this.cidade(request, response); break;
            case "/cidades-acessiveis-por-cidade": this.cidadesAcessiveisPorCidade(request, response); break;
            case "/cidades-que-acessam-cidade": this.cidadeQueAcessamCidade(request, response); break;
            //case "/cliente-by-cpf": this.clienteByCpf(request, response); break;
        }
    }

    private void cidadeQueAcessamCidade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        Integer cidadeId = Integer.parseInt(request.getParameter("cidade"));
        List<Cidade> cidades = dao.getCidadesQueAcessamCidade(cidadeId);
        writer.write(gson.toJson(cidades));
    }

    private void cidadesAcessiveisPorCidade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        Integer cidadeId = Integer.parseInt(request.getParameter("cidade"));
        List<Cidade> cidades = dao.getCidadesAcessiveisPorCidade(cidadeId);
        writer.write(gson.toJson(cidades));
    }
    /**
     * Devolver lista de cidades
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void cidades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        List<Cidade> lista = dao.getAll();
        writer.write(gson.toJson(lista));
    }

    /**
     * Devolver uma cidade especifico, por id
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void cidade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = (String) request.getParameter("id");
        PrintWriter writer = response.getWriter();
        Cidade cidade = null;
        if (id != null) {
            cidade = dao.getCidadeById(Integer.parseInt(id));
        }
        writer.write(gson.toJson(cidade));
    }

}
