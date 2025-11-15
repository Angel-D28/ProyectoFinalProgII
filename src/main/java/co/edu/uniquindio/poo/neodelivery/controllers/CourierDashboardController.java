package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.DeliveryDriver;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

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

    private boolean visibleMenu = true;
    private final double menuWidth = 200;

    private DeliveryDriver currentCourier;

    public void setCurrentCourier(DeliveryDriver courier) {
        this.currentCourier = courier;
        lblWelcomeCourier.setText("Bienvenido, " + courier.getName());
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
    void openShipments(ActionEvent event) {
        try {
            ManageShipmentsController controller = Utils.replaceMainContent(mainContent, "manageShipments(DeliveryDriver).fxml");
            controller.setMainContent(mainContent);
            controller.setCurrentUser(currentCourier);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}