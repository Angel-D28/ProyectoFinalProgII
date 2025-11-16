package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.DeliveryDriver;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageDeliveryDrivers;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class AddDeliveryDriverController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnCreateDriver;

    @FXML
    private BorderPane rootPane;

    @FXML
    private TextField txtDriverEmail;

    @FXML
    private TextField txtDriverName;

    @FXML
    private PasswordField txtDriverPassword;

    private final ManageDeliveryDrivers manageDriver = new ManageDeliveryDrivers();

    private AnchorPane mainContent;

    public void setContentAddDriver(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    @FXML
    void cancelCreate(ActionEvent event) {
        ManageDeliveryDriverController controller = Utils.replaceMainContent(mainContent, "manageDeliveryDrivers(Admin).fxml");
        controller.setMainContent(mainContent);
    }

    @FXML
    void createDriver(ActionEvent event) {
        String name = this.txtDriverName.getText() == null ? "" : this.txtDriverName.getText().trim();
        String email = this.txtDriverEmail.getText() == null ? "" : this.txtDriverEmail.getText().trim();
        String password = this.txtDriverPassword.getText() == null ? "" : this.txtDriverPassword.getText();

        if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            if(isEmailRegistered(email)){
                Utils.showAlert("ERROR", "Email is already registered!");
            } else if (txtDriverPassword.getLength() < 8) {
                Utils.showAlert("ERROR", "Password must be at least 8 characters!");
            } else{
                String driverId = manageDriver.generateId();
                String hashedPassword = Utils.hashPassword(password);

                DeliveryDriver driver = new DeliveryDriver(driverId, name,  email, hashedPassword);
                manageDriver.createDeliveryDriver(driver);
                Utils.showAlert("VERIFIED", "Client successfully registered.");

                try {
                    ManageClientController controller = Utils.replaceMainContent(mainContent, "manageDeliveryDrivers(Admin).fxml");
                    controller.setMainContent(mainContent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Utils.showAlert("ERROR", "Could not return to Manage Delivery Drivers");
                }
            }
        }else{Utils.showAlert("WARNING", "Please, fill in all fields");}
    }

    private boolean isEmailRegistered(String email) {
        String lowerEmail = email.toLowerCase();
        DataBase db = DataBase.getInstance();
        if (db.getListaUsuarios().stream().anyMatch((u) -> u.getEmail().toLowerCase().equals(lowerEmail))) {
            return true;
        } else if (db.getListaAdmin().stream().anyMatch((a) -> a.getEmail().toLowerCase().equals(lowerEmail))) {
            return true;
        } else {
            return db.getListaRepartidores().stream().anyMatch((d) -> d.getEmail().toLowerCase().equals(lowerEmail));
        }
    }

}
