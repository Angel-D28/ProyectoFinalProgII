package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageAdmin;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class AddAdminController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnCreateAdmin;

    @FXML
    private BorderPane rootPane;

    @FXML
    private TextField txtEmailAdmin;

    @FXML
    private TextField txtNameAdmin;

    @FXML
    private TextField txtNumberAdmin;

    @FXML
    private PasswordField txtPasswordAdmin;

    private Admin adminLogged;

    public void adminLogged(Admin adminLogged) {
        this.adminLogged = adminLogged;
    }

    ManageAdmin manageAdmin = new ManageAdmin();

    private AnchorPane mainContent;

    public void setContentAddAdmin(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    @FXML
    void cancelCreate(ActionEvent event) {
        ManageAdminController controller = Utils.replaceMainContent(mainContent, "manageAdmin.fxml");
        controller.setMainContent(mainContent);
    }

    @FXML
    void createAdmin(ActionEvent event) {
        String name = this.txtNameAdmin.getText() == null ? "" : this.txtNameAdmin.getText().trim();
        String email = this.txtEmailAdmin.getText() == null ? "" : this.txtEmailAdmin.getText().trim();
        String phoneNumber  = this.txtNumberAdmin.getText() == null ? "" : this.txtNumberAdmin.getText().trim();
        String password = this.txtPasswordAdmin.getText() == null ? "" : this.txtPasswordAdmin.getText();

        if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !phoneNumber.isEmpty()){
            if(isEmailRegistered(email)){
                Utils.showAlert("ERROR", "Email is already registered!");
            } else if (txtPasswordAdmin.getLength() < 8) {
                Utils.showAlert("ERROR", "Password must be at least 8 characters!");
            } else{
                String adminId = manageAdmin.generateId();
                String hashedPassword = Utils.hashPassword(password);

                Admin newAdmin = new Admin(adminId, name,  email, phoneNumber, hashedPassword);
                manageAdmin.createAdmin(newAdmin);
                Utils.showAlert("VERIFIED", "Client successfully registered.");

                try {
                    ManageAdminController controller = Utils.replaceMainContent(mainContent, "manageAdmin.fxml");
                    controller.setMainContent(mainContent);
                    controller.setLoggedAdmin(adminLogged);
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
