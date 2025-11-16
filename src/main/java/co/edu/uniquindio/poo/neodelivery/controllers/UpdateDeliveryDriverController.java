package co.edu.uniquindio.poo.neodelivery.controllers;

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
        if (!email.equalsIgnoreCase(driverToUpdate.getEmail()) && isEmailRegistered(email)) {
            Utils.showAlert("ERROR", "Email already in use");
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

        try {
            ManageDeliveryDriverController controller = Utils.replaceMainContent(mainContent, "manageDeliveryDrivers(Admin).fxml");
            controller.setMainContent(mainContent);
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showAlert("ERROR", "Could not return to Manage Clients");
        }
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
