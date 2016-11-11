package model;

import model.cidade.CidadeDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by rafa93br on 08/11/16.
 */
public class DAO {
    private static String USER = "t2g8";
    private static String PASSWORD = "U3v;Ax";
    private static String HOST = "143.107.102.14";
    private static Integer PORT = 3306;
    private static String DB = "mdb108";

    protected DAO() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    protected Connection getConexao() throws SQLException {
        return DriverManager.getConnection("jdbc:mariadb://" +
                HOST + ":" +
                PORT + "/" +
                DB + "?user=" +
                USER + "&password=" +
                PASSWORD
        );
    }

}
