package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static String URL = "jdbc:mysql://localhost:3306/knk_project";
    private static String USER = "root";
    private static String PASSWORD = "root";
    private static Connection connection = null;

    public static Connection getConnection(){
        if(connection == null){
            try {
                connection = DriverManager.getConnection(
                        URL, USER, PASSWORD
                );
            } catch (SQLException e) {
//                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
}