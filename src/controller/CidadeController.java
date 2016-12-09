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

}
