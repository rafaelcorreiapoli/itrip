package model.hotel;

import model.DAO;

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
        return new Hotel(rs.getInt("id"), rs.getString("nome"), rs.getDouble("preco_diaria"), rs.getString("endereco"));
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

}
