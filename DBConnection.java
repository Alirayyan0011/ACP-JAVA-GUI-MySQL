import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
