import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseConnection {
    private static Connection connection;

    public static Connection getConnectionToMySQL() throws Exception {
        if (connection == null) {
            String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
            String DB_URL = "jdbc:mysql://localhost:3306/academia";

            String USER = "root";
            String PASS = "Reus_01020";

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        }
        return connection;
    }
}
    // public static void main(String[] args) {
    // // JDBC driver name and database URL
    // String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    // String DB_URL =
    // "jdbc:sqlserver://localhost:1433;databaseName=mydatabase;encrypt=true;trustServerCertificate=true;";

    // String USER = "sa";
    // String PASS = "Root123456";

    // Connection conn = null;

    // try {
    // Class.forName(JDBC_DRIVER);

    // System.out.println("Connecting to database...");
    // conn = DriverManager.getConnection(DB_URL, USER, PASS);

    // System.out.println("Database connected.");

    // } catch (SQLException se) {
    // // Handle errors for JDBC
    // se.printStackTrace();
    // } catch (Exception e) {
    // // Handle errors for Class.forName
    // e.printStackTrace();
    // } finally {
    // // Close the connection
    // try {
    // if (conn != null) {
    // conn.close();
    // }
    // } catch (SQLException se) {
    // se.printStackTrace();
    // }
    // }
    // System.out.println("Database connection closed.");
    // }
