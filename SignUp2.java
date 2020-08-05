package com.sandeep.Commuto;

import com.sandeep.Commuto.Model.Datasource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp2 {

    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField ConfirmPasswordField;
    @FXML
    private CheckBox termsCheckbox;
    @FXML
    private Button getStartedButton;
    @FXML
    private Label pass;


    @FXML
    public void initialize() {
        passwordField.setDisable(true);
        ConfirmPasswordField.setDisable(true);
        getStartedButton.setDisable(true);
    }

    @FXML
    public void p(String text) {
        pass.setText(text);
    }

    @FXML
    public void getStartedPushed(ActionEvent event) throws IOException {



        if(!Datasource.getInstance().lg_t(userNameField.getText()))
        {
            if(passwordField.getText().equals(ConfirmPasswordField.getText()))
            {
                if(termsCheckbox.isSelected())
                {
                    String email = pass.getText();
                    String username = userNameField.getText();
                    String password = passwordField.getText();
                    ZYX h = new ZYX();
                    h.insertNewUserPart2(username, password, email);
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("sample.fxml"));
                    Parent signInPage = loader.load();

                    Scene signInScene = new Scene(signInPage);

                    Controller controller = loader.getController();

                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(signInScene);
                    window.show();
                }
                else
                {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Terms haven't been accepted");
                    errorAlert.setContentText("You must agree to terms before creating a new account");
                    errorAlert.showAndWait();
                }

            }
            else
            {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Passwords don't match");
                errorAlert.setContentText("You must confirm the password before updating");
                errorAlert.showAndWait();
                ConfirmPasswordField.clear();
            }
        }
        else
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Username already taken");
            errorAlert.setContentText("Enter an unique username");
            errorAlert.showAndWait();
            userNameField.clear();
            passwordField.clear();
            ConfirmPasswordField.clear();
        }



    }

    @FXML
    public void handleKeyReleased() {
        String userName = userNameField.getText();

        boolean disableButtons = userName.isEmpty() || userName.trim().isEmpty();



        passwordField.setDisable(disableButtons);

        String password = passwordField.getText();
        boolean pswdFieldisEmpty = password.isEmpty() || password.trim().isEmpty() || password.length()<8;

        ConfirmPasswordField.setDisable(disableButtons || pswdFieldisEmpty);

        String confirmPassword = ConfirmPasswordField.getText();
        boolean confirmPswdFieldisEmpty = confirmPassword.isEmpty() || confirmPassword.trim().isEmpty();

        getStartedButton.setDisable(disableButtons || pswdFieldisEmpty || confirmPswdFieldisEmpty);
    }
}
