package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.ActivityLogService;
import co.edu.uniquindio.poo.neodelivery.model.Address;
import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.User;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageUsers;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class UpdateClientController {

    @FXML
    private Button btnCancelUpdate;

    @FXML
    private Button btnUpdateClient;

    @FXML
    private TextField txtClientNewAddress;

    @FXML
    private TextField txtClientNewEmail;

    @FXML
    private TextField txtClientNewName;

    @FXML
    private TextField txtClientNewNumber;

    @FXML
    private PasswordField txtClientNewPassword;

    @FXML
    private PasswordField txtConfirmClientPassword;

    private ManageUsers manageUsers = new ManageUsers();

    private User clientToUpdate;

    private AnchorPane mainContent;

    private Admin adminLogged;

    public void setContentUpdateClient(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    public void setAdminLogged(Admin adminLogged) {
        this.adminLogged = adminLogged;
    }

    public void setClient(User client) {
        this.clientToUpdate = client;
        Address address = client.getAddress();
        if(client != null) {
            txtClientNewName.setText(client.getName());
            txtClientNewEmail.setText(client.getEmail());
            txtClientNewNumber.setText(client.getNumbre());
            txtClientNewAddress.setText(client.getAddress() != null ? address.getAddress() : "");

        }
    }

    @FXML
    void updateClient(ActionEvent event) {

        if (clientToUpdate == null) {
            Utils.showAlert("ERROR", "No client has been loaded for update.");
            return;
        }

        String name = txtClientNewName.getText() == null ? "" : txtClientNewName.getText().trim();
        String phoneNumber = txtClientNewNumber.getText() == null ? "" : txtClientNewNumber.getText().trim();
        String email = txtClientNewEmail.getText() == null ? "" : txtClientNewEmail.getText().trim();
        String address = txtClientNewAddress.getText() == null ? "" : txtClientNewAddress.getText().trim();
        String password = txtClientNewPassword.getText() == null ? "" : txtClientNewPassword.getText();
        String confirmPassword = txtConfirmClientPassword.getText() == null ? "" : txtConfirmClientPassword.getText();

        if (name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || address.isEmpty()
                || password.isEmpty() || confirmPassword.isEmpty()) {
            Utils.showAlert("WARNING", "Fill in all fields");
            return;
        }

        if (!phoneNumber.matches("^3\\d{9}$")) {
            Utils.showAlert("ERROR", "Phone number must start with 3 and have 10 digits");
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(gmail|hotmail|outlook|yahoo)\\.com$")) {
            Utils.showAlert("ERROR", "Email must be Gmail, Hotmail, Outlook, or Yahoo");
            return;
        }

        if (!email.equalsIgnoreCase(clientToUpdate.getEmail()) && Utils.isEmailRegistered(email)) {
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

        User updatedUser = new User(
                name,
                Utils.hashPassword(password),
                email,
                new Address(address),
                phoneNumber,
                clientToUpdate.getIdUser()
        );

        manageUsers.updateUser(clientToUpdate.getIdUser().toString(), updatedUser);
        Utils.showAlert("VERIFIED", "Successfully updated");

        ActivityLogService.log(adminLogged.getName(),
                "Updated client ID: " + clientToUpdate.getIdUser()
        );

        try {
            ManageClientController controller =
                    Utils.replaceMainContent(mainContent, "manageClient(Admin).fxml");
            controller.setMainContent(mainContent);
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showAlert("ERROR", "Could not return to Manage Clients");
        }
    }


    @FXML
    void cancelUpdate(ActionEvent event) {
        ManageClientController controller = Utils.replaceMainContent(mainContent, "manageClient(Admin).fxml");
        controller.setMainContent(mainContent);
    }


}