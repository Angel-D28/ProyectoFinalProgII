package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;

public class ProfileAdminController {

    @FXML
    private Button btnChangePhoto;

    @FXML
    private Button btnSave;

    @FXML
    private ImageView imageProfile;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    private Admin adminLogged;

    private File imageFile;

    private AnchorPane mainContent;

    private AdminDashboardController dashboardController;

    public void setMainContent(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    public void setDashboardController(AdminDashboardController controller) {
        this.dashboardController = controller;
    }

    public void setAdmin(Admin admin) {
        this.adminLogged = admin;

        txtName.setText(admin.getName());
        txtEmail.setText(admin.getEmail());
        txtPhone.setText(admin.getNumber());

        if (admin.getProfilePicturePath() != null) {
            Image img = new Image(new File(admin.getProfilePicturePath()).toURI().toString(), 100, 100, false, true);
            imageProfile.setImage(img);
        }

        Circle clip = new Circle(50, 50, 50);
        imageProfile.setClip(clip);
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
        if (adminLogged == null) return;

        adminLogged.setName(txtName.getText());
        adminLogged.setEmail(txtEmail.getText());
        adminLogged.setNumber(txtPhone.getText());

        if (imageFile != null) {
            adminLogged.setProfilePicturePath(imageFile.getAbsolutePath());
        }

        Utils.showAlert("VERIFIED", "Profile updated successfully!");

        if (mainContent != null) {
            mainContent.getChildren().clear();
        }

        if (dashboardController != null) {
            dashboardController.refreshProfileImage();
        }
    }

}
