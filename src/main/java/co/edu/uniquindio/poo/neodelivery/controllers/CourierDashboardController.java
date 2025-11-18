package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.ActivityLogService;
import co.edu.uniquindio.poo.neodelivery.model.DeliveryDriver;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
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

public class CourierDashboardController {

    @FXML
    private Button btnLogOut;

    @FXML
    private Label lblWelcomeCourier;

    @FXML
    private BorderPane root;

    @FXML
    private AnchorPane mainContent;

    @FXML
    private Button menuButton;

    @FXML
    private AnchorPane menu;

    @FXML
    private ImageView profileImageView;

    @FXML
    private Button homeButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button btnStatistics;

    private boolean visibleMenu = true;
    private final double menuWidth = 200;

    private DeliveryDriver currentCourier;

    public void setCurrentCourier(DeliveryDriver courier) {
        this.currentCourier = courier;
        lblWelcomeCourier.setText("Welcome, " + courier.getName());
        loadCourierImage();

        if (mainContent != null) {
            DeliveryDriverStatisticsController controller = Utils.replaceMainContent(mainContent, "deliveryDriverStatistics.fxml");
            if (controller != null) {
                controller.setCurrentCourier(currentCourier);
            }
        }
    }

    @FXML
    public void initialize() {
        if (profileImageView != null) {
            profileImageView.setClip(new Circle(50, 50, 50));
        }
        if (currentCourier != null) {
            loadCourierImage();
        }
    }

    @FXML
    void logOut(ActionEvent event) {
        Utils.replaceScene(event, "loginView.fxml", "Login - Neo Delivery");
        ActivityLogService.log(currentCourier.getName()+" - ID: "+currentCourier.getId(), "Signed out");
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
    void openShipments(ActionEvent event) {
        DeliveryShipmentsController controller = Utils.replaceMainContent(mainContent, "DeliveryShipmentsView.fxml");
        if (controller != null) {
            controller.setCurrentCourier(currentCourier);
        }
    }

    @FXML
    void goToStatistics(ActionEvent event) {
        try {
            DeliveryDriverStatisticsController controller = Utils.replaceMainContent(mainContent, "deliveryDriverStatistics.fxml");
            if (controller != null) {
                controller.setCurrentCourier(currentCourier);
            }
            loadCourierImage();
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showAlert("ERROR", "Could not load statistics view");
        }
    }

    @FXML
    void showProfile(ActionEvent event) {
        try {
            ProfileDeliveryDriverController controller = Utils.replaceMainContent(mainContent, "deliveryDriverProfile.fxml");
            if (controller != null) {
                controller.setDeliveryDriver(currentCourier);
                controller.setMainContent(mainContent);
                controller.setDashboardController(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showAlert("ERROR", "Could not load profile view");
        }
    }

    private void loadCourierImage() {
        if (profileImageView == null || currentCourier == null) return;
        
        Image img;
        if (currentCourier.getProfilePicturePath() != null) {
            File f = new File(currentCourier.getProfilePicturePath());
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
        loadCourierImage();
    }
}