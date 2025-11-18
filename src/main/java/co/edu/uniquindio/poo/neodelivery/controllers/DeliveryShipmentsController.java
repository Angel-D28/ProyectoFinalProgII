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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class DeliveryShipmentsController {

    @FXML
    private TableView<Shipment> assignedShipmentsTable;

    @FXML
    private TableView<Shipment> deliveredShipmentsTable;

    @FXML
    private TableColumn<Shipment, String> assignedShipmentIdColumn;

    @FXML
    private TableColumn<Shipment, String> assignedCustomerNameColumn;

    @FXML
    private TableColumn<Shipment, String> assignedAddressColumn;

    @FXML
    private TableColumn<Shipment, String> assignedShipmentDateColumn;

    @FXML
    private TableColumn<Shipment, Status> assignedStatusColumn;

    @FXML
    private TableColumn<Shipment, String> deliveredShipmentIdColumn;

    @FXML
    private TableColumn<Shipment, String> deliveredCustomerNameColumn;

    @FXML
    private TableColumn<Shipment, String> deliveredAddressColumn;

    @FXML
    private TableColumn<Shipment, String> deliveredShipmentDateColumn;

    @FXML
    private TableColumn<Shipment, Status> deliveredStatusColumn;

    @FXML
    private Button markAsDeliveredButton;

    @FXML
    private Button markAsPickedUpButton;

    private DeliveryDriver currentCourier;


    private final ObservableList<Shipment> assignedShipments = FXCollections.observableArrayList();
    private final ObservableList<Shipment> deliveredShipments = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        configureColumns();
        assignedShipmentsTable.setItems(assignedShipments);
        deliveredShipmentsTable.setItems(deliveredShipments);
    }

    private void configureColumns() {
        assignedShipmentIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getId()));
        assignedCustomerNameColumn.setCellValueFactory(cell -> new SimpleStringProperty(resolveCustomerName(cell.getValue())));
        assignedAddressColumn.setCellValueFactory(cell -> new SimpleStringProperty(resolveAddress(cell.getValue())));
        assignedShipmentDateColumn.setCellValueFactory(cell -> new SimpleStringProperty(resolveShipmentDate(cell.getValue())));
        assignedStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        deliveredShipmentIdColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getId()));
        deliveredCustomerNameColumn.setCellValueFactory(cell -> new SimpleStringProperty(resolveCustomerName(cell.getValue())));
        deliveredAddressColumn.setCellValueFactory(cell -> new SimpleStringProperty(resolveAddress(cell.getValue())));
        deliveredShipmentDateColumn.setCellValueFactory(cell -> new SimpleStringProperty(resolveShipmentDate(cell.getValue())));
        deliveredStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void setCurrentCourier(DeliveryDriver courier) {
        this.currentCourier = courier;
        loadShipments();
    }

    private void loadShipments() {
        assignedShipments.clear();
        deliveredShipments.clear();

        if (currentCourier == null) {
            return;
        }

        DataBase db = DataBase.getInstance();
        List<Shipment> shipments = db.getListaEnvios();

        for (Shipment shipment : shipments) {
            if (shipment.getAssignedDriver() == null) {
                continue;
            }

            if (!shipment.getAssignedDriver().getId().equals(currentCourier.getId())) {
                continue;
            }

            if (shipment.getStatus() == Status.DELIVERED) {
                deliveredShipments.add(shipment);
            } else {
                assignedShipments.add(shipment);
            }
        }
    }

    @FXML
    void handleMarkAsDelivered(ActionEvent event) {
        Shipment selectedShipment = assignedShipmentsTable.getSelectionModel().getSelectedItem();

        if (selectedShipment == null) {
            Utils.showAlert("WARNING", "Please select a shipment first.");
            return;
        }

        if (currentCourier == null) {
            Utils.showAlert("ERROR", "No delivery driver is currently active.");
            return;
        }

        if (selectedShipment.getStatus() == Status.DELIVERED) {
            Utils.showAlert("WARNING", "The shipment is already delivered.");
            return;
        }

        if (selectedShipment.getAssignedDriver() == null ||
                !selectedShipment.getAssignedDriver().getId().equals(currentCourier.getId())) {
            Utils.showAlert("ERROR", "This shipment is not assigned to the current delivery driver.");
            return;
        }

        if (selectedShipment.getStatus() != Status.DELIVERING) {
            showShipmentInfoAlert(selectedShipment);
            return;
        }

        selectedShipment.setStatus(Status.DELIVERED);
        currentCourier.setAvalibility(true);
        assignedShipments.remove(selectedShipment);
        if (!deliveredShipments.contains(selectedShipment)) {
            deliveredShipments.add(selectedShipment);
        }
        deliveredShipmentsTable.refresh();
        assignedShipmentsTable.refresh();
        Utils.showAlert("VERIFIED", "Shipment marked as delivered.");
    }

    @FXML
    void handleMarkAsPickedUp(ActionEvent event) {
        Shipment selectedShipment = assignedShipmentsTable.getSelectionModel().getSelectedItem();

        if (selectedShipment == null) {
            Utils.showAlert("WARNING", "Please select a shipment first.");
            return;
        }

        if (currentCourier == null) {
            Utils.showAlert("ERROR", "No delivery driver is currently active.");
            return;
        }

        if (selectedShipment.getAssignedDriver() == null ||
                !selectedShipment.getAssignedDriver().getId().equals(currentCourier.getId())) {
            Utils.showAlert("ERROR", "This shipment is not assigned to the current delivery driver.");
            return;
        }

        if (selectedShipment.getStatus() == Status.DELIVERED) {
            Utils.showAlert("WARNING", "The shipment is already delivered.");
            return;
        }

        if (selectedShipment.getStatus() != Status.DELIVERASSIGNED) {
            Utils.showAlert("WARNING", "Only shipments in Deliver Assigned status can be picked up.");
            return;
        }

        selectedShipment.setStatus(Status.DELIVERING);
        assignedShipmentsTable.refresh();
        Utils.showAlert("VERIFIED", "Shipment marked as picked up.");
    }

    private String resolveCustomerName(Shipment shipment) {
        if (shipment.getOrigin() != null) {
            return shipment.getOrigin().toString();
        }
        return "Not available";
    }

    private String resolveAddress(Shipment shipment) {
        if (shipment.getDestination() != null) {
            return shipment.getDestination().toString();
        }
        return "Not available";
    }

    private String resolveShipmentDate(Shipment shipment) {
        return "-";
    }

    private void showShipmentInfoAlert(Shipment shipment) {
        String message = "The shipment must be picked up before it can be delivered.\n\n" +
                "Shipment ID: " + shipment.getId() + "\n" +
                "Customer: " + resolveCustomerName(shipment) + "\n" +
                "Address: " + resolveAddress(shipment) + "\n" +
                "Current status: " + shipment.getStatus();
        Utils.showAlert("INFO", message);
    }
}

