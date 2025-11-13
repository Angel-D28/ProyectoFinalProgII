package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.DeliveryDriver;
import co.edu.uniquindio.poo.neodelivery.model.User;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageAdmin;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageDeliveryDrivers;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageUsers;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;

public class LoginViewController {
    ManageUsers manageUsers = new ManageUsers();
    ManageAdmin manageAdmin = new ManageAdmin();
    ManageDeliveryDrivers manageDeliveryDrivers = new ManageDeliveryDrivers();
    @FXML
    private Button btnLogin;
    @FXML
    private Hyperlink hyperLRegister;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtUsername;
    @FXML
    private BorderPane rootPane;

    @FXML
    void goToRegister(ActionEvent event) {
        Utils.replaceScene(event, "registerView.fxml", "Register - Neo Delivery");
    }

    @FXML
    void login(ActionEvent event) {
        String email = this.txtUsername.getText() == null ? "" : this.txtUsername.getText().trim();
        String password = this.txtPassword.getText() == null ? "" : this.txtPassword.getText();
        if (!email.isEmpty() && !password.isEmpty()) {
            DataBase db = DataBase.getInstance();
            String hashedInput = Utils.hashPassword(password);

            for(User user : db.getListaUsuarios()) {
                if (user.getEmail().toLowerCase().equals(email) && user.getPassword().equals(hashedInput)) {
                    Utils.replaceScene(event, "clientDashboard.fxml", "Dashboard - Client");
                    return;
                }
            }

            for(Admin admin : db.getListaAdmin()) {
                if (admin.getEmail().toLowerCase().equals(email) && admin.getPassword().equals(hashedInput)) {
                    Utils.replaceScene(event, "adminDashboard.fxml", "Dashboard - Admin");
                    return;
                }
            }

            for(DeliveryDriver driver : db.getListaRepartidores()) {
                if (driver.getEmail().toLowerCase().equals(email) && driver.getPassword().equals(hashedInput)) {
                    Utils.replaceScene(event, "courierDashboard.fxml", "Dashboard - Delivery Driver");
                    return;
                }
            }

            Utils.showAlert("ERROR", "Email or password incorrect");
        } else {
            (new Alert(AlertType.WARNING, "Please type your email and password", new ButtonType[0])).showAndWait();
        }
    }
}
