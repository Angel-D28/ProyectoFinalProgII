package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.Shipment;
import co.edu.uniquindio.poo.neodelivery.model.Status;
import co.edu.uniquindio.poo.neodelivery.model.User;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageShipments;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TrackOrderController {

    @FXML
    private Button btnTrackOrder;

    @FXML
    private Label lblInformation;

    @FXML
    private HBox timelineBox;

    @FXML
    private TextField txtOrderID;

    private User clientLogged;

    private AnchorPane mainContent;

    ManageShipments manageShipments = new ManageShipments();

    void setUserLogged(User clientLogged) {
        this.clientLogged = clientLogged;
    }

    void setMainContent(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    @FXML
    void trackOrder(ActionEvent event) {

        String idBuscar = txtOrderID.getText().trim().toUpperCase();

        if(idBuscar.isEmpty()){
            Utils.showAlert("Error", "Please type an Order ID");
            return;
        }

        Shipment found = null;

        for (Shipment s : clientLogged.getShipmentsList()) {
            if (s.getId().equals(idBuscar)) {
                found = s;
                break;
            }
        }

        if (found == null) {
            timelineBox.getChildren().clear();
            lblInformation.setText("");
            Utils.showAlert("ERROR", "Wrong ID or non-existent order");
            return;
        }
        labelStatus(found.getStatus());
        updateTimeline(found);
    }

    private void updateTimeline(Shipment shipment) {

        timelineBox.getChildren().clear();

        String status = shipment.getStatus().toString().toUpperCase();

        boolean isPending = status.equals("PENDING") || status.equals("DELIVER ASSIGNED") ||
                status.equals("DELIVERING") || status.equals("DELIVERED");

        boolean isAssigned = status.equals("DELIVER ASSIGNED") ||
                status.equals("DELIVERING") || status.equals("DELIVERED");

        boolean isDelivering = status.equals("DELIVERING") ||
                status.equals("DELIVERED");

        boolean isDelivered = status.equals("DELIVERED");

        timelineBox.getChildren().add(buildStep("Pending", "/images/iconsTimeLine/pending.png", isPending));
        timelineBox.getChildren().add(buildArrow(isAssigned));

        timelineBox.getChildren().add(buildStep("Assigned", "/images/iconsTimeLine/assigned.png", isAssigned));
        timelineBox.getChildren().add(buildArrow(isDelivering));

        timelineBox.getChildren().add(buildStep("Delivering", "/images/iconsTimeLine/delivering.png", isDelivering));
        timelineBox.getChildren().add(buildArrow(isDelivered));

        timelineBox.getChildren().add(buildStep("Delivered", "/images/iconsTimeLine/delivered.png", isDelivered));
    }


    private VBox buildStep(String text, String iconPath, boolean active) {
        ImageView img = new ImageView(getClass().getResource(iconPath).toString());
        img.setFitHeight(100);
        img.setFitWidth(100);
        img.setOpacity(active ? 1.0 : 0.3);

        Label lbl = new Label(text);
        lbl.setStyle(active ? "-fx-font-weight: bold;" : "-fx-opacity: 0.5;");

        VBox box = new VBox(img, lbl);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);

        return box;
    }

    private ImageView buildArrow(boolean active) {
        ImageView arrow = new ImageView(getClass().getResource("/images/iconsTimeLine/arrow.png").toString());
        arrow.setFitHeight(40);
        arrow.setFitWidth(40);
        arrow.setOpacity(active ? 1.0 : 0.3);
        return arrow;
    }

    private void labelStatus(Status status) {
        switch (status) {
            case DELIVERASSIGNED -> lblInformation.setText("A delivery partner is preparing to pick up your package.");
            case DELIVERING -> lblInformation.setText("Your order is on its way!");
            case DELIVERED -> lblInformation.setText("Your package has arrived — thank you for choosing us!");
            case PENDING ->  lblInformation.setText("Your order has been received and will be processed soon.");
            default -> lblInformation.setText("");
        }
    }


}
