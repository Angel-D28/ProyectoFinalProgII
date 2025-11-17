package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.ActivityLogService;
import co.edu.uniquindio.poo.neodelivery.model.User;
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

public class ClientDashboardController {

    @FXML
    private Button btnAddress;
    @FXML
    private Button btnHomeUser;
    @FXML
    private Button btnNotifications;
    @FXML
    private Button btnOrders;
    @FXML
    private Button btnProfileUser;
    @FXML
    private Button btnTrackOrder;
    @FXML
    private Button btnLogOut;
    @FXML
    private Label lblWelcomeClient;
    @FXML
    private AnchorPane mainContent;
    @FXML
    private Button menuButton;
    @FXML
    private AnchorPane menu;
    @FXML
    private BorderPane root;
    @FXML
    private ImageView profileImageView;

    private boolean visibleMenu = true;
    private boolean notificationActived = true;
    private final double menuWidth = 200;
    private User userLogged;

    public void setClient(User userLogged) {
        this.userLogged = userLogged;
        lblWelcomeClient.setText(userLogged.getName());
        loadUserImage();
    }

    @FXML
    public void initialize() {
        Utils.replaceMainContent(mainContent, "homeClient.fxml");
        profileImageView.setClip(new Circle(50, 50, 50));

        if (userLogged != null) {
            loadUserImage();
        }
    }

    @FXML
    void btnActivateNotifitcations(ActionEvent event) {

        if (notificationActived) {
            Utils.showAlert("VERIFIED", "Notifications actived");

        }else{
            Utils.showAlert("VERIFIED", "Notifications desactived");
        }
        notificationActived = !notificationActived;
    }

    @FXML
    void btnAddress(ActionEvent event) {

    }

    @FXML
    void homeUser(ActionEvent event) {
        HomeClientController homeClientController = Utils.replaceMainContent(mainContent, "homeClient.fxml");
        homeClientController.setMainContentHomeClient(mainContent);
        loadUserImage();
    }

    @FXML
    void showOrders(ActionEvent event) {

    }

    @FXML
    void showProfileUser(ActionEvent event) {
        try {
            ProfileClientController controller = Utils.replaceMainContent(mainContent, "profileClient.fxml");
            controller.setClient(userLogged);
            controller.setMainContent(mainContent);
            controller.setDashboardController(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void trackOrder(ActionEvent event) {

    }

    @FXML
    void logOut(ActionEvent event) {
        Utils.replaceScene(event, "loginView.fxml", "Login - Neo Delivery");
        ActivityLogService.log(userLogged.getName()+" - ID: "+userLogged.getIdUser(), "Signed out");
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
    void goToManageReports(ActionEvent event) {
        try {
            ManageReportsController controller = Utils.replaceMainContent(mainContent, "manageReports.fxml");
            controller.setMainContent(mainContent);
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showAlert("ERROR", "No se pudo cargar la vista de reportes");
        }
    }

    @FXML
    void generateReport(ActionEvent event) {
        try {
            ManageReportsController controller = Utils.replaceMainContent(mainContent, "manageReports.fxml");
            controller.setMainContent(mainContent);
            controller.generateDeliveryReportPDF();
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showAlert("ERROR", "No se pudo generar el reporte");
        }
    }

    private void loadUserImage() {
        Image img;
        if (userLogged != null && userLogged.getProfilePicturePath() != null) {
            File f = new File(userLogged.getProfilePicturePath());
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
        loadUserImage();
    }
}