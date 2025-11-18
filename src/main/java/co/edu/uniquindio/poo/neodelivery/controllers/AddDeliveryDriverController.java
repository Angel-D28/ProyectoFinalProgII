package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.ActivityLogService;
import co.edu.uniquindio.poo.neodelivery.model.Admin;
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

import javax.lang.model.type.NullType;

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

    private Admin adminLogged;

    public void setContentAddDriver(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    public void setAdminL(Admin admin){
        this.adminLogged = admin;
    }

    @FXML
    void cancelCreate(ActionEvent event) {
        ManageDeliveryDriverController controller = Utils.replaceMainContent(mainContent, "manageDeliveryDrivers(Admin).fxml");
        controller.setMainContent(mainContent);
    }

    @FXML
    void createDriver(ActionEvent event) {
        String name = txtDriverName.getText() == null ? "" : txtDriverName.getText().trim();
        String email = txtDriverEmail.getText() == null ? "" : txtDriverEmail.getText().trim();
        String password = txtDriverPassword.getText() == null ? "" : txtDriverPassword.getText();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Utils.showAlert("WARNING", "Please, fill in all fields");
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(gmail|hotmail|outlook|yahoo)\\.com$")) {
            Utils.showAlert("ERROR", "Email must be Gmail, Hotmail, Outlook, or Yahoo");
            return;
        }

        if (Utils.isEmailRegistered(email)) {
            Utils.showAlert("ERROR", "Email is already registered!");
            return;
        }

        if (password.length() < 8) {
            Utils.showAlert("ERROR", "Password must be at least 8 characters!");
            return;
        }

        String driverId = manageDriver.generateId();
        String hashedPassword = Utils.hashPassword(password);

        DeliveryDriver driver = new DeliveryDriver(driverId, name, hashedPassword, email);
        manageDriver.createDeliveryDriver(driver);

        Utils.showAlert("VERIFIED", "Driver successfully registered.");
        ActivityLogService.log(adminLogged.getName(),
                "Created driver - Name: " + name + " ID: " + driverId);

        try {
            ManageDeliveryDriverController controller =
                    Utils.replaceMainContent(mainContent, "manageDeliveryDrivers(Admin).fxml");
            controller.setMainContent(mainContent);
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showAlert("ERROR", "Could not return to Manage Delivery Drivers");
        }
    }


}
