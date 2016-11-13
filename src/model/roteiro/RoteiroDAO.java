package model.roteiro;

import model.DAO;
import model.cidade.CidadeDAO;
import model.estadia.Estadia;
import model.itnerario.ItnerarioDAO;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by rafa93br on 08/11/16.
 */
public class RoteiroDAO extends DAO {
    private static final RoteiroDAO instance = new RoteiroDAO();
    public RoteiroDAO() {
        super();
    }
    public static RoteiroDAO getInstance () {
        return instance;
    }

    public Roteiro inserirRoteiro(Roteiro roteiro) {
        try {
            Connection connection = getConexao();
            Statement statement = connection.createStatement();

            String query = "INSERT INTO roteiro(cidade_inicial_id, custo) values(?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, roteiro.getCidadeInicial().getId());
            preparedStatement.setDouble(2, roteiro.getCusto());
            System.out.println(roteiro.getCidadeInicial().getId());
            preparedStatement.execute();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            Integer roteiroId = null;
            if (rs.next()) {
                roteiroId = rs.getInt(1);
            }

            if (roteiroId == null) {
                throw new Error("Erro ao inserir roteiro.");
            }
            roteiro.setId(roteiroId);
            ArrayList<Estadia> estadias = roteiro.getTodasEstadias();
            System.out.print("Estadias size: " + estadias.size());
            for (Estadia estadia : estadias) {
                String queryEstadia = "INSERT INTO estadia(data_chegada, data_saida, custo, cidade_id, hotel_id, roteiro_id, itinerario_id, itinerario_volta_id, dias) VALUES(?,?,?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement1 = connection.prepareStatement(queryEstadia);
                preparedStatement1.setDate(1, new Date(estadia.getDataChegada().getTime()));
                preparedStatement1.setDate(2, new Date(estadia.getDataSaida().getTime()));
                preparedStatement1.setDouble(3, estadia.getCusto());
                preparedStatement1.setInt(4, estadia.getCidade().getId());
                preparedStatement1.setInt(5, estadia.getHotel().getId());
                preparedStatement1.setInt(6, roteiro.getId());
                preparedStatement1.setInt(7, estadia.getItinerario().getId());
                if (estadia.getItinerarioVolta() == null) {
                    preparedStatement1.setNull(8, Types.INTEGER);
                } else {
                    preparedStatement1.setInt(8, estadia.getItinerarioVolta().getId());
                }
                preparedStatement1.setInt(9, estadia.getDias());

                preparedStatement1.execute();
            }
            return roteiro;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
