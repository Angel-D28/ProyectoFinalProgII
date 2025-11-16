package co.edu.uniquindio.poo.neodelivery.model.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Utils {
    public static <T> T replaceScene(ActionEvent event, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(Utils.class.getResource("/co/edu/uniquindio/poo/neodelivery/" + fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T replaceMainContent(AnchorPane mainContent, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(Utils.class.getResource("/co/edu/uniquindio/poo/neodelivery/" + fxmlPath));
            Node view = loader.load();

            mainContent.getChildren().setAll(view);
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for(byte b : hash) {
                String hex = Integer.toHexString(255 & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void showAlert(String type, String message) {

        String css = Utils.class.getResource("/co/edu/uniquindio/poo/neodelivery/styles.css").toExternalForm();

        String iconPath = switch (type.toUpperCase()) {
            case "ERROR" -> "/co/edu/uniquindio/poo/neodelivery/icons/error.png";
            case "WARNING" -> "/co/edu/uniquindio/poo/neodelivery/icons/warning.png";
            case "VERIFIED" -> "/co/edu/uniquindio/poo/neodelivery/icons/verified.png";
            default -> "/co/edu/uniquindio/poo/neodelivery/icons/info.png";
        };

        Alert alert = new Alert(Alert.AlertType.NONE);

        DialogPane pane = createCustomPane(type, message, iconPath, css);
        alert.setDialogPane(pane);

        Stage stage = (Stage) pane.getScene().getWindow();
        stage.initStyle(StageStyle.UNDECORATED);

        alert.showAndWait();
    }

    private static DialogPane createCustomPane(String title, String message, String iconPath, String css) {

        DialogPane pane = new DialogPane();
        pane.getStylesheets().add(css);
        pane.getStyleClass().add("custom-alert-pane");

        //root
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        //Title and icon
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        ImageView icon = new ImageView(new Image(Utils.class.getResourceAsStream(iconPath)));
        icon.setFitHeight(32);
        icon.setFitWidth(32);

        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("alert-title");

        header.getChildren().addAll(icon, lblTitle);

        //message
        Label lblMessage = new Label(message);
        lblMessage.getStyleClass().add("alert-message");

        //Button
        Button btn = new Button("Accept");
        btn.getStyleClass().add("alert-button");
        btn.setOnAction(e -> pane.getScene().getWindow().hide());

        HBox buttonBox = new HBox(btn);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        root.getChildren().addAll(header, lblMessage, buttonBox);
        pane.setContent(root);

        //Animation
        FadeTransition ft = new FadeTransition(Duration.millis(200), root);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        return pane;
    }

}

