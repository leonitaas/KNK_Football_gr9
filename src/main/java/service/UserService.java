package service;
import models.User;
import models.dto.CreateUserDto;
import models.dto.LoginUserDto;
import models.dto.UserDto;
import models.filter.UserFilter;
import repository.UserRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
public class UserService {
    public UserService() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/knk_football", "root", "leonita123");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }public static boolean signUp(UserDto userData){
        String password = userData.getPassword();
        String confirmPassword = userData.getConfirmPassword();

        if(!password.equals(confirmPassword)){
            return false;
        }

        String salt = PasswordHasher.generateSalt();
        String passwordHash = PasswordHasher.generateSaltedHash(
                password, salt
        );

        CreateUserDto createUserData = new CreateUserDto(
                userData.getFirstName(),
                userData.getLastName(),
                userData.getEmail(),
                salt,
                passwordHash
        );

        return UserRepository.create(createUserData);
    }

    public static boolean login(LoginUserDto loginData){
        User user = UserRepository.getByEmail(loginData.getEmail());
        if(user == null){
            return false;
        }

        String password = loginData.getPassword();
        String salt = user.getSalt();
        String passwordHash = user.getPasswordHash();

        return PasswordHasher.compareSaltedHash(
                password, salt, passwordHash
        );
    }


    public static List<User> filter(UserFilter filter){
        return UserRepository.getByFilter(filter);
    }
}