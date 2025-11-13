//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package co.edu.uniquindio.poo.neodelivery.model.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
        Alert.AlertType var10000;
        switch (type.toUpperCase()) {
            case "ERROR" -> var10000 = AlertType.ERROR;
            case "WARNING" -> var10000 = AlertType.WARNING;
            case "INFO" -> var10000 = AlertType.INFORMATION;
            default -> var10000 = AlertType.NONE;
        }

        Alert.AlertType alertType = var10000;
        (new Alert(alertType, message, new ButtonType[0])).showAndWait();
    }
}

