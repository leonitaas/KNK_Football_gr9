
package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static String URL = "jdbc:mysql://localhost:3306/knk_football?user=root&password=leonita123&useSSL=false&allowPublicKeyRetrieval=true";
    private static String USER = "root";
    private static String PASSWORD = "leonita123";
    private static Connection connection = null;

    public static Connection getConnection(){
        if(connection == null){
            try {
                connection = DriverManager.getConnection(
                        URL, USER, PASSWORD
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
}
