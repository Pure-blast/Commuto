package com.sandeep.Commuto;

import com.sandeep.Commuto.Model.Datasource;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

public class Controller {

    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signInButton;
    @FXML
    private Hyperlink forgotPassword;
    @FXML
    private Hyperlink signUp;
    @FXML
    private AnchorPane root;


    @FXML
    public void initialize() {
        signInButton.setDisable(true);
        forgotPassword.setDisable(true);
        passwordField.setDisable(true);
    }

    //Simple Disable Stuff
    @FXML
    public void handleKeyReleased() {
        String userName = userNameField.getText();
        String password = passwordField.getText();
        boolean disableButtons = userName.isEmpty() || userName.trim().isEmpty();
        boolean disableB2 = password.isEmpty() || password.trim().isEmpty() || (password.length()<8);
        signInButton.setDisable((disableButtons || disableB2));
        forgotPassword.setDisable(disableButtons);
        passwordField.setDisable(disableButtons);
    }

    @FXML
    public void signUpButtonPushed(ActionEvent event) throws IOException {



        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("signUp.fxml"));
        Parent signUpPage = loader.load();

        Scene signUpscene = new Scene(signUpPage);


        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(signUpscene);
        window.show();

    }


    //Sign In Button is Pushed
    @FXML
    public void signInButtonPushed(ActionEvent event) throws IOException {

        if(Datasource.getInstance().lg_t(userNameField.getText(), passwordField.getText()))
        {
                            FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("trip.fxml"));
                Parent tripPage = loader.load();

                Scene tripscene = new Scene(tripPage);

                trip controller = loader.getController();
                controller.labelSet(userNameField.getText());

                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(tripscene);
                window.show();
        }
        else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Invalid Credentials...");
            errorAlert.setContentText("Please enter the correct username and password");
            errorAlert.showAndWait();
            userNameField.clear();
            passwordField.clear();
        }

    }

    //Forgot Password is Clicked
    @FXML
    public void Fpswd(ActionEvent event) throws IOException {


        if(Datasource.getInstance().lg_t(userNameField.getText()))
        {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Fpswd.fxml"));
            Parent FpswdPage = loader.load();

            Scene Fpswdscene = new Scene(FpswdPage);

            Fpswd controller = loader.getController();
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(Fpswdscene);
            window.show();


        }
        else
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Invalid Username");
            errorAlert.setContentText("Please enter a valid username");
            errorAlert.showAndWait();
            userNameField.clear();
            passwordField.clear();
        }

    }

}
