package model.roteiro;

import model.DAO;
import model.cidade.CidadeDAO;
import model.estadia.Estadia;
import model.itnerario.ItnerarioDAO;

import java.sql.*;

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

    public Integer inserirRoteiro(Roteiro roteiro) {
        try {
            Connection connection = getConexao();
            Statement statement = connection.createStatement();

            String query = "INSERT INTO roteiro(cidade_inicial) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, roteiro.getCidadeInicial().getId());

            Integer roteiroId = preparedStatement.executeUpdate(query);

            if (roteiroId != null) {
                for (Estadia estadia : roteiro.getEstadias()) {
                    String queryEstadia = "INSERT INTO estadia(data_chegada, data_saida, custo, cidade_id, hotel_id, dias) VALUES(?,?,?,?,?,?)";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(queryEstadia);
                    preparedStatement1.setDate(1, new Date(estadia.getDataChegada().getTime()));
                    preparedStatement1.setDate(2, new Date(estadia.getDataSaida().getTime()));
                    preparedStatement1.setDouble(3, estadia.getCusto());
                    preparedStatement1.setInt(4, estadia.getCidade().getId());
                    preparedStatement1.setInt(5, estadia.getHotel().getId());
                    preparedStatement1.setInt(6, estadia.getDias());

                    Integer estadiaId = preparedStatement1.executeUpdate();
                    if (estadiaId == null) {
                        throw new Error("nao conseguiu salvar estadia");
                    }
                }
            } else {
                throw new Error("nao conseguiu salvar roteiro");
            }
            return roteiroId;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
