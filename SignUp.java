package com.sandeep.Commuto;

import com.sandeep.Commuto.Model.Datasource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp {


    @FXML
    private TextField nameField;
    @FXML
    private TextField emailIDField;
    @FXML
    private TextField phoneField;
    @FXML
    private Button nxtButton;


    @FXML
    public void initialize() {
        nxtButton.setDisable(true);
    }

    @FXML
    public void backButtonPushed(ActionEvent event) throws IOException {



        Parent signInPage = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene signInscene = new Scene(signInPage);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(signInscene);
        window.show();

    }

    @FXML
    public void continuePushed(ActionEvent event) throws IOException {

        String email = emailIDField.getText();
        String phone = phoneField.getText();
        String name = nameField.getText();

        ZYX h = new ZYX();

        if(Datasource.getInstance().mail(email))
        {
            if(Datasource.getInstance().phone(phone))
            {
                h.insertNewUserPart1(name, email, phone);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("signUp2.fxml"));
                Parent sp2Page = loader.load();

                Scene sp2scene = new Scene(sp2Page);

                SignUp2 controller = loader.getController();
                controller.p(email);

                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(sp2scene);
                window.show();
            }
            else
            {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Phone No. is already in use");
                errorAlert.setContentText("Please enter a different phone no.");
                errorAlert.showAndWait();
                phoneField.clear();
            }

        }
        else
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("EmailID is already in use");
            errorAlert.setContentText("Please enter a different email");
            errorAlert.showAndWait();
            emailIDField.clear();
        }




    }


    @FXML
    public void handleKeyReleased() {
        String name = nameField.getText();

        boolean disableButtons = name.isEmpty() || name.trim().isEmpty();


        String email = emailIDField.getText();
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);
        boolean emailIsInvalid = email.isEmpty() || email.trim().isEmpty() || !matcher.matches();


        String phoneNo = phoneField.getText();
        String regex2 = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";

        Pattern pat = Pattern.compile(regex2);
        Matcher matcher1 = pat.matcher(phoneNo);
        boolean phoneIsInvalid = phoneNo.isEmpty() || phoneNo.trim().isEmpty() || !matcher1.matches();

        nxtButton.setDisable(disableButtons || emailIsInvalid || phoneIsInvalid);
    }

}
