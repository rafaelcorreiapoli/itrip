package model.cidade;

import model.DAO;
import model.itnerario.Itinerario;
import model.itnerario.ItinerarioDAO;

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

    public List<Cidade> getCidadesQueAcessamCidade(Integer cidadeId, Boolean necessitaAeroporto) {
        ArrayList<Cidade> cidades = new ArrayList<>();

        try {
            Connection connection = getConexao();
            Statement statement = connection.createStatement();

            String query;
            if (necessitaAeroporto) {
                query = "SELECT C.id, nome, tem_aeroporto, numero_dias_ideal FROM itinerario I, cidade C WHERE I.chega_em = ? AND I.parte_de = C.id AND tem_aeroporto=TRUE AND I.meio_de_transporte='Avião' GROUP BY parte_de";
            } else {
                query = "SELECT C.id, nome, tem_aeroporto, numero_dias_ideal FROM itinerario I, cidade C WHERE I.chega_em = ? AND I.parte_de = C.id GROUP BY parte_de";
            }
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, cidadeId);

            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {
                System.out.println(">>" + rs.getInt("id") + rs.getString("nome"));
                cidades.add(this.createCidadeFromRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cidades;
    }

    public List<Cidade> getCidadesAcessiveisPorCidade(Integer cidadeId, Boolean necessitaAeroporto) {
        ArrayList<Cidade> cidades = new ArrayList<>();

        try {
            Connection connection = getConexao();
            Statement statement = connection.createStatement();

            String query;
            if (necessitaAeroporto) {
                query = "SELECT C.id, nome, tem_aeroporto, numero_dias_ideal FROM itinerario I, cidade C WHERE I.parte_de = ? AND I.chega_em = C.id AND tem_aeroporto=TRUE AND I.meio_de_transporte='Avião' GROUP BY chega_em";
            } else {
                query = "SELECT C.id, nome, tem_aeroporto, numero_dias_ideal FROM itinerario I, cidade C WHERE I.parte_de = ? AND I.chega_em = C.id GROUP BY chega_em";
            }
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, cidadeId);

            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {
                cidades.add(this.createCidadeFromRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cidades;
    }
    public Cidade getCidadeById(int id, boolean fetchItinerarios) {
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


            if (fetchItinerarios) {
                List<Itinerario> itinerarios = ItinerarioDAO.getInstance().getItnerariosPartindoDaCidade(cidade);
                cidade.setItinerarios(itinerarios);
            }


            connection.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return cidade;
    }

    public static CidadeDAO getInstance () {
        return instance;
    }
}
