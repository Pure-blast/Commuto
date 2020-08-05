package com.sandeep.Commuto;

import com.sandeep.Commuto.Model.Datasource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.text.DecimalFormat;

public class Wallet {

    @FXML
    private Label walletBalance;
    @FXML
    private Label namet;
    @FXML
    private TextField amountAddedField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button updateWalletButton;
    @FXML
    private Label insufficientBalance;
    @FXML
    private Label pass;




    public void addMoney(ActionEvent event) throws IOException {

        ZYX h = new ZYX();
        double bal = h.getWalletBalance(pass.getText());
        String username = pass.getText();
        double monAdd = Double.parseDouble(amountAddedField.getText());

        if(Datasource.getInstance().lg_t(username, passwordField.getText()))
        {
            if(bal+monAdd <= 999.99) {

                h.updateWalletBalance(username, (bal+monAdd));

                walletBalance.setText("$ "+h.getWalletBalance(username));

                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setHeaderText("Success");
                confirmAlert.setContentText("Your wallet balance has been successfully updated.");
                confirmAlert.showAndWait();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("trip.fxml"));
                Parent tripPage = loader.load();

                Scene tripscene = new Scene(tripPage);

                trip controller = loader.getController();
                controller.labelSet(pass.getText());

                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(tripscene);
                window.show();
            }
            else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Invalid Balance");
                errorAlert.setContentText("Your wallet balance cannot exceed $ 999.99");
                errorAlert.showAndWait();
                amountAddedField.clear();
                passwordField.clear();
            }

        }
        else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Invalid Password");
            errorAlert.setContentText("Please enter the correct password");
            errorAlert.showAndWait();
            passwordField.clear();
        }



    }

    public void backToTripPage(ActionEvent event) throws IOException {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("trip.fxml"));
        Parent tripPage = loader.load();

        Scene tripscene = new Scene(tripPage);

        trip controller = loader.getController();
        controller.labelSet(pass.getText());

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tripscene);
        window.show();

    }


    public void labelset(String text) {

        ZYX h = new ZYX();
        String s = h.setLabelasName(text);
        namet.setText(s);
        pass.setText(text);

        double balance = h.getWalletBalance(text);
        walletBalance.setText("$ "+ balance);
        if(balance < 300)
        {
            insufficientBalance.setOpacity(0.63);
        }
        else {
            insufficientBalance.setOpacity(0);
        }

    }

    @FXML
    public void hndle() {
        String amt = amountAddedField.getText();
        boolean disableButtons = amt.isEmpty() || amt.trim().isEmpty() ;
        passwordField.setDisable(disableButtons);
        String password = passwordField.getText();
        boolean disableB2 = password.isEmpty() || password.trim().isEmpty() || (password.length()<8);
        updateWalletButton.setDisable((disableButtons || disableB2));

    }
}
