package model.itnerario;

import model.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafa93br on 09/11/16.
 */
public class ItnerarioDAO extends DAO {
    private static final ItnerarioDAO instance = new ItnerarioDAO();
    public ItnerarioDAO() {
        super();
    }

    public static ItnerarioDAO getInstance () {
        return instance;
    }

    private Itinerario createItnerarioFromRow(ResultSet rs) throws SQLException {
        return new Itinerario(rs.getInt("id"), rs.getString("meio_de_transporte"), rs.getDouble("custo"), rs.getInt("duracao"));
    }

    public List<Itinerario> getItnerariosEntreCidades(int cidadeA, int cidadeB) {
        List<Itinerario> itnerarios = new ArrayList<Itinerario>();

        try {
            Connection connection = getConexao();
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM itinerario WHERE parte_de = ? AND chega_em = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, cidadeA);
            preparedStatement.setInt(2, cidadeB);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                itnerarios.add(this.createItnerarioFromRow(rs));
            }

            connection.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return itnerarios;
    }
}
