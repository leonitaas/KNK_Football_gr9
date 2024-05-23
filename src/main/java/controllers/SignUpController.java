package controllers;


import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.dto.UserDto;
import service.UserService;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnCreate;

    @FXML
    private Label lblConfirmPassword;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblFirstName;

    @FXML
    private Label lblLastName;

    @FXML
    private Label lblPassword;

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
    private Text txtUserRegister;

    @FXML
    private void handleSignUp(ActionEvent ae){
        System.out.println("Attempting to sign up.");
        UserDto userSignUpData = new UserDto(
                this.txtFirstName.getText(),
                this.txtLastName.getText(),
                this.txtEmail.getText(),
                this.pwdPassword.getText(),
                this.pwdConfirmPassword.getText()
        );

        boolean response = UserService.signUp(userSignUpData);
        System.out.println("SignUp Response: " + response);

        if(response){
            System.out.println("Navigating to login page.");
            Navigator.navigate(ae, Navigator.LOGIN_PAGE);
        } else {
            System.out.println("SignUp failed.");
        }
    }


    @FXML
    private void handleCancel(ActionEvent ae){
        Navigator.navigate(ae, Navigator.LOGIN_PAGE);
    }

   // @Override
    //public void initialize(URL url, ResourceBundle resourceBundle) {
      //  if (resourceBundle != null) {
       //     this.txtEmail.setText(resourceBundle.getString("Email"));
        //} else {
          //  System.err.println("ResourceBundle is null");
       // }
   // }
//@Override
//public void initialize(URL url, ResourceBundle resourceBundle) {
//    this.lblEmail.setText(resourceBundle.getString("lblEmail"));
//
//}

}