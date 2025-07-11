package sa.com.kranthi.e2eAutomation.backend.foundation.mysqlConfigs;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.*;

public class DBMethods { // 37 usages & kranthi +9

    public static Connection connection; // 15 usages
    public static Statement statement; // 47 usages
    public static ResultSet resultSet; // 9 usages

    public static Statement setup(String JDBC_URL, String JDBC_USER, String JDBC_PASSWORD) { // & kranthi
        // Establish connection to MySQL
        try {
            System.out.println("Creating Database Connection.");
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            statement = connection.createStatement();
            System.out.println("Database Connection Created.");
            return statement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void updateDB(String query) { // @T.Ucoops @kranthi +1
        try {
            if (statement == null) {
                System.out.println("MySQL Test: Statement is null!");
            } else {
                System.out.println("Update Query is: " + query);
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}