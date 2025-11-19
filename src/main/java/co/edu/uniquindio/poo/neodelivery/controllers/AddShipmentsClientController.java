package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.App.NeoDeliveryApp;
import co.edu.uniquindio.poo.neodelivery.model.*;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManagePayments;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageShipments;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AddShipmentsClientController {

    @FXML private ChoiceBox<String> boxFragile;
    @FXML private ChoiceBox<String> boxHasInsure;
    @FXML private ChoiceBox<String> boxIsPriority;
    @FXML private ChoiceBox<String> boxPaymentMethods;
    @FXML private ChoiceBox<String> boxToSign;
    @FXML private Button btnCancel;
    @FXML private Button btnGoPay;
    @FXML private TextField txtDestination;
    @FXML private TextField txtOrigin;
    @FXML private TextField txtVolume;
    @FXML private TextField txtWeigth;

    @FXML
    private Label txtAboutRates;

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
            if (txtDestination.getText().isEmpty() || txtWeigth.getText().isEmpty() || txtVolume.getText().isEmpty() ||
                    boxIsPriority.getValue().equals("Select one") || boxFragile.getValue().equals("Select one") ||
                    boxHasInsure.getValue().equals("Select one") || boxToSign.getValue().equals("Select one") ||
                    boxPaymentMethods.getValue().equals("Select a payment method")) {

                Utils.showAlert("ERROR", "Please fill all the fields and select all options.");
                return;
            }

            String paymentMethod = boxPaymentMethods.getValue().toLowerCase();

            Payment payment = switch(paymentMethod) {
                case "card payment" -> new CardPayment(0, "");
                case "daviplata" -> new DigitalWalletPayment(0, "Daviplata", "");
                case "nequi" -> new DigitalWalletPayment(0, "Nequi", "");
                case "cash" -> new CashPayment(0);
                default -> null;
            };

            if (payment == null) {
                Utils.showAlert("ERROR", "Seleccione un método de pago válido.");
                return;
            }

            double weight = Double.parseDouble(txtWeigth.getText());
            double volume = Double.parseDouble(txtVolume.getText());
            Address origin = new Address(txtOrigin.getText());
            Address destination = new Address(txtDestination.getText());

            Shipment shipment = manageShipments.createShipment(clientLogged, origin, destination, weight, volume,
                    boxHasInsure.getValue().equals("Yes"),
                    boxIsPriority.getValue().equals("Yes"),
                    boxToSign.getValue().equals("Yes"),
                    boxFragile.getValue().equals("Yes"),
                    payment);

            double finalCost = manageShipments.calculateShipmentCost(shipment, "peso");
            shipment.setCost(finalCost);
            shipment.setPayment(payment);
            shipment.addObserver(clientLogged);

            switch(paymentMethod) {
                case "card payment" -> {
                    CardPaymentController cardController = Utils.replaceMainContent(mainContent, "CardPayment.fxml");
                    if(cardController != null) {
                        cardController.setShipment(shipment);
                        cardController.setClient(clientLogged);
                        cardController.setMainContent(mainContent);
                    }
                }
                case "daviplata" -> {
                    DaviplataController daviplataController = Utils.replaceMainContent(mainContent, "DaviplataPayment.fxml");
                    if(daviplataController != null) {
                        daviplataController.setShipment(shipment);
                        daviplataController.setClient(clientLogged);
                        daviplataController.setMainContent(mainContent);
                    }
                }
                case "nequi" -> {
                    NequiController nequiController = Utils.replaceMainContent(mainContent, "NequiPayment.fxml");
                    if(nequiController != null) {
                        nequiController.setShipment(shipment);
                        nequiController.setClient(clientLogged);
                        nequiController.setMainContentNequi(mainContent);
                    }
                }
                case "cash" -> {
                    payment = managePayments.createAndProcessPayment(clientLogged, shipment, shipment.getCost(),
                            "efecty", null, null, null);

                    Utils.createPaymentPDF(payment, shipment, clientLogged);
                    DataBase.getInstance().saveToJson();
                    Utils.showAlert("SUCCESS", "Shipment and payment created successfully!");

                    ManageShipmentsClientController controller = Utils.replaceMainContent(mainContent, "manageShipmentsClient.fxml");
                    controller.setClientLogged(clientLogged);
                    controller.setMainContentManageShipments(mainContent);
                    controller.setShipment(shipment);
                }
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

    @FXML
    void OnClickedAboutRates(MouseEvent event) {
        try {
            String url = getClass()
                    .getResource("/NeoDeliveryInformation.html")
                    .toExternalForm();

            NeoDeliveryApp.getAppHostServices().showDocument(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onMouseEnteredAboutRates(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), txtAboutRates);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        scaleTransition.play();
    }

    @FXML
    void onMouseExitedAboutRates(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), txtAboutRates);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }


}
