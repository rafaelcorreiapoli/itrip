package model.cidade;

import model.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CidadeDAO extends DAO {
    private static final CidadeDAO instance = new CidadeDAO();
    public CidadeDAO() {
        super();
    }

    private Cidade createCidadeFromRow(ResultSet rs) throws SQLException {
        return new Cidade(rs.getInt("id"), rs.getString("nome"), rs.getBoolean("tem_aeroporto"), rs.getInt("numero_dias_ideal"));
    }
    public List<Cidade> getAll() {
        ArrayList<Cidade> cidades = new ArrayList<>();
        try {

            Connection connection = getConexao();
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM cidade ORDER BY nome ASC";
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                cidades.add(this.createCidadeFromRow(rs));
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cidades;
    }

    public Cidade getSingle(int id) {
        Cidade cidade = null;
        try {
            Connection connection = getConexao();
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM cidade WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                cidade = this.createCidadeFromRow(rs);
            }

            connection.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return cidade;
    }

    public CidadeDAO getInstance () {
        return instance;
    }
}
