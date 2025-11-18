package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.Address;
import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.DeliveryDriver;
import co.edu.uniquindio.poo.neodelivery.model.User;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageAdmin;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageDeliveryDrivers;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageUsers;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class RegisterViewController {
    private final ManageUsers manageUser = new ManageUsers();
    private final ManageAdmin manageAdmin = new ManageAdmin();
    private final ManageDeliveryDrivers manageDriver = new ManageDeliveryDrivers();
    @FXML
    private Button btnRegister;
    @FXML
    private ChoiceBox<String> choiceBoxRol;
    @FXML
    private Hyperlink hyperLLogin;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextField txtName;
    @FXML
    private PasswordField txtPassword;

    @FXML
    public void initialize() {
        this.choiceBoxRol.setItems(FXCollections.observableArrayList(new String[]{"Client", "Delivery Driver"}));
        this.choiceBoxRol.getSelectionModel().clearSelection();
    }

    @FXML
    void goToLogin(ActionEvent event) {
        Utils.replaceScene(event, "loginView.fxml", "Login - Neo Delivery");
    }

    @FXML
    void register(ActionEvent event) {
        String name = txtName.getText() == null ? "" : txtName.getText().trim();
        String phoneNumber = txtPhoneNumber.getText() == null ? "" : txtPhoneNumber.getText().trim();
        String email = txtEmail.getText() == null ? "" : txtEmail.getText().trim();
        String address = txtAddress.getText() == null ? "" : txtAddress.getText().trim();
        String password = txtPassword.getText() == null ? "" : txtPassword.getText();
        String rol = (String) choiceBoxRol.getValue();

        if (name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || password.isEmpty()
                || rol == null || address.isEmpty()) {
            Utils.showAlert("WARNING", "Fill in all fields");
            return;
        }

        if (!name.matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ]+( [A-Za-zÁÉÍÓÚáéíóúñÑ]+)*$") || name.length() < 3) {
            Utils.showAlert("ERROR",
                    "Name must contain only letters, no double spaces, and be at least 3 characters");
            return;
        }

        if (!phoneNumber.matches("^3[1-9][0-9]{9}$")) {
            Utils.showAlert("ERROR",
                    "Phone number must start with 3 and contain exactly 10 digits");
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(gmail|hotmail|outlook|yahoo)\\.com$")) {
            Utils.showAlert("ERROR",
                    "Email must be Gmail, Hotmail, Outlook, or Yahoo");
            return;
        }

        if (Utils.isEmailRegistered(email)) {
            Utils.showAlert("ERROR", "Email already in use");
            return;
        }

        if (address.length() < 5 || address.matches(".*  +.*")) {
            Utils.showAlert("ERROR",
                    "Address must be at least 5 characters and cannot contain double spaces");
            return;
        }

        if (!password.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*+]).{8,}$")) {
            Utils.showAlert("ERROR",
                    "Password must be at least 8 chars,\ninclude a capital letter,\na number, and a special character (!@#$%^&*+)");
            return;
        }
        String hashedPassword = Utils.hashPassword(password);

        switch (rol) {
            case "Client" -> {
                String newId = manageUser.generateId();
                Address addressClient = new Address(address);
                User newUser = new User(name, hashedPassword, email, addressClient, phoneNumber, newId);
                newUser.getAddressList().add(addressClient);
                manageUser.createUser(newUser);
            }

            case "Delivery Driver" -> {
                String newId = manageDriver.generateId();
                DeliveryDriver newDeliveryDriver = new DeliveryDriver(newId, name, hashedPassword, email);
                manageDriver.createDeliveryDriver(newDeliveryDriver);
            }
        }

        Utils.showAlert("VERIFIED", "You have registered successfully | Welcome to Neo Delivery");
        Utils.replaceScene(event, "loginView.fxml", "Login - Neo Delivery");
    }

}