package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.EmailService;
import co.edu.uniquindio.poo.neodelivery.model.Payment;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.Shipment;
import co.edu.uniquindio.poo.neodelivery.model.User;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManagePayments;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.File;

public class DaviplataController {

    @FXML private TextField txtCedulaDaviplata;
    @FXML private TextField txtTelefonoDaviplata;
    @FXML private PasswordField txtClaveDaviplata;
    @FXML private Button btnPagarDaviplata;

    private AnchorPane mainContent;

    private Shipment shipment;

    private User clientLogged;

    private DataBase db = DataBase.getInstance();


    void setClient(User client) {
        this.clientLogged = client;
    }

    void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    void setMainContent(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }


    @FXML
    void validarPago(ActionEvent event) {
        String phone = txtTelefonoDaviplata.getText();
        String pin = txtClaveDaviplata.getText();
        String cedula = txtCedulaDaviplata.getText();

        if(phone.isEmpty() || pin.isEmpty() || cedula.isEmpty()) {
            Utils.showAlert("DAVIPLATA", "Por favor, rellene todos los espacios.");
        }else if(cedula.length()<5) {
            Utils.showAlert("DAVIPLATA", "Su cédula debe tener mínimo 5 caracteres.");
        }else if(phone.length() != 10) {
            Utils.showAlert("DAVIPLATA", "Su número de celular debe contener 10 dígitos");
        }else if(pin.length()!= 4) {
            Utils.showAlert("DAVIPLATA", "El pin debe tener 4 dígitos.");
        }else{
            ManagePayments mp = new ManagePayments();

            Payment pago = mp.createAndProcessPayment(
                    clientLogged,
                    shipment,
                    shipment.getCost(),
                    "billetera digital",
                    null,
                    "daviplata",
                    phone + ":" + pin
            );
            clientLogged.getShipmentsList().add(shipment);
            db.getListaEnvios().add(shipment);

            File pdfGenerado = Utils.createPaymentPDF(pago, shipment, clientLogged);

            if (clientLogged.isNotificationsEnabled()) {
                EmailService.sendEmailWithAttachment(clientLogged.getEmail(), "YOUR SHIPMENT INVOICE - NEO DELIVERY",
                        "Thank you for your purchase! Your invoice is attached.\n\nBe happy.", pdfGenerado);
            }


            InvoiceOptionsController invoiceController = Utils.replaceMainContent(mainContent, "invoiceOptions.fxml");
            invoiceController.setMainContent(mainContent);
            invoiceController.setData(clientLogged, shipment, pago);
        }


    }

}
