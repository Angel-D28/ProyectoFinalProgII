package co.edu.uniquindio.poo.neodelivery.model.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.Locale;

import co.edu.uniquindio.poo.neodelivery.model.CashPayment;
import co.edu.uniquindio.poo.neodelivery.model.Payment;
import co.edu.uniquindio.poo.neodelivery.model.Shipment;
import co.edu.uniquindio.poo.neodelivery.model.User;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
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
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
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
            case "DAVIPLATA" -> "/co/edu/uniquindio/poo/neodelivery/icons/daviplata.png";
            case "NEQUI" -> "/co/edu/uniquindio/poo/neodelivery/icons/nequi.png";
            default -> "/co/edu/uniquindio/poo/neodelivery/icons/info.png";
        };

        Alert alert = new Alert(Alert.AlertType.NONE);
        DialogPane pane = createCustomPane(type, message, iconPath, css);
        alert.setDialogPane(pane);

        Stage stage = (Stage) pane.getScene().getWindow();
        stage.initStyle(StageStyle.UNDECORATED);

        alert.showAndWait();
    }

    private static DialogPane createCustomPane(String type, String message, String iconPath, String css) {
        DialogPane pane = new DialogPane();
        pane.getStylesheets().add(css);
        pane.getStyleClass().add("custom-alert-pane");

        String typeClass = switch(type.toUpperCase()) {
            case "ERROR" -> "alert-error";
            case "WARNING" -> "alert-warning";
            case "VERIFIED" -> "alert-verified";
            case "DAVIPLATA" -> "alert-daviplata";
            case "NEQUI" -> "alert-nequi";
            default -> "alert-info";
        };
        pane.getStyleClass().add(typeClass);

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        ImageView icon = new ImageView(new Image(Utils.class.getResourceAsStream(iconPath)));
        icon.setFitHeight(32);
        icon.setFitWidth(32);

        Label lblTitle = new Label(type);
        lblTitle.getStyleClass().add("alert-title");

        header.getChildren().addAll(icon, lblTitle);

        Label lblMessage = new Label(message);
        lblMessage.getStyleClass().add("alert-message");

        Button btn = new Button("Accept");
        btn.getStyleClass().add("alert-button");
        btn.setOnAction(e -> pane.getScene().getWindow().hide());

        HBox buttonBox = new HBox(btn);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        root.getChildren().addAll(header, lblMessage, buttonBox);
        pane.setContent(root);

        FadeTransition ft = new FadeTransition(Duration.millis(200), root);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        return pane;
    }

    public static String formatCOP(double amount) {
        NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        formato.setMaximumFractionDigits(0); // sin decimales
        return formato.format(amount);
    }

    public static void createPaymentPDF(Payment payment, Shipment shipment, User client) {
        Document document = new Document();
        try {
            String userHome = System.getProperty("user.home");
            String downloadPath = userHome + "/Downloads";
            String fileName = "recibo_" + payment.getIdPayment() + ".pdf";
            String fullPath = downloadPath + File.separator + fileName;

            PdfWriter.getInstance(document, new FileOutputStream(fullPath));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            Paragraph title = new Paragraph("Recibo de Pago - NeoDelivery", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            Paragraph clientInfo = new Paragraph("Cliente: " + client.getName() +
                    "\nEmail: " + client.getEmail() +
                    "\nTeléfono: " + client.getNumbre(), normalFont);
            clientInfo.setSpacingAfter(15);
            document.add(clientInfo);

            Paragraph shipmentInfo = new Paragraph(
                    "Envío ID: " + shipment.getId() +
                            "\nOrigen: " + shipment.getOrigin() +
                            "\nDestino: " + shipment.getDestination() +
                            "\nPeso: " + shipment.getWeight() + " kg" +
                            "\nVolumen: " + shipment.getVolume() + " m³" +
                            "\nPrioridad: " + (shipment.isPriority() ? "Sí" : "No") +
                            "\nFrágil: " + (shipment.isFragile() ? "Sí" : "No") +
                            "\nCon seguro: " + (shipment.isHasInsurance() ? "Sí" : "No") +
                            "\nRequiere firma: " + (shipment.isRequiresSignature() ? "Sí" : "No") +
                            "\nCosto final: $" + shipment.getCost()
                    , normalFont);
            shipmentInfo.setSpacingAfter(15);
            document.add(shipmentInfo);

            String paymentDetails = "Pago ID: " + payment.getIdPayment() +
                    "\nMonto: $" + payment.getAmount() +
                    "\nFecha: " + payment.getPaymentDate() +
                    "\nEstado: " + payment.getStatus();
            if(payment instanceof CashPayment cashPayment){
                paymentDetails += "\nReferencia Efecty: " + cashPayment.getReferenceCode();
            }
            Paragraph paymentInfo = new Paragraph(paymentDetails, normalFont);
            paymentInfo.setSpacingAfter(20);
            document.add(paymentInfo);

            Paragraph thanks = new Paragraph("¡Thank you for trusting NeoDelivery!", titleFont);
            thanks.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(thanks);

            Utils.showAlert("VERIFIED", "Invoice PDF generated successfully!\nYou can find it here: " + fullPath);

        } catch (Exception e) {
            e.printStackTrace();
            Utils.showAlert("ERROR", "Failed to create PDF: " + e.getMessage());
        } finally {
            document.close();
        }

    }


}

