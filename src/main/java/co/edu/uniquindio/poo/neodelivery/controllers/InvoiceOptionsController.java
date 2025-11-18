package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.Payment;
import co.edu.uniquindio.poo.neodelivery.model.Shipment;
import co.edu.uniquindio.poo.neodelivery.model.User;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.File;

public class InvoiceOptionsController {

    @FXML
    private Button btnDownload;

    @FXML
    private Button btnContinue;

    private AnchorPane mainContent;
    private Shipment shipment;
    private User client;
    private Payment payment;

    public void setMainContent(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    public void setData(User client, Shipment shipment, Payment payment) {
        this.client = client;
        this.shipment = shipment;
        this.payment = payment;
    }

    @FXML
    private void initialize() {

        btnDownload.setOnAction(e -> {
            try {
                Utils.createPaymentPDF(payment, shipment, client);
                Utils.showAlert("SUCCESS", "Invoice downloaded successfully!");
                ManageShipmentsClientController controller = Utils.replaceMainContent(mainContent, "manageShipmentsClient.fxml");
                controller.setClientLogged(client);
                controller.setMainContentManageShipments(mainContent);
            } catch (Exception ex) {
                Utils.showAlert("ERROR", "Couldn't download invoice.");
            }
        });

        btnContinue.setOnAction(e -> {
            ManageShipmentsClientController controller =
                    Utils.replaceMainContent(mainContent, "manageShipmentsClient.fxml");
            controller.setClientLogged(client);
            controller.setMainContentManageShipments(mainContent);
        });
    }
}