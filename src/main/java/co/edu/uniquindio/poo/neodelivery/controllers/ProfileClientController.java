package co.edu.uniquindio.poo.neodelivery.controllers;

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
            imageFile = file;
            Image img = new Image(file.toURI().toString(), 100, 100, false, true);
            imageProfile.setImage(img);
        }
    }

    @FXML
    void saveChanges(ActionEvent event) {
        if (clientLogged == null) return;

        clientLogged.setName(txtName.getText());
        clientLogged.setEmail(txtEmail.getText());
        clientLogged.setNumbre(txtPhone.getText());

        String oldPass = txtOldPassword.getText();
        String newPass = txtNewPassword.getText();

        boolean valid = true;
        String message = "Profile updated successfully!";

        if(!oldPass.isEmpty() || !newPass.isEmpty()) {
            if(!Utils.hashPassword(oldPass).equals(clientLogged.getPassword())) {
                valid = false;
                message = "The old password doesn't match.";
            } else if(newPass.length() < 8) {
                valid = false;
                message = "The new password must be at least 8 characters long.";
            } else if(newPass.equals(oldPass)) {
                valid = false;
                message = "The new password must be different from the old one.";
            } else {
                clientLogged.setPassword(Utils.hashPassword(newPass));
            }
        }

        if (imageFile != null) {
            clientLogged.setProfilePicturePath(imageFile.getAbsolutePath());
        }

        if(valid) {
            Utils.showAlert("VERIFIED", message);
            if(mainContent != null) backToHome();
            if(dashboardController != null) dashboardController.refreshProfileImage();
        } else {
            Utils.showAlert("ERROR", message);
        }
    }

    void backToHome(){
        HomeClientController homeController = Utils.replaceMainContent(mainContent, "homeClient.fxml");
        homeController.setMainContentHomeClient(mainContent);
    }

}
