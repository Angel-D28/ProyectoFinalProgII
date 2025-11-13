package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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
    private boolean visibleMenu = true;

    @FXML
    void logOut(ActionEvent event) {
        Utils.replaceScene(event, "loginView.fxml", "Login - Neo Delivery");
    }

    @FXML
    void slideMenu(ActionEvent event) {
        if (this.visibleMenu) {
            this.menu.setVisible(false);
            this.menu.setManaged(false);
        } else {
            this.menu.setVisible(true);
            this.menu.setManaged(true);
        }

        this.visibleMenu = !this.visibleMenu;
    }
}