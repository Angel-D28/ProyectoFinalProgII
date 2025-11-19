package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.*;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManagePayments;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;

public class CardPaymentController {

    @FXML private TextField txtCardNumber;
    @FXML private TextField txtCardHolder;
    @FXML private TextField txtExpiry;
    @FXML private PasswordField txtCVV;
    @FXML private Button btnCancel;
    @FXML private Button btnPay;

    private DataBase db = DataBase.getInstance();
    private Shipment shipment;
    private User client;
    private AnchorPane mainContent;

    private ManagePayments managePayments = new ManagePayments();

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public void setMainContent(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    @FXML
    void cancel(ActionEvent event) {
        Utils.showAlert("WARNING", "Cancelando...");
        ManageShipmentsClientController shipmentsController = Utils.replaceMainContent(mainContent, "manageShipmentsClient.fxml");
        shipmentsController.setMainContentManageShipments(mainContent);
    }

    @FXML
    void pay(ActionEvent event) {
        try {
            if (txtCardNumber.getText().isEmpty() ||
                    txtCardHolder.getText().isEmpty() ||
                    txtExpiry.getText().isEmpty() ||
                    txtCVV.getText().isEmpty()) {

                Utils.showAlert("ERROR", "Please fill all fields.");
                return;
            }


            if (!txtExpiry.getText().matches("(0[1-9]|1[0-2])\\/\\d{2}")) {
                Utils.showAlert("ERROR", "Expiry format must be MM/YY");
                return;
            }

            if (!txtCardNumber.getText().matches("\\d{16}")) {
                Utils.showAlert("ERROR", "Card number must be 16 digits.");
                return;
            }

            if (!txtCVV.getText().matches("\\d{3}")) {
                Utils.showAlert("ERROR", "CVV must be 3 digits.");
                return;
            }

            Payment payment = managePayments.createAndProcessPayment(
                    client, shipment, shipment.getCost(),
                    "tarjeta", txtCardNumber.getText(),
                    txtExpiry.getText(), txtCVV.getText()
            );

            shipment.addObserver(client);

            File pdfGenerado = Utils.createPaymentPDF(payment, shipment, client);
            //DataBase.getInstance().saveToJson();

            if (client.isNotificationsEnabled()) {
                EmailService.sendEmailWithAttachment(client.getEmail(), "YOUR SHIPMENT INVOICE - NEO DELIVERY",
                        "Thank you for your purchase! Your invoice is attached.\n\nBe happy.", pdfGenerado);
            }

            InvoiceOptionsController invoiceController = Utils.replaceMainContent(mainContent, "invoiceOptions.fxml");
            invoiceController.setMainContent(mainContent);
            invoiceController.setData(client, shipment, payment);

        } catch (Exception e) {
            Utils.showAlert("ERROR", "Something went wrong: " + e.getMessage());
        }
    }
}
