package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.ActivityLogService;
import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.File;

public class AdminDashboardController {

    @FXML
    private Button btnActivityLog;
    @FXML
    private Button btnShipments;
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
        Utils.replaceMainContent(mainContent, "adminHome.fxml");
        profileImageView.setClip(new Circle(50, 50, 50));

    }

    @FXML
    void logOut(ActionEvent event) {
        Utils.replaceScene(event, "loginView.fxml", "Login - Neo Delivery");
        ActivityLogService.log(admin.getName() + " - ID: "+admin.getIdAdmin(), "Signed out");

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
            controller.setAdminLogged(admin);
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
            controller.setAdminLogged(admin);
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
            AdminHomeController controller = Utils.replaceMainContent(mainContent, "adminHome.fxml");
            controller.setHomeMainContent(mainContent);
        }
        loadAdminImage();
    }

    @FXML
    void goToShipments(ActionEvent event) {
        ManageShipmentsAdminController shipmentController = Utils.replaceMainContent(mainContent, "manageShipments(Admin).fxml");
        shipmentController.setAdminLogged(admin);
        shipmentController.setMainContentManageShipments(mainContent);
    }

    @FXML
    void viewActivityLog(ActionEvent event) {
        ActivityLogController logController = Utils.replaceMainContent(mainContent, "activityLog.fxml");
        logController.setMainContent(mainContent);
    }

    private void loadAdminImage() {
        Image img;
        if (admin != null && admin.getProfilePicturePath() != null) {
            File f = new File(admin.getProfilePicturePath());
            if (f.exists()) {
                img = new Image(f.toURI().toString());
            } else {
                img = new Image(getClass().getResource("/images/defaultAvatar.png").toString());
            }
        } else {
            img = new Image(getClass().getResource("/images/defaultAvatar.png").toString());
        }
        profileImageView.setImage(img);
    }

    public void refreshProfileImage() {
        loadAdminImage();
    }
}
