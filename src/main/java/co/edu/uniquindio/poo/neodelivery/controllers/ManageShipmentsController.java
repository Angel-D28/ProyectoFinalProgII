package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.*;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
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
    private TableColumn<Shipment, Address> columnDestination;

    @FXML
    private TableColumn<Shipment, Boolean> columnFragile;

    @FXML
    private TableColumn<Shipment, Boolean> columnHasInsurence;

    @FXML
    private TableColumn<Shipment, Integer> columnID;

    @FXML
    private TableColumn<Shipment, Boolean> columnIsPriority;

    @FXML
    private TableColumn<Shipment, Address> columnOrigin;

    @FXML
    private TableColumn<Shipment, Boolean> columnRequiresSignature;

    @FXML
    private TableColumn<Shipment, Status> columnStatus;

    @FXML
    private TableColumn<Shipment, Double> columnVolume;

    @FXML
    private TableColumn<Shipment, Double> columnWeight;

    private ObservableList<Shipment> shipmentsList = FXCollections.observableArrayList();
    private AnchorPane mainContent;

    private DeliveryDriver currentUser;

    public void setMainContent(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    public void setCurrentUser(DeliveryDriver deliveryDriver) {
        this.currentUser = deliveryDriver;
        loadShipmentsFromDatabase();
    }

    @FXML
    public void initialize() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        columnDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        columnFragile.setCellValueFactory(new PropertyValueFactory<>("fragile"));
        columnHasInsurence.setCellValueFactory(new PropertyValueFactory<>("hasInsurance"));
        columnIsPriority.setCellValueFactory(new PropertyValueFactory<>("isPriority"));
        columnOrigin.setCellValueFactory(new PropertyValueFactory<>("origin"));
        columnRequiresSignature.setCellValueFactory(new PropertyValueFactory<>("requiresSignature"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        columnVolume.setCellValueFactory(new PropertyValueFactory<>("volume"));
        columnWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));

        assignedShipments.setItems(shipmentsList);
    }

    private void loadShipmentsFromDatabase() {
        if (currentUser == null) return;

        DataBase db = DataBase.getInstance();
        shipmentsList.clear();

        for (Shipment shipment : db.getListaEnvios()) {
            if (shipment.getAssignedDriver() != null &&
                    shipment.getAssignedDriver().getId().equals(currentUser.getId())) {

                shipmentsList.add(shipment);
            }
        }
    }
    @FXML
    void changeShippingStatus(ActionEvent event) {
        // Obtener el envío seleccionado
        Shipment selectedShipment = assignedShipments.getSelectionModel().getSelectedItem();

        if (selectedShipment == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Primero debe seleccionar un envío", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        // Crear un diálogo para seleccionar el nuevo estado
        ChoiceDialog<Status> dialog = new ChoiceDialog<>(selectedShipment.getStatus(), Status.values());
        dialog.setTitle("Cambiar estado del envío");
        dialog.setHeaderText("Envío ID: " + selectedShipment.getId());
        dialog.setContentText("Selecciona el nuevo estado:");

        dialog.showAndWait().ifPresent(newStatus -> {
            selectedShipment.setStatus(newStatus);
            assignedShipments.refresh();
        });
    }
}