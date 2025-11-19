package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.ActivityLogService;
import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageAdmin;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UpdateAdminController {

    @FXML
    private Button btnCancelUpdate;

    @FXML
    private Button btnUpdateAdmin;

    @FXML
    private TextField txtAdminEmail;

    @FXML
    private TextField txtAdminName;

    @FXML
    private TextField txtAdminNumber;

    @FXML
    private PasswordField txtAdminPassword;

    @FXML
    private PasswordField txtAdminPasswordConfirm;

    private Admin adminToUpdate;

    private Admin adminLogged;

    private AnchorPane mainContent;

    ManageAdmin manageAdmin = new ManageAdmin();

    void setAdminLogged(Admin adminLogged) {
        this.adminLogged = adminLogged;
    }

    void setAdmin(Admin admin){
        this.adminToUpdate = admin;

        if(admin!=null){
            txtAdminEmail.setText(admin.getEmail());
            txtAdminName.setText(admin.getName());
            txtAdminNumber.setText(admin.getNumber());
        }
    }

    public void setAdminUpdateContent(AnchorPane mainContent){
        this.mainContent = mainContent;
    }

    @FXML
    void cancelUpdate(ActionEvent event) {
        ManageAdminController controller = Utils.replaceMainContent(mainContent, "manageAdmin.fxml");
        controller.setLoggedAdmin(adminLogged);
        controller.setMainContent(mainContent);
    }

    @FXML
    void updateAdmin(ActionEvent event) {
        if (adminToUpdate == null) {
            Utils.showAlert("ERROR", "No admin has been loaded for update.");
            return;
        }

        String name = txtAdminName.getText() == null ? "" : txtAdminName.getText().trim();
        String phoneNumber = txtAdminNumber.getText() == null ? "" : txtAdminNumber.getText().trim();
        String email = txtAdminEmail.getText() == null ? "" : txtAdminEmail.getText().trim();
        String password = txtAdminPassword.getText() == null ? "" : txtAdminPassword.getText();
        String confirmPassword = txtAdminPasswordConfirm.getText() == null ? "" : txtAdminPasswordConfirm.getText();

        if (name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()
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

        if (!email.equalsIgnoreCase(adminToUpdate.getEmail()) && Utils.isEmailRegistered(email)) {
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

        Admin updatedAdmin = new Admin(
                adminToUpdate.getIdAdmin(),
                name,
                email,
                phoneNumber,
                Utils.hashPassword(password)
        );

        manageAdmin.updateAdmin(adminToUpdate.getIdAdmin().toString(), updatedAdmin);
        Utils.showAlert("VERIFIED", "Successfully updated");

        ActivityLogService.log(adminLogged.getName(),
                "Updated Admin - Name: " + name + " ID: " + adminToUpdate.getIdAdmin()
        );

        try {
            ManageAdminController controller =
                    Utils.replaceMainContent(mainContent, "manageAdmin.fxml");
            controller.setLoggedAdmin(adminLogged);
            controller.setMainContent(mainContent);
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showAlert("ERROR", "Could not return to Manage Admins");
        }
    }



}
