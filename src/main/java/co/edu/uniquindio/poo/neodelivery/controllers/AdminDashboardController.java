package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.File;

public class AdminDashboardController {

    @FXML
    private Button btnManageDrivers;
    @FXML
    private Button btnProfile;
    @FXML
    private ImageView profileImageView;
    @FXML
    private Button btnManageAdmin;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnManageUsers;
    @FXML
    private Button btnLogOut;
    @FXML
    private Label lblWelcomeAdmin;
    @FXML
    private AnchorPane mainContent;
    @FXML
    private BorderPane root;
    @FXML
    private Button menuButton;
    @FXML
    private AnchorPane menu;

    private boolean visibleMenu = true;
    private final double menuWidth = 200;
    private Admin admin;

    void setAdmin (Admin admin) {
        this.admin = admin;
        lblWelcomeAdmin.setText("Welcome, " + admin.getName());
        loadAdminImage();
    }

    @FXML
    public void initialize() {
        profileImageView.setClip(new Circle(50, 50, 50));
    }

    @FXML
    void logOut(ActionEvent event) {
        Utils.replaceScene(event, "loginView.fxml", "Login - Neo Delivery");
    }

    @FXML
    void slideMenu(ActionEvent event) {
        double duration = 250;

        if (visibleMenu) {

            TranslateTransition hideMenu = new TranslateTransition(Duration.millis(duration), menu);
            hideMenu.setToX(-menuWidth);
            hideMenu.setInterpolator(Interpolator.EASE_BOTH);

            hideMenu.setOnFinished(e -> {
                root.setLeft(null);
                menu.setTranslateX(0);
            });

            hideMenu.play();

        } else {

            root.setLeft(menu);
            menu.setTranslateX(-menuWidth);

            TranslateTransition showMenu = new TranslateTransition(Duration.millis(duration), menu);
            showMenu.setToX(0);
            showMenu.setInterpolator(Interpolator.EASE_BOTH);
            showMenu.play();
        }

        visibleMenu = !visibleMenu;
    }

    @FXML
    void goToManageUsers(ActionEvent event) {
        try {
            ManageClientController controller = Utils.replaceMainContent(mainContent, "manageClient(Admin).fxml");
            controller.setMainContent(mainContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToManageAdmin(ActionEvent event) {
        try {
            ManageAdminController controller = Utils.replaceMainContent(mainContent, "manageAdmin.fxml");
            controller.setLoggedAdmin(admin);
            controller.setMainContent(mainContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToManageDrivers(ActionEvent event) {
        try {
            ManageDeliveryDriverController controller = Utils.replaceMainContent(mainContent, "manageDeliveryDrivers(Admin).fxml");
            controller.setMainContent(mainContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToEditProfile(ActionEvent event) {
        try {
            ProfileAdminController controller = Utils.replaceMainContent(mainContent, "profileAdmin.fxml");
            controller.setAdmin(admin);
            controller.setMainContent(mainContent);
            controller.setDashboardController(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToHome(ActionEvent event) {
        if (mainContent != null) {
            mainContent.getChildren().clear();
        }
        loadAdminImage();
    }

    private void loadAdminImage() {
        if (admin != null && admin.getProfilePicturePath() != null) {
            File f = new File(admin.getProfilePicturePath());
            if (f.exists()) {
                Image img = new Image(f.toURI().toString());
                profileImageView.setImage(img);
            }
        }
    }

    public void refreshProfileImage() {
        if (admin != null && admin.getProfilePicturePath() != null) {
            File f = new File(admin.getProfilePicturePath());
            if (f.exists()) {
                profileImageView.setImage(new Image(f.toURI().toString()));
            }
        }
    }
}
