package com.sandeep.Commuto;

import com.sandeep.Commuto.Model.Datasource;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fpswd {

    private String userName;

    @FXML
    private TextField userNameField;
    @FXML
    private TextField emailIDField;
    @FXML
    private Button UpdatePasswordButton;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private PasswordField ConfirmPasswordField;
    @FXML
    private Hyperlink back;
    @FXML
    private AnchorPane root;



    @FXML
    public void initialize() {


        ConfirmPasswordField.setDisable(true);
        PasswordField.setDisable(true);
        UpdatePasswordButton.setDisable(true);


    }




    @FXML
    public void updatePswd(ActionEvent event) throws IOException {

        if(Datasource.getInstance().mail(userNameField.getText(), emailIDField.getText()))
        {


                if(PasswordField.getText().equals(ConfirmPasswordField.getText()))
                {

                    //Update Password in the database
                    ZYX h = new ZYX();
                    h.updatePassword(userNameField.getText(), PasswordField.getText());


                    Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    errorAlert.setHeaderText("Update Successful");
                    errorAlert.setContentText("Your password has been updated successfully !");
                    errorAlert.showAndWait();

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
                    errorAlert.setHeaderText("Passwords don't match");
                    errorAlert.setContentText("You must confirm the password before updating");
                    errorAlert.showAndWait();
                    PasswordField.clear();
                    ConfirmPasswordField.clear();
                }

            }


        else
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Wrong Email or Username");
            errorAlert.setContentText("Please enter the correct username and email");
            errorAlert.showAndWait();
            userNameField.clear();
            emailIDField.clear();
            PasswordField.clear();
            ConfirmPasswordField.clear();
        }



    }

    @FXML
    public void signInBack(ActionEvent event) throws IOException {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        Parent signInPage = loader.load();

        Scene signInScene = new Scene(signInPage);

        Controller controller = loader.getController();
//        controller.userInit(userNameField.getText());
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(signInScene);
        window.show();

    }

    @FXML
    public void handleKeyReleased() {
        String email = emailIDField.getText();
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);
        boolean disableButtons = email.isEmpty() || email.trim().isEmpty() || !matcher.matches();

        String password = PasswordField.getText();
        boolean pswdFieldisEmpty = password.isEmpty() || password.trim().isEmpty();

        PasswordField.setDisable(disableButtons);
        ConfirmPasswordField.setDisable(disableButtons || pswdFieldisEmpty);

        String confirmPassword = ConfirmPasswordField.getText();
        boolean confirmPswdFieldisEmpty = confirmPassword.isEmpty() || confirmPassword.trim().isEmpty();


        UpdatePasswordButton.setDisable(disableButtons || pswdFieldisEmpty || confirmPswdFieldisEmpty);
    }


}
