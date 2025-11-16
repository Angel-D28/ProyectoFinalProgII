package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.Address;
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

    private final ManageUsers manageUser = new ManageUsers();

    private AnchorPane mainContent;

    public void setContentAddClient(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    @FXML
    void cancelCreateClient(ActionEvent event) {
        ManageClientController controller = Utils.replaceMainContent(mainContent, "manageClient(Admin).fxml");
        controller.setMainContent(mainContent);
    }

    @FXML
    void createClient(ActionEvent event) {
        String name = this.txtClientName.getText() == null ? "" : this.txtClientName.getText().trim();
        String phoneNumber = this.txtClientNumber.getText() == null ? "" : this.txtClientNumber.getText().trim();
        String email = this.txtClientEmail.getText() == null ? "" : this.txtClientEmail.getText().trim();
        String address = this.txtAddressClient.getText() == null ? "" : this.txtAddressClient.getText().trim();
        String password = this.txtPasswordClient.getText() == null ? "" : this.txtPasswordClient.getText();

        if(!name.isEmpty() && !phoneNumber.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            if(isEmailRegistered(email)){
                Utils.showAlert("ERROR", "Email is already registered!");
            } else if (txtPasswordClient.getLength() < 8) {
                Utils.showAlert("ERROR", "Password must be at least 8 characters!");
            } else{
                String clientId = manageUser.generateId();
                String hashedPassword = Utils.hashPassword(password);
                Address clientAddress = new Address(address);

                User registerUser = new User(name, hashedPassword, email, clientAddress, phoneNumber, clientId);
                manageUser.createUser(registerUser);
                Utils.showAlert("VERIFIED", "Client successfully registered.");

                try {
                    ManageClientController controller = Utils.replaceMainContent(mainContent, "manageClient(Admin).fxml");
                    controller.setMainContent(mainContent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Utils.showAlert("ERROR", "Could not return to Manage Clients");
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
