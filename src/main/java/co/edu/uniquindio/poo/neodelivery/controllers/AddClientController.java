package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.ActivityLogService;
import co.edu.uniquindio.poo.neodelivery.model.Address;
import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.User;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageUsers;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class AddClientController {

    @FXML
    private Button btnCancelCreateClient;

    @FXML
    private Button btnCreateClient;

    @FXML
    private BorderPane rootPane;

    @FXML
    private TextField txtAddressClient;

    @FXML
    private TextField txtClientEmail;

    @FXML
    private TextField txtClientName;

    @FXML
    private TextField txtClientNumber;

    @FXML
    private PasswordField txtPasswordClient;

    private Admin admin;

    private final ManageUsers manageUser = new ManageUsers();

    private AnchorPane mainContent;

    public void setContentAddClient(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    public void setAdminLogged(Admin admin) {
        this.admin = admin;
    }

    @FXML
    void cancelCreateClient(ActionEvent event) {
        ManageClientController controller = Utils.replaceMainContent(mainContent, "manageClient(Admin).fxml");
        controller.setMainContent(mainContent);
    }

    @FXML
    void createClient(ActionEvent event) {

        String name = txtClientName.getText() == null ? "" : txtClientName.getText().trim();
        String phoneNumber = txtClientNumber.getText() == null ? "" : txtClientNumber.getText().trim();
        String email = txtClientEmail.getText() == null ? "" : txtClientEmail.getText().trim();
        String address = txtAddressClient.getText() == null ? "" : txtAddressClient.getText().trim();
        String password = txtPasswordClient.getText() == null ? "" : txtPasswordClient.getText();

        if (name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Utils.showAlert("WARNING", "Please, fill in all fields");
            return;
        }

        if (!name.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{2,}$")) {
            Utils.showAlert("ERROR", "Invalid name. Only letters and spaces allowed.");
            return;
        }

        if (!phoneNumber.matches("^3\\d{9}$")) {
            Utils.showAlert("ERROR", "Phone number must start with 3 and contain 10 digits.");
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(gmail|hotmail|outlook|yahoo)\\.com$")) {
            Utils.showAlert("ERROR",
                    "Email must be Gmail, Hotmail, Outlook, or Yahoo");
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

        if (address.length() < 3) {
            Utils.showAlert("ERROR", "Address seems too short.");
            return;
        }

        String clientId = manageUser.generateId();
        String hashedPassword = Utils.hashPassword(password);
        Address clientAddress = new Address(address);

        User registerUser = new User(name, hashedPassword, email, clientAddress, phoneNumber, clientId);
        manageUser.createUser(registerUser);
        registerUser.getAddressList().add(clientAddress);
        DataBase.getInstance().saveToJson();

        Utils.showAlert("VERIFIED", "Client successfully registered.");
        ActivityLogService.log(admin.getName(), "Created client: " + name + ", ID: " + clientId);

        try {
            ManageClientController controller = Utils.replaceMainContent(mainContent, "manageClient(Admin).fxml");
            controller.setMainContent(mainContent);
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showAlert("ERROR", "Could not return to Manage Clients");
        }
    }


}
