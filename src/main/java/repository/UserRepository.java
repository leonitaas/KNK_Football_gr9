package repository;
import database.DBConnection;
import models.User;
import models.dto.CreateUserDto;
import models.filter.UserFilter;
import service.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class UserRepository {
    public static boolean create(CreateUserDto userData){
    Connection conn = DBConnector.getConnection();
        System.out.println("Ka ardh deri ne repo");
        String domeni[] = userData.getEmail().split("@");
        String tabela = "";

        if(domeni[1].equals("admin.com")){
            tabela = "adminInfo";
        } else if (domeni[1].equals("football.com")) {
            tabela = "users";
        }

    String query = " INSERT INTO " + tabela + " (fname, lname, email, salt, passwordHash) VALUE (?, ?, ?, ?, ?);";
        System.out.println(query);
    //String query = "INSERT INTO USER VALUE (?, ?, ?, ?, ?)";
    try{
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, userData.getFirstName());
        pst.setString(2, userData.getLastName());
        pst.setString(3, userData.getEmail());
        pst.setString(4, userData.getSalt());
        pst.setString(5, userData.getPasswordHash());

        System.out.println("Domeni: " + domeni[1]);
        System.out.println("Tabela " + tabela);
        pst.execute();
        pst.close();
        conn.close();
        return true;
    }catch (Exception e){
        e.printStackTrace();
        return false;
    }

}


    public static User getByEmail(String email){
        String tabela = "";

        String domeni[] = email.split("@");
        if(domeni[1].equals("admin.com")){
            tabela = "adminInfo";
        } else if (domeni[1].equals("football.com")) {
            tabela = "users";
        }

        String query = "SELECT * FROM " + tabela + " WHERE email = ? LIMIT 1";
        Connection connection = DBConnector.getConnection();
        try{
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, email);
            ResultSet result = pst.executeQuery();
            if(result.next()){
                return getFromResultSet(result);
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }

    public static List<User> getByFilter(UserFilter filter){
        String query = "SELECT * FROM users WHERE 1 == 1";
        String filterQuery = filter.buildQuery();
        query += filterQuery;
        return null;
    }
    /*
    SELECT * FROM users WHERE 1 == 1
    AND user_name like l%;

     */

    private static User getFromResultSet(ResultSet result){
        try{
            int id = result.getInt("id");
            String firstName = result.getString("fname");
            String lastName = result.getString("lname");
            String email = result.getString("email");
            String salt = result.getString("salt");
            String passwordHash = result.getString("passwordHash");
            return new User(
                    id, firstName, lastName, email, salt, passwordHash
            );
        }catch (Exception e){
            return null;
        }
    }





}
