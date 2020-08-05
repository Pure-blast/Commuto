package com.sandeep.Commuto;

import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;

import java.io.IOException;

public class FinalDetails {

    @FXML
    private Line collapseLine;
    @FXML
    private Line expandLine;
    @FXML
    private AnchorPane collapsibleAnchorPane;
    @FXML
    private Label driverName;
    @FXML
    private Label driverRating;
    @FXML
    private Label destinationLocation;
    @FXML
    private Label pickupLocation;
    @FXML
    private Label tripCost;
    @FXML
    private Label ETA;
    @FXML
    private Label carModel;
    @FXML
    private Label carID;
    @FXML
    private Label pass;
    @FXML
    private Label driver_phone;
    @FXML
    private Button end_call;
    @FXML
    private AnchorPane call_Screen;
    @FXML
    private Label MsgDriverName;
    @FXML
    private Label sent;
    @FXML
    private Label actualMessage;
    @FXML
    private TextField messageTextField;
    @FXML
    private AnchorPane messagePane;
    @FXML
    private Hyperlink msgclose;
    @FXML
    private AnchorPane RatingPopUp;
    @FXML
    private Slider rating;
    @FXML
    private Button submitRating;

    @FXML
    public void tripLabelSet(String pickup, String destination, String username) {

        pass.setText(username);
        ZYX h = new ZYX();
        pickupLocation.setText(pickup);
        destinationLocation.setText(destination);
        ETA.setText(h.getETAFromLocation(pickup, destination)+" min");
        tripCost.setText("$ "+h.getCostFromLocation(pickup, destination));

    }
    @FXML
    public void driverLabelSet(int did) {
        ZYX h = new ZYX();
        driverName.setText(h.getDriverName(did));
        MsgDriverName.setText(h.getDriverName(did));
        double r = h.getDriverRating(did);
        driverRating.setText(String.valueOf(r));
        carModel.setText(h.getCarModel(did));
        carID.setText(h.getCarID(did));
        driver_phone.setText(h.getDriverPhone(did));

    }

    @FXML
    public void initialize() {
        collapsibleAnchorPane.setVisible(true);
        call_Screen.setVisible(false);
        call_Screen.setDisable(true);
        messagePane.setVisible(false);
        messagePane.setDisable(true);
        RatingPopUp.setDisable(true);
        RatingPopUp.setVisible(false);
        expandLine.setOpacity(0.00);
    }


    @FXML
    public void backPushed(ActionEvent event) throws IOException {
        ZYX h = new ZYX();

        String usrname = pass.getText();
        String destination = destinationLocation.getText();
        String pickup = pickupLocation.getText();
        double x = h.getWalletBalance(usrname)-h.getCostFromLocation(pickup, destination);
        h.updateWalletBalance(usrname, x);
        Parent signInPage = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene signInscene = new Scene(signInPage);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(signInscene);
        window.show();
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

    @FXML
    public void call() {
        call_Screen.setVisible(true);
        call_Screen.setDisable(false);
        messagePane.setVisible(false);
        messagePane.setDisable(true);
    }
    @FXML
    public void end_call(ActionEvent event) {
        call_Screen.setVisible(false);
        call_Screen.setDisable(true);
    }
    @FXML
    public void message() {
        messagePane.setVisible(true);
        messagePane.setDisable(false);
        call_Screen.setVisible(false);
        call_Screen.setDisable(true);
        sent.setVisible(false);
        actualMessage.setVisible(false);
        messageTextField.clear();
    }
    @FXML
    public void closeMsg() {
        messagePane.setVisible(false);
        messagePane.setDisable(true);
        messageTextField.clear();
    }
    @FXML
    public boolean noMsg() {
        String msg = messageTextField.getText();
        if(msg.isEmpty() || msg.trim().isEmpty())
            return true;

        return false;

    }
    @FXML
    public void sendMsg() {

        if(!noMsg())
        {
            actualMessage.setText(messageTextField.getText());
            messageTextField.clear();
            actualMessage.setVisible(true);
            sent.setVisible(true);
        }

    }



    @FXML
    public void finish() {

        RatingPopUp.setDisable(false);
        RatingPopUp.setVisible(true);
        submitRating.setDisable(false);

        ZYX h = new ZYX();

        String usrname = pass.getText();
        String destination = destinationLocation.getText();
        String pickup = pickupLocation.getText();
        double x = h.getWalletBalance(usrname)-h.getCostFromLocation(pickup, destination);
        h.updateWalletBalance(usrname, x);
        h.RandomizeLocations();



    }

    @FXML
    public void submitrating() {
        double ratin = rating.getValue();
        String w = carID.getText();

        ZYX h = new ZYX();
        h.updateRating(w,ratin);


        System.exit(0);
    }

}
