package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.ActivityLogService;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
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
        setNameLabel(userLogged.getName());
        loadUserImage();

        HomeClientController homeController = Utils.replaceMainContent(mainContent, "homeClient.fxml");
        if(homeController != null){
            homeController.setClient(userLogged);
            homeController.setMainContentHomeClient(mainContent);
        }
    }

    void setNameLabel(String name){
        lblWelcomeClient.setText(name);
    }

    @FXML
    public void initialize() {
        profileImageView.setClip(new Circle(50, 50, 50));
    }

    @FXML
    void btnActivateNotifitcations(ActionEvent event) {
        boolean newState = !userLogged.isNotificationsEnabled();
        userLogged.setNotificationsEnabled(newState);

        if (newState) {
            Utils.showAlert("VERIFIED", "Notifications activated");
        } else {
            Utils.showAlert("VERIFIED", "Notifications deactivated");
        }
        DataBase.getInstance().saveToJson();
    }

    @FXML
    void btnAddress(ActionEvent event) {
        ManageAddressesController addressController = Utils.replaceMainContent(mainContent, "manageAddresses.fxml");
        if(addressController != null){
            addressController.setClient(userLogged);
            addressController.setMainContent(mainContent);
        }
    }

    @FXML
    void homeUser(ActionEvent event) {
        HomeClientController homeClientController = Utils.replaceMainContent(mainContent, "homeClient.fxml");
        homeClientController.setMainContentHomeClient(mainContent);
        homeClientController.setClient(userLogged);
        loadUserImage();
    }

    @FXML
    void showOrders(ActionEvent event) {
        ManageShipmentsClientController shipmentController = Utils.replaceMainContent(mainContent, "manageShipmentsClient.fxml");
        shipmentController.setClientLogged(userLogged);
        shipmentController.setMainContentManageShipments(mainContent);
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
        TrackOrderController trackOrderController = Utils.replaceMainContent(mainContent, "trackOrder.fxml");
        trackOrderController.setUserLogged(userLogged);
        trackOrderController.setMainContent(mainContent);
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
            generateReport();
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showAlert("ERROR", "No se pudo cargar la vista de reportes");
        }
    }

    @FXML
    void generateReport() {
        try {
            ManageReportsController controller = Utils.replaceMainContent(mainContent, "manageReports.fxml");
            if(controller != null){
                controller.setMainContent(mainContent);
                controller.setClientLogged(userLogged);
                controller.generateDeliveryReportPDF();

            }
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