package model.cliente;

import model.DAO;
import model.cidade.Cidade;
import model.cidade.CidadeDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafa93br on 08/11/16.
 */
public class ClienteDAO extends DAO {
    private static final ClienteDAO instance = new ClienteDAO();
    public ClienteDAO() {
        super();
    }

    private Cliente createClienteFromRow(ResultSet rs) throws SQLException {
        return new Cliente(rs.getInt("id"), rs.getString("cpf"), rs.getString("nome"));
    }

    public List<Cliente> getAll() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try {

            Connection connection = getConexao();
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM cliente ORDER BY nome ASC";
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                System.out.println(rs.getInt("id"));
                clientes.add(this.createClienteFromRow(rs));
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    public Cliente getClienteById(int id) {
        Cliente cliente = null;
        try {
            Connection connection = getConexao();
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM cliente WHERE id = ? ? ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                cliente = this.createClienteFromRow(rs);
            }

            connection.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }

    public Cliente getClienteByCpf(String cpf) {
        Cliente cliente = null;
        try {
            Connection connection = getConexao();
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM cliente WHERE cpf = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cpf);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                cliente = this.createClienteFromRow(rs);
            }

            connection.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }

    public static ClienteDAO getInstance() {
        return instance;
    }
}




