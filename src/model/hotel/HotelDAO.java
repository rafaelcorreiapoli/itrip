package model.hotel;

import model.DAO;
import model.cidade.Cidade;
import model.cidade.CidadeDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafa93br on 08/11/16.
 */
public class HotelDAO extends DAO {
    private static final HotelDAO instance = new HotelDAO();
    public HotelDAO() {
        super();
    }

    public static HotelDAO getInstance () {
        return instance;
    }

    private Hotel createHotelFromRow(ResultSet rs) throws SQLException {
        Hotel hotel = new Hotel(rs.getInt("id"), rs.getString("nome"), rs.getDouble("preco_diaria"), rs.getString("endereco"));
        hotel.setCidade(CidadeDAO.getInstance().getCidadeById(rs.getInt("cidade_id"), true));

        return hotel;
    }

    public List<Hotel> getHoteisByCidade(int cidadeId) {
        List<Hotel> hoteis = new ArrayList<Hotel>();

        try {
            Connection connection = getConexao();
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM hotel WHERE cidade_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, cidadeId);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                hoteis.add(this.createHotelFromRow(rs));
            }

            connection.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return hoteis;
    }

    public Hotel getHotelById(int id) {
        Hotel hotel = null;
        try {
            Connection connection = getConexao();
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM hotel WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                hotel = this.createHotelFromRow(rs);
            }



            connection.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return hotel;
    }

}
