package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.User;
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

public class ClientDashboardController {
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

    private boolean visibleMenu = true;
    private final double menuWidth = 200;
    private User userLogged;

    public void setClient(User userLogged) {
        this.userLogged = userLogged;
        lblWelcomeClient.setText("Welcome " + userLogged.getName());
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

    /**
     * Navega a la vista de gestión de reportes
     */
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

    /**
     * Genera un reporte PDF directamente desde el dashboard
     */
    @FXML
    void generateReport(ActionEvent event) {
        try {
            ManageReportsController controller = Utils.replaceMainContent(mainContent, "manageReports.fxml");
            controller.setMainContent(mainContent);
            // Generar el reporte automáticamente
            controller.generateDeliveryReportPDF();
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showAlert("ERROR", "No se pudo generar el reporte");
        }
    }
}