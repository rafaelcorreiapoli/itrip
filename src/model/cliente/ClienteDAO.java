package model.cliente;

import model.DAO;

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
        return new Cliente(rs.getInt("id"), rs.getString("cpf"), rs.getString("nome"), rs.getDate("nascimento"), rs.getBoolean("sexo"), rs.getString("email"), rs.getString("telefone"));
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

    public Boolean deletaCliente(Cliente cliente){
        try {
            Connection connection = getConexao();
            String query = "DELETE FROM cliente WHERE cliente.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, cliente.getId());
            Boolean executou = preparedStatement.execute();

            connection.close();
            return  executou;
        } catch(SQLException e) {
            e.printStackTrace();
            throw new Error("Não conseguiu conexão");
        }
    }

    public Integer inserirCliente(Cliente cliente) {
        try {
            Connection connection = getConexao();
            Statement statement = connection.createStatement();

            String query = "INSERT INTO CLIENTE (nome, data_nascimento, sexo, cpf, email, telefone)" +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cliente.getNome());
            preparedStatement.setDate(2, new Date(cliente.getNascimento().getTime()));
            preparedStatement.setBoolean(3, cliente.getSexo());
            preparedStatement.setString(4, cliente.getCpf());
            preparedStatement.setString(5, cliente.getEmail());
            preparedStatement.setString(6, cliente.getTelefone());

            Integer clienteId = preparedStatement.executeUpdate();

            if (clienteId == null) {
                throw new Error("Não conseguiu salvar cliente");
            }

            connection.close();
            return clienteId;

        } catch(SQLException e) {
            e.printStackTrace();
            throw new Error("Não conseguiu conexão");
        }
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




