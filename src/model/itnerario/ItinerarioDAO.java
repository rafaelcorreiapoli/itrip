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

    public List<Itinerario> getAll() {
        ArrayList<Itinerario> itinerarios = new ArrayList<>();
        try {

            Connection connection = getConexao();
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM itinerario";
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                itinerarios.add(this.createItnerarioFromRow(rs));
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itinerarios;
    }

    public List<Itinerario> getAllMenorCusto() {
        List<Itinerario> itinerarios = new ArrayList<Itinerario>();

        try {
            Connection connection = getConexao();
            Statement statement = connection.createStatement();

            //String query = "SELECT id, meio_de_transporte, MIN(custo) as custo, duracao, chega_em, parte_de FROM itinerario GROUP BY parte_de, chega_em";
            String query = "SELECT itinerario_a.* " +
                    "FROM itinerario itinerario_a " +
                    "WHERE custo = (" +
                    "SELECT MIN(custo) FROM itinerario itinerario_b WHERE itinerario_a.parte_de = itinerario_b.parte_de AND itinerario_a.chega_em = itinerario_b.chega_em " +
                    ") " +
                    "GROUP BY parte_de, chega_em";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                itinerarios.add(this.createItnerarioFromRow(rs));
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itinerarios;
    }

    public Itinerario getMenorCustoAviao(Cidade cidadeOrigem, Cidade cidadeDestino) {
        Itinerario itinerario = null;
        try {
            Connection connection = getConexao();

            String query = "SELECT * FROM itinerario WHERE parte_de = ? AND chega_em = ? AND meio_de_transporte=? ORDER BY custo ASC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, cidadeOrigem.getId());
            preparedStatement.setInt(2, cidadeDestino.getId());
            preparedStatement.setString(3, "Avião");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                itinerario = this.createItnerarioFromRow(rs);
            }

            connection.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return itinerario;
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
