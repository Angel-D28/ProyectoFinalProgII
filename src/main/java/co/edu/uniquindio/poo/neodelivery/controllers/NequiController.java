package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.Payment;
import co.edu.uniquindio.poo.neodelivery.model.Shipment;
import co.edu.uniquindio.poo.neodelivery.model.User;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManagePayments;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class NequiController {

    @FXML private TextField txtNequiNumber;
    @FXML private PasswordField txtNequiCode;
    @FXML private Button btnPagarNequi;

    private Shipment shipment;

    private User clientLogged;

    private double amount;

    public void setAmount(double amount) {
        this.amount = amount;
    }

    void setClient(User client) {
        this.clientLogged = client;
    }

    void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    @FXML
    public void initialize() {
        btnPagarNequi.setOnAction(e -> pagar());
    }

    private void pagar() {
        String number = txtNequiNumber.getText();
        String code = txtNequiCode.getText();

        ManagePayments mp = new ManagePayments();

        Payment pago = mp.createAndProcessPayment(
                clientLogged,
                shipment,
                amount,
                "billetera digital",
                null,
                "nequi",
                number + ":" + code
        );

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resultado");
        alert.setHeaderText("Pago con Nequi");
        alert.setContentText("Estado: " + pago.getStatus());
        alert.show();
    }
}
