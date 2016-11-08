import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by rafa93br on 07/11/16.
 */
public class Database {
    public Database() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/DB?user=root&password=myPwd");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
