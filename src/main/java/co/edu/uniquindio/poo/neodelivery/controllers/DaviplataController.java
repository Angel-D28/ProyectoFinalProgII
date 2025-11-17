package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.Payment;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManagePayments;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DaviplataController {

    @FXML private TextField txtCedulaDaviplata;
    @FXML private TextField txtTelefonoDaviplata;
    @FXML private PasswordField txtClaveDaviplata;
    @FXML private Button btnPagarDaviplata;

    private double amount;

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @FXML
    public void initialize() {
        btnPagarDaviplata.setOnAction(e -> pagar());
    }

    private void pagar() {
        String phone = txtTelefonoDaviplata.getText();
        String pin = txtClaveDaviplata.getText();

        ManagePayments mp = new ManagePayments();

        Payment pago = mp.createAndProcessPayment(
                amount,
                "billetera digital",
                null,
                "daviplata",
                phone + ":" + pin
        );

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resultado");
        alert.setHeaderText("Pago con Daviplata");
        alert.setContentText("Estado: " + pago.getStatus());
        alert.show();
    }
}
