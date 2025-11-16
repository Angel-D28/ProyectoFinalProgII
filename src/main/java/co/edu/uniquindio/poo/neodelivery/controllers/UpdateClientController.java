package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.Address;
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

    DataBase db = DataBase.getInstance();

    private ManageUsers manageUsers = new ManageUsers();

    private User clientToUpdate;

    private AnchorPane mainContent;

    public void setContentUpdateClient(AnchorPane mainContent) {
        this.mainContent = mainContent;
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

        String name = this.txtClientNewName.getText() == null ? "" : this.txtClientNewName.getText().trim();
        String phoneNumber = this.txtClientNewNumber.getText() == null ? "" : this.txtClientNewNumber.getText().trim();
        String email = this.txtClientNewEmail.getText() == null ? "" : this.txtClientNewEmail.getText().trim();
        String address = this.txtClientNewAddress.getText() == null ? "" : this.txtClientNewAddress.getText().trim();
        String password = this.txtClientNewPassword.getText() == null ? "" : this.txtClientNewPassword.getText();
        String confirmPassword = this.txtConfirmClientPassword.getText() == null ? "" : this.txtConfirmClientPassword.getText();

        if (name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || address.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Utils.showAlert("WARNING", "Fill in all fields");
            return;
        }
        if (!email.equalsIgnoreCase(clientToUpdate.getEmail()) && isEmailRegistered(email)) {
            Utils.showAlert("ERROR", "Email already in use");
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

        try {
            ManageClientController controller = Utils.replaceMainContent(mainContent, "manageClient(Admin).fxml");
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