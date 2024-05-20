package controllers;


import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.dto.UserDto;
import service.UserService;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private PasswordField pwdConfirmPassword;

    @FXML
    private PasswordField pwdPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private void handleSignUp(ActionEvent ae){
        UserDto userSignUpData = new UserDto(
                this.txtFirstName.getText(),
                this.txtLastName.getText(),
                this.txtEmail.getText(),
                this.pwdPassword.getText(),
                this.pwdConfirmPassword.getText()
        );

        boolean response = UserService.signUp(userSignUpData);

        if(response){
            Navigator.navigate(ae, Navigator.LOGIN_PAGE);
        } else {
            //logic
        }

    }

    @FXML
    private void handleCancel(ActionEvent ae){
      Navigator.navigate(ae, Navigator.LOGIN_PAGE);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (resourceBundle != null) {
            this.txtEmail.setText(resourceBundle.getString("Email"));
        } else {
            System.err.println("ResourceBundle is null");
        }
    }

}
