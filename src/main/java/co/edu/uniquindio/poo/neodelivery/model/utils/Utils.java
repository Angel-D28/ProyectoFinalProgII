//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package co.edu.uniquindio.poo.neodelivery.model.utils;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Utils {
    public static void replaceScene(ActionEvent event, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(Utils.class.getResource("/co/edu/uniquindio/poo/neodelivery/" + fxmlPath));
            Parent root = (Parent)loader.load();
            Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            currentStage.setTitle(title);
            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void replaceMainContent(AnchorPane mainContent, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(Utils.class.getResource("/co/edu/uniquindio/poo/neodelivery/" + fxmlPath));
            Node view = (Node)loader.load();
            if (view instanceof Region region) {
                region.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                region.setPrefSize(mainContent.getWidth(), mainContent.getHeight());
                region.prefWidthProperty().bind(mainContent.widthProperty());
                region.prefHeightProperty().bind(mainContent.heightProperty());
            }

            mainContent.getChildren().setAll(new Node[]{view});
            AnchorPane.setTopAnchor(view, (double)0.0F);
            AnchorPane.setBottomAnchor(view, (double)0.0F);
            AnchorPane.setLeftAnchor(view, (double)0.0F);
            AnchorPane.setRightAnchor(view, (double)0.0F);
        } catch (IOException e) {
            e.printStackTrace();
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

