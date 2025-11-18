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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class AddShipmentsClientController {

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

    DataBase db = DataBase.getInstance();

    private User clientLogged;

    private AnchorPane mainContent;

    private ManageShipments manageShipments = new ManageShipments();

    private ManagePayments managePayments = new ManagePayments();

    void setClient(User client) {
        this.clientLogged = client;
        txtOrigin.setText(clientLogged.getAddress().toString());
        txtOrigin.setEditable(false);
    }

    void setMainContent(AnchorPane mainContent) {
        this.mainContent = mainContent;
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
        ManageShipmentsClientController controller = Utils.replaceMainContent(mainContent, "manageShipmentsClient.fxml");
        controller.setClientLogged(clientLogged);
        controller.setMainContentManageShipments(mainContent);
    }

    @FXML
    void goPay(ActionEvent event) {
        try {
            if (    txtDestination.getText().isEmpty() ||
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

            double finalCost = manageShipments.calculateShipmentCost(shipment, "peso");
            shipment.setCost(finalCost);

            Payment payment;
            String paymentMethod = boxPaymentMethods.getValue();

            switch(paymentMethod.toLowerCase()){
                case "card payment":
                    CardPaymentController cardController =
                            Utils.replaceMainContent(mainContent, "CardPayment.fxml");

                    if(cardController != null){
                        cardController.setShipment(shipment);
                        cardController.setClient(clientLogged);
                        cardController.setMainContent(mainContent);
                    }
                    break;
                case "daviplata":
                    DaviplataController daviplataController = Utils.replaceMainContent(mainContent, "DaviplataPayment.fxml");
                    if(daviplataController != null){
                        daviplataController.setShipment(shipment);
                        daviplataController.setClient(clientLogged);
                        daviplataController.setMainContent(mainContent);
                    }else{
                        Utils.showAlert("ERROR", "Could not load Daviplata payment view.");
                    }
                    break;
                case "nequi":
                    NequiController nequiController = Utils.replaceMainContent(mainContent, "NequiPayment.fxml");
                    if(nequiController != null){
                        nequiController.setShipment(shipment);
                        nequiController.setClient(clientLogged);
                        nequiController.setMainContentNequi(mainContent);
                    }else {
                        Utils.showAlert("ERROR", "Could not load Nequi payment view.");
                    }
                    break;
                case "cash":
                    payment = managePayments.createAndProcessPayment(clientLogged, shipment,
                            shipment.getCost(), "efecty", null, null, null);

                    Utils.createPaymentPDF(payment, shipment, clientLogged);
                    shipment.setPayment(payment);
                    shipment.addObserver(clientLogged);
                    clientLogged.getPaymentsMethodsList().add(payment);
                    clientLogged.getShipmentsList().add(shipment);
                    db.getListaEnvios().add(shipment);



                    Utils.showAlert("SUCCESS", "Shipment and payment created successfully!");

                    ManageShipmentsClientController controller = Utils.replaceMainContent(mainContent, "manageShipmentsClient.fxml");
                    controller.setClientLogged(clientLogged);
                    controller.setMainContentManageShipments(mainContent);
                    controller.setShipment(shipment);
                    break;
                default:
                    Utils.showAlert("ERROR","Please select a payment method");
            }


        } catch (NumberFormatException e) {
            Utils.showAlert("ERROR", "Weight and volume must be numbers.");
        } catch (Exception e) {
            Utils.showAlert("ERROR", "Something went wrong: " + e.getMessage());
        }
    }

    @FXML
    void messagePopUp(MouseEvent event) {
        Utils.showAlert("WARNING", "You cannot change your address in this field.\n\nChange your default address in 'addresses'.");
    }

}
