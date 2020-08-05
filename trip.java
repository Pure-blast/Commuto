package com.sandeep.Commuto;

import com.sandeep.Commuto.Model.Datasource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Calendar;

public class trip {

    @FXML
    private Label goodLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private ComboBox pickupLocationComboBox;
    @FXML
    private ComboBox destinationComboBox;
    @FXML
    private Button confirmTripButton;
    @FXML
    private Line collapseLine;
    @FXML
    private Line expandLine;
    @FXML
    private AnchorPane collapsibleAnchorPane;
    @FXML
    private Label pass;


    @FXML
    public void initialize() {
        Calendar c = Calendar.getInstance();
        int time = c.get(Calendar.HOUR_OF_DAY);

        if(time >= 0 && time < 12){
            goodLabel.setText("Good Morning,");
        }else if(time >= 12 && time < 16){
            goodLabel.setText("Good Afternoon,");
        }else
            goodLabel.setText("Good Evening,");


        confirmTripButton.setDisable(true);
        collapsibleAnchorPane.setVisible(true);
        expandLine.setOpacity(0.00);
    }


    public void labelSet(String text) {

        ZYX h = new ZYX();
        pass.setText(text);
        String s = h.setLabelasName(text);
        int i = s.indexOf(' ');
        String fname = s.substring(0, i);
        nameLabel.setText(fname);
    }



    public void finishTrip(ActionEvent event) throws IOException {

        ZYX h = new ZYX();

       double bal = h.getWalletBalance(pass.getText());
        String pickupLocation = (String)pickupLocationComboBox.getValue();
        String destination = (String)destinationComboBox.getValue();
        String usrname = pass.getText();

        double costOfTrip = h.getCostFromLocation(pickupLocation, destination);

        if(!pickupLocation.equals(destination)) {
            if(bal < 300 || bal < costOfTrip)
            {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Insufficient Balance");
                errorAlert.setContentText("You will be directed to your Wallet");
                errorAlert.showAndWait();
                FXMLLoader loader = new FXMLLoader();
               loader.setLocation(getClass().getResource("Wallet.fxml"));
                Parent walletPage = loader.load();

                Scene walletscene = new Scene(walletPage);

                Wallet controller = loader.getController();
                controller.labelset(pass.getText());

                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(walletscene);
                window.show();
            }
            else
                {
                    int lk = h.getDriverforUser(pickupLocation, destination);

                    if(lk > 0)
                    {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("finalDetails.fxml"));
                        Parent finalPage = loader.load();

                        Scene finalscene = new Scene(finalPage);

                        FinalDetails controller = loader.getController();
                        controller.tripLabelSet(pickupLocation, destination, usrname);
                        controller.driverLabelSet(lk);

                        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                        window.setScene(finalscene);
                        window.show();
                    }
                    else {

                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setHeaderText("Request Timed Out");
                        errorAlert.setContentText("We are having trouble finding a ride for you. Please, Try Again");
                        errorAlert.showAndWait();
                        h.RandomizeLocations();
                    }




            }

        }
        else
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Invalid Destination or Pickup Location");
            errorAlert.setContentText("Please select a different pickup location or destination");
            errorAlert.showAndWait();

        }

    }


    public void checkWallet(ActionEvent event) throws IOException {



        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Wallet.fxml"));
        Parent walletPage = loader.load();

        Scene walletscene = new Scene(walletPage);

        Wallet controller = loader.getController();
        controller.labelset(pass.getText());

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(walletscene);
        window.show();
    }

    @FXML
    public void handle() {

        String pickupLocation = (String)pickupLocationComboBox.getValue();


        String dest = (String)destinationComboBox.getValue();

        boolean s = pickupLocation == null || dest== null;

        confirmTripButton.setDisable(s);




    }

    @FXML
    public void collapse() {

        collapsibleAnchorPane.setVisible(false);
        expandLine.setOpacity(1.00);

    }
    @FXML
    public void expand() {

        collapsibleAnchorPane.setVisible(true);
        expandLine.setOpacity(0.00);
    }



}
