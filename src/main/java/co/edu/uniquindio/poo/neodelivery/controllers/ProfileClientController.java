package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.User;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ProfileClientController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnChangePhoto;

    @FXML
    private Button btnSave;

    @FXML
    private ImageView imageProfile;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private PasswordField txtOldPassword;

    @FXML
    private TextField txtPhone;

    private User clientLogged;

    private File imageFile;

    private AnchorPane mainContent;

    private ClientDashboardController dashboardController;

    public void setMainContent(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    public void setDashboardController(ClientDashboardController controller) {
        this.dashboardController = controller;
    }

    public void setClient(User user) {
        this.clientLogged = user;

        txtName.setText(user.getName());
        txtEmail.setText(user.getEmail());
        txtPhone.setText(user.getNumbre());

        if (user.getProfilePicturePath() != null) {
            Image img = new Image(new File(user.getProfilePicturePath()).toURI().toString(), 100, 100, false, true);
            imageProfile.setImage(img);
        }else {
            Image defaultImg = new Image(getClass().getResource("/images/defaultAvatar.png").toString(), 100, 100, false, true);
            imageProfile.setImage(defaultImg);
        }

        Circle clip = new Circle(50, 50, 50);
        imageProfile.setClip(clip);
    }

    @FXML
    void cancel(ActionEvent event) {
        backToHome();
    }

    @FXML
    void changePhoto(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

        File file = chooser.showOpenDialog(null);
        if (file != null) {
            File destDir = new File("data/images");
            if (!destDir.exists()) destDir.mkdirs();

            File destFile = new File(destDir, file.getName());
            try {
                Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                imageFile = destFile;

                Image img = new Image(destFile.toURI().toString(), 100, 100, false, true);
                imageProfile.setImage(img);

                clientLogged.setProfilePicturePath(destFile.getPath());

                DataBase.getInstance().saveToJson();

            } catch (IOException e) {
                e.printStackTrace();
                Utils.showAlert("ERROR", "Error saving image");
            }
        }
    }

    @FXML
    void saveChanges(ActionEvent event) {
        if (clientLogged == null) return;

        String name = txtName.getText() == null ? "" : txtName.getText().trim();
        String email = txtEmail.getText() == null ? "" : txtEmail.getText().trim();
        String phone = txtPhone.getText() == null ? "" : txtPhone.getText().trim();
        String oldPass = txtOldPassword.getText() == null ? "" : txtOldPassword.getText();
        String newPass = txtNewPassword.getText() == null ? "" : txtNewPassword.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Utils.showAlert("WARNING", "Name, email, and phone cannot be empty");
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(gmail|hotmail|outlook|yahoo)\\.com$")) {
            Utils.showAlert("ERROR", "Email must be Gmail, Hotmail, Outlook, or Yahoo");
            return;
        }

        if (!email.equalsIgnoreCase(clientLogged.getEmail())
                && Utils.isEmailRegistered(email)) {
            Utils.showAlert("ERROR", "Email already in use");
            return;
        }

        if (!phone.matches("^3\\d{9}$")) {
            Utils.showAlert("ERROR", "Phone must be 10 digits and start with 3");
            return;
        }

        clientLogged.setName(name);
        clientLogged.setEmail(email);
        clientLogged.setNumbre(phone);

        if (!oldPass.isEmpty() || !newPass.isEmpty()) {

            if (oldPass.isEmpty() || newPass.isEmpty()) {
                Utils.showAlert("ERROR", "To change your password, fill both fields");
                return;
            }

            if (!Utils.hashPassword(oldPass).equals(clientLogged.getPassword())) {
                Utils.showAlert("ERROR", "The old password doesn't match");
                return;
            }

            if (newPass.length() < 8) {
                Utils.showAlert("ERROR", "The new password must be at least 8 characters long");
                return;
            }

            if (newPass.equals(oldPass)) {
                Utils.showAlert("ERROR", "The new password must be different from the old one");
                return;
            }

            clientLogged.setPassword(Utils.hashPassword(newPass));
        }

        if (imageFile != null) {
            clientLogged.setProfilePicturePath(imageFile.getAbsolutePath());
        }

        DataBase.getInstance().saveToJson();
        Utils.showAlert("VERIFIED", "Profile updated successfully!");

        if (mainContent != null) backToHome();
        if (dashboardController != null) {
            dashboardController.refreshProfileImage();
            dashboardController.setNameLabel(name);
        }
    }

    void backToHome(){
        HomeClientController homeController = Utils.replaceMainContent(mainContent, "homeClient.fxml");
        homeController.setMainContentHomeClient(mainContent);
    }

}
