package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.Payment;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.Shipment;
import co.edu.uniquindio.poo.neodelivery.model.User;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManagePayments;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class NequiController {

    @FXML private TextField txtNequiNumber;
    @FXML private PasswordField txtNequiCode;
    @FXML private Button btnPagarNequi;

    private DataBase db = DataBase.getInstance();

    private Shipment shipment;

    private User clientLogged;

    private AnchorPane mainContentNequi;

    void setMainContentNequi(AnchorPane mainContent) {
        this.mainContentNequi = mainContent;
    }

    void setClient(User client) {
        this.clientLogged = client;
    }

    void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    @FXML
    void validarPagoNequi(ActionEvent event) {
        String phone = txtNequiNumber.getText();
        String code = txtNequiCode.getText();

        if(phone.isEmpty() || code.isEmpty()) {
            Utils.showAlert("NEQUI", "Por favor, rellene todos los espacios.");
        }else if(code.length()!= 6) {
            Utils.showAlert("NEQUI", "El código es de 6 dígitos.");
        }else if(phone.length() != 10 ){
            Utils.showAlert("NEQUI", "Su número de celular debe contener 10 dígitos");
        }
        else{
        ManagePayments mp = new ManagePayments();

        Payment pago = mp.createAndProcessPayment(
                clientLogged,
                shipment,
                shipment.getCost(),
                "billetera digital",
                null,
                "nequi",
                phone + ":" + code
        );
        clientLogged.getShipmentsList().add(shipment);
        db.getListaEnvios().add(shipment);
        String montoMostrar = Utils.formatCOP(shipment.getCost());

        Utils.showAlert("NEQUI", "Sucessful payment\nOrder ID: "
                +pago.getIdPayment()+"\nAmount paid: "+montoMostrar);
        ManageShipmentsClientController shipmentsClientController = Utils.replaceMainContent(mainContentNequi, "manageShipmentsClient.fxml");
        shipmentsClientController.setClientLogged(clientLogged);
        shipmentsClientController.setMainContentManageShipments( mainContentNequi);
        }
    }

}
