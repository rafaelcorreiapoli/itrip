package model.cidade;

import model.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CidadeDAO extends DAO {
    public CidadeDAO() {
        super();
    }

    public List<Cidade> getAll() {
        ArrayList<Cidade> cidades = new ArrayList<>();
        try {

            Connection connection = getConexao();
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM cidades ORDER BY nome ASC";
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                cidades.add(new Cidade(rs.getInt("id"), rs.getString("nome"), rs.getBoolean("tem_aeroporto"), rs.getInt("numero_dias_ideal")));
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cidades;
    }
}
