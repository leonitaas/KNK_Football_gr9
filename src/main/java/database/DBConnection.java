package database;
import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnection {
    private static String URL = "jdbc:mysql://localhost:3306/dbFootball";
    private static String SERVER="localhost:3306";
    private static String USER = "root";
   private static String DATABASE = "knk_football";
    private static String PASSWORD = "leonita123";
    private static Connection connection ;

    public static Connection getConnection() {
        return (Connection) new DBConnection();
    }
    private DBConnection() {
        this.connection = this.initConnection();
    }
    private Connection initConnection() {
        try {
            Class.forName(DBConnection.URL);
            connection = DriverManager.getConnection( //per url
                    "jdbc:mysql://" + DBConnection.SERVER  + "/" + DBConnection.DATABASE, DBConnection.USER,
                    DBConnection.PASSWORD);
            return connection;
        } catch (Exception e) {
            System.out.println("DBConnection error : " + e.getMessage());
            return  null ;
        }
    }




}
