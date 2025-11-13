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
        this.choiceBoxRol.setItems(FXCollections.observableArrayList(new String[]{"Administrator", "Client", "Delivery Driver"}));
        this.choiceBoxRol.getSelectionModel().clearSelection();
    }

    @FXML
    void goToLogin(ActionEvent event) {
        Utils.replaceScene(event, "loginView.fxml", "Login - Neo Delivery");
    }

    @FXML
    void register(ActionEvent event) {
        String name = this.txtName.getText() == null ? "" : this.txtName.getText().trim();
        String phoneNumber = this.txtPhoneNumber.getText() == null ? "" : this.txtPhoneNumber.getText().trim();
        String email = this.txtEmail.getText() == null ? "" : this.txtEmail.getText().trim();
        String address = this.txtAddress.getText() == null ? "" : this.txtAddress.getText().trim();
        String password = this.txtPassword.getText() == null ? "" : this.txtPassword.getText();
        String rol = (String)this.choiceBoxRol.getValue();
        if (!name.isEmpty() && !phoneNumber.isEmpty() && !email.isEmpty() && !password.isEmpty() && rol != null) {
            if (this.isEmailRegistered(email)) {
                (new Alert(AlertType.WARNING, "Email registrado", new ButtonType[0])).showAndWait();
            } else {
                String hashedPassword = Utils.hashPassword(password);
                switch (rol) {
                    case "Administrator" -> {
                        String newId = this.manageAdmin.generateId();
                        Admin admin = new Admin(newId, name, email, phoneNumber, hashedPassword);
                        this.manageAdmin.createAdmin(admin);
                    }

                    case "Client"->{
                        String newId = this.manageUser.generateId();
                        Address addressClient = new Address(address);
                        User newUser = new User(name, hashedPassword, email, addressClient, phoneNumber, newId);
                        this.manageUser.createUser(newUser);
                    }

                    case "Delivery Driver"->{
                        String newId = this.manageDriver.generateId();
                        DeliveryDriver newDeliveryDriver = new DeliveryDriver(newId, name, hashedPassword, email);
                        this.manageDriver.createDeliveryDriver(newDeliveryDriver);
                    }

                }

                (new Alert(AlertType.INFORMATION, "Registro exitoso. Ya puedes iniciar Sesion :)", new ButtonType[0])).showAndWait();
                Utils.replaceScene(event, "loginView.fxml", "Login - Neo Delivery");
            }
        } else {
            (new Alert(AlertType.WARNING, "Completa todos los campos", new ButtonType[0])).showAndWait();
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