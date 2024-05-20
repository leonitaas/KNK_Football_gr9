package controllers;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.User;
import models.dto.LoginUserDto;
import service.UserService;

import java.util.Locale;
public class LoginController {
    @FXML
    private PasswordField pwdPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private void handleLoginClick(ActionEvent ae){
        LoginUserDto loginUserData = new LoginUserDto(
                this.txtEmail.getText(),
                this.pwdPassword.getText()
        );

        boolean isLogin = UserService.login(loginUserData);

        String domeni[] =  this.txtEmail.getText().split("@");
        System.out.println(domeni[1]);
        if(domeni[1].equals("admin.com") && isLogin){
            Navigator.navigate(ae,Navigator.View_Page);
            System.out.println("Ju jeni admin");
        } else if (domeni[1].equals("football.com") && isLogin) {
            System.out.println("Ju jeni user");
        } else if (!isLogin) {
            System.out.println("Jepni kredencialet korrekte");
        }

//        User user = UserService.login(loginUserData);
//        if(user != null){
//            SessionManager.setUser(user);


    }

    @FXML
    private void handleCancelClick(ActionEvent ae){
        Locale locale = Locale.of("de");
        Locale.setDefault(locale);
        Locale.setDefault(new Locale("de", "DE")); // set default locale to German
    }

    @FXML
    private void handleCreateAccountClick(MouseEvent me){
        Navigator.navigate(me, Navigator.CREATE_ACCOUNT_PAGE);
    }


    @FXML
    private void handleLanguageClick(ActionEvent ae){
        Locale defaultLocale = Locale.getDefault();
        if(defaultLocale.getLanguage().equals("sq")){

        }else{

        }
    }

}
