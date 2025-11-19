package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.ActivityLogService;
import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.DeliveryDriver;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageDeliveryDrivers;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UpdateDeliveryDriverController {

    @FXML
    private Button btnCancelUpdate;

    @FXML
    private Button btnUpdateDriver;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtNewDriverEmail;

    @FXML
    private TextField txtNewDriverName;

    @FXML
    private PasswordField txtNewPassword;

    ManageDeliveryDrivers manageDrives = new ManageDeliveryDrivers();

    private AnchorPane mainContent;

    private DeliveryDriver driverToUpdate;

    private Admin adminLogged;

    public void setAdminLogged(Admin adminLogged) {
        this.adminLogged = adminLogged;
    }

    public void setDriverToUpdate(DeliveryDriver driverToUpdate){
        this.driverToUpdate=driverToUpdate;

        if(driverToUpdate!=null){
            txtNewDriverEmail.setText(driverToUpdate.getEmail());
            txtNewDriverName.setText(driverToUpdate.getName());
        }
    }

    public void setContentUpdateDriver(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }


    @FXML
    void cancelUpdate(ActionEvent event) {
        ManageDeliveryDriverController controller = Utils.replaceMainContent(mainContent, "manageDeliveryDrivers(Admin).fxml");
        controller.setMainContent(mainContent);
    }

    @FXML
    void updateDriver(ActionEvent event) {
        if (driverToUpdate == null) {
            Utils.showAlert("ERROR", "No driver has been loaded for update.");
            return;
        }

        String name = this.txtNewDriverName.getText() == null ? "" : this.txtNewDriverName.getText().trim();
        String email = this.txtNewDriverEmail.getText() == null ? "" : this.txtNewDriverEmail.getText().trim();
        String password = this.txtNewPassword.getText() == null ? "" : this.txtNewPassword.getText();
        String confirmPassword = this.txtConfirmPassword.getText() == null ? "" : this.txtConfirmPassword.getText();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Utils.showAlert("WARNING", "Fill in all fields");
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(gmail|hotmail|outlook|yahoo)\\.com$")) {
            Utils.showAlert("ERROR", "Email must be Gmail, Hotmail, Outlook, or Yahoo");
            return;
        }

        if (!email.equalsIgnoreCase(driverToUpdate.getEmail()) && Utils.isEmailRegistered(email)) {
            Utils.showAlert("ERROR", "Email already in use");
            return;
        }

        if (password.length() < 8) {
            Utils.showAlert("ERROR", "Password must be at least 8 characters!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            Utils.showAlert("ERROR", "Passwords don't match");
            return;
        }

        DeliveryDriver updatedDriver = new DeliveryDriver(
                driverToUpdate.getId(),
                name,
                Utils.hashPassword(password),
                email
        );

        manageDrives.updateDeliveryDriverr(driverToUpdate.getId().toString(), updatedDriver);
        Utils.showAlert("VERIFIED", "Successfully updated");

        ActivityLogService.log(adminLogged.getName(),
                "Updated delivery driver - Name: " + name + " ID: " + driverToUpdate.getId()
        );

        try {
            ManageDeliveryDriverController controller =
                    Utils.replaceMainContent(mainContent, "manageDeliveryDrivers(Admin).fxml");
            controller.setMainContent(mainContent);
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showAlert("ERROR", "Could not return to Manage Clients");
        }
    }


}
