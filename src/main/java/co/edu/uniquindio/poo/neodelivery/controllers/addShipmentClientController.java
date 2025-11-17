package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.*;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManagePayments;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageShipments;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class addShipmentClientController {

    @FXML
    private ChoiceBox<String> boxFragile;

    @FXML
    private ChoiceBox<String> boxHasInsure;

    @FXML
    private ChoiceBox<String> boxIsPriority;

    @FXML
    private ChoiceBox<String> boxPaymentMethods;

    @FXML
    private ChoiceBox<String> boxToSign;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnGoPay;

    @FXML
    private TextField txtDestination;

    @FXML
    private TextField txtOrigin;

    @FXML
    private TextField txtVolume;

    @FXML
    private TextField txtWeigth;

    private User clientLogged;

    private ManageShipments manageShipments = new ManageShipments();

    private ManagePayments managePayments = new ManagePayments();

    void setClient(User client) {
        this.clientLogged = client;
    }

    @FXML
    void initialize() {
        ChoiceBox<String>[] boxes = new ChoiceBox[]{boxIsPriority, boxFragile, boxHasInsure, boxToSign};
        for (ChoiceBox<String> box : boxes) {
            box.getItems().addAll("Yes", "No");
            box.setValue("Select one");
        }

        boxPaymentMethods.setItems(FXCollections.observableArrayList("Card payment", "Cash", "Daviplata", "Nequi"));
        boxPaymentMethods.setValue("Select a payment method");
    }

    @FXML
    void cancelCreate(ActionEvent event) {

    }

    @FXML
    void goPay(ActionEvent event) {
        try {
            if (txtOrigin.getText().isEmpty() || txtDestination.getText().isEmpty() ||
                    txtWeigth.getText().isEmpty() || txtVolume.getText().isEmpty() ||
                    boxIsPriority.getValue().equals("Select one") ||
                    boxFragile.getValue().equals("Select one") ||
                    boxHasInsure.getValue().equals("Select one") ||
                    boxToSign.getValue().equals("Select one") ||
                    boxPaymentMethods.getValue().equals("Select a payment method")) {

                Utils.showAlert("ERROR", "Please fill all the fields and select all options.");
                return;
            }

            double weight = Double.parseDouble(txtWeigth.getText());
            double volume = Double.parseDouble(txtVolume.getText());
            Address origin = new Address(txtOrigin.getText());
            Address destination = new Address(txtDestination.getText());

            Shipment shipment = new Shipment.Builder()
                    .id(manageShipments.generateID())
                    .origin(origin)
                    .destination(destination)
                    .weight(weight)
                    .volume(volume)
                    .hasInsurance(boxHasInsure.getValue().equals("Yes"))
                    .isPriority(boxIsPriority.getValue().equals("Yes"))
                    .requiresSignature(boxToSign.getValue().equals("Yes"))
                    .fragile(boxFragile.getValue().equals("Yes"))
                    .build();

            clientLogged.getShipmentsList().add(shipment);
            DataBase.getInstance().getListaEnvios().add(shipment);

            double finalCost = manageShipments.calculateShipmentCost(shipment, "peso");
            shipment.setCost(finalCost);

            Payment payment = null;
            String paymentMethod = boxPaymentMethods.getValue();

            switch(paymentMethod.toLowerCase()){
                case "card payment":
                    //pagarTarjeta(clientLogged, shipment);
                    return;
                case "daviplata":
                    //pagarDaviplata(clientLogged, shipment, paymentMethod)
                case "nequi":
                    //pagarNequi(clientLogged, shipment, paymentMethod);
                    return;
                case "cash":
                    payment = managePayments.createAndProcessPayment(clientLogged, shipment,
                            shipment.getCost(), "efecty", null, null, null);
                    break;
                default:
                    Utils.showAlert("ERROR","Please select a payment method");
                    return;
            }

            Utils.createPaymentPDF(payment, shipment, clientLogged);
            shipment.setPayment(payment);
            shipment.addObserver(clientLogged);
            clientLogged.getPaymentsMethodsList().add(payment);

            Utils.showAlert("SUCCESS", "Shipment and payment created successfully!");

            txtOrigin.clear();
            txtDestination.clear();
            txtWeigth.clear();
            txtVolume.clear();
            boxIsPriority.setValue("Select one");
            boxFragile.setValue("Select one");
            boxHasInsure.setValue("Select one");
            boxToSign.setValue("Select one");
            boxPaymentMethods.setValue("Select a payment method");

        } catch (NumberFormatException e) {
            Utils.showAlert("ERROR", "Weight and volume must be numbers.");
        } catch (Exception e) {
            Utils.showAlert("ERROR", "Something went wrong: " + e.getMessage());
        }
    }

}
