package model.itnerario;

import model.DAO;
import model.cidade.Cidade;
import model.cidade.CidadeDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafa93br on 09/11/16.
 */
public class ItinerarioDAO extends DAO {
    private static final ItinerarioDAO instance = new ItinerarioDAO();
    public ItinerarioDAO() {
        super();
    }

    public static ItinerarioDAO getInstance () {
        return instance;
    }

    private Itinerario createItnerarioFromRow(ResultSet rs) throws SQLException {
        Cidade chegaEm = CidadeDAO.getInstance().getCidadeById(rs.getInt("chega_em"), false);
        Cidade parteDe = CidadeDAO.getInstance().getCidadeById(rs.getInt("parte_de"), false);
        Itinerario itinerario = new Itinerario(rs.getInt("id"), rs.getString("meio_de_transporte"), rs.getDouble("custo"), rs.getInt("duracao"));
        itinerario.setChegaEm(chegaEm);
        itinerario.setParteDe(parteDe);
        return itinerario;
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

    public Itinerario getItinerarioById(int id) {
        Itinerario itinerario = null;
        try {
            Connection connection = getConexao();
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM itinerario WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                itinerario = this.createItnerarioFromRow(rs);
            }

            connection.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return itinerario;
    }

    public List<Itinerario> getItnerariosPartindoDaCidade(Cidade cidade) {
       List<Itinerario> itinerarios = new ArrayList<Itinerario>();
        try {
            Connection connection = getConexao();
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM itinerario WHERE parte_de = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, cidade.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                itinerarios.add(this.createItnerarioFromRow(rs));
            }

            connection.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return itinerarios;
    }

}
