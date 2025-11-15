package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class AdminDashboardController {

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
}
