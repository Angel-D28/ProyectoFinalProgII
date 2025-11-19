package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.DeliveryDriver;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.Shipment;
import co.edu.uniquindio.poo.neodelivery.model.Status;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class ManageShipmentsController {

    @FXML
    private TableView<Shipment> assignedShipments;

    @FXML
    private Button btnChangeStatus;

    @FXML
    private Button btnDeselect;

    @FXML
    private TableColumn<Shipment, String> columnDestination;

    @FXML
    private TableColumn<Shipment, String> columnFragile;

    @FXML
    private TableColumn<Shipment, String> columnHasInsurence;

    @FXML
    private TableColumn<Shipment, String> columnID;

    @FXML
    private TableColumn<Shipment, String> columnIsPriority;

    @FXML
    private TableColumn<Shipment, String> columnOrigin;

    @FXML
    private TableColumn<Shipment, String> columnRequiresSignature;

    @FXML
    private TableColumn<Shipment, Status> columnStatus;

    @FXML
    private TableColumn<Shipment, Double> columnVolume;

    @FXML
    private TableColumn<Shipment, Double> columnWeight;

    private AnchorPane mainContent;

    private DeliveryDriver currentUser;

    private ObservableList<Shipment> shipmentsList = FXCollections.observableArrayList();

    public void setMainContent(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    public void setCurrentUser(DeliveryDriver deliveryDriver) {
        this.currentUser = deliveryDriver;
        loadShipmentsFromDatabase();
    }

    private void loadShipmentsFromDatabase() {
        if (currentUser == null) return;

        DataBase db = DataBase.getInstance();
        shipmentsList.clear();

        for (Shipment shipment : db.getListaEnvios()) {
            if (shipment.getAssignedDriver() != null && shipment.getAssignedDriver().getId().equals(currentUser.getId())) {
                shipmentsList.add(shipment);
                System.out.println("Shipment: " + shipment.getId() + ", driver: " +
                        (shipment.getAssignedDriver() != null ? shipment.getAssignedDriver().getName() : "null"));
            }
        }
    }


    @FXML
    public void initialize() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnOrigin.setCellValueFactory(new PropertyValueFactory<>("origin"));
        columnDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        columnWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        columnVolume.setCellValueFactory(new PropertyValueFactory<>("volume"));
        columnHasInsurence.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isHasInsurance() ? "Yes" : "No")
        );
        columnIsPriority.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isPriority() ? "Yes" : "No")
        );
        columnRequiresSignature.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isRequiresSignature() ? "Yes" : "No")
        );
        columnFragile.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isFragile() ? "Yes" : "No")
        );
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        assignedShipments.setItems(shipmentsList);
    }

    @FXML
    void changeShippingStatus(ActionEvent event) {
        Shipment selectedShipment = assignedShipments.getSelectionModel().getSelectedItem();

        if (selectedShipment == null) {
            Utils.showAlert("WARNING", "No shipment was selected, please select one.");
            return;
        }

        ChoiceDialog<Status> dialog = new ChoiceDialog<>(selectedShipment.getStatus(), Status.values());
        dialog.setTitle("Cambiar estado del envío");
        dialog.setHeaderText("Envío ID: " + selectedShipment.getId());
        dialog.setContentText("Selecciona el nuevo estado:");

        dialog.showAndWait().ifPresent(newStatus -> {
            selectedShipment.setStatus(newStatus);
            assignedShipments.refresh();
        });
        DataBase.getInstance().saveToJson();
    }

    @FXML
    void deselectShipment(ActionEvent event) {
        assignedShipments.getSelectionModel().clearSelection();
    }



}
