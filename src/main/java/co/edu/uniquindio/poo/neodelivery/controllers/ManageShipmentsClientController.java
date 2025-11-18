package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.Shipment;
import co.edu.uniquindio.poo.neodelivery.model.Status;
import co.edu.uniquindio.poo.neodelivery.model.User;
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

public class ManageShipmentsClientController {

    @FXML
    private Button btnCancelShipment;

    @FXML
    private Button btnCreateShipment;

    @FXML
    private Button btnDeselect;

    @FXML
    private Button btnEditShipment;

    @FXML
    private TableColumn<Shipment, String> columnDeliveryDriver;

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
    private TableColumn<Shipment, String> columnStatus;

    @FXML
    private TableColumn<Shipment, Double> columnVolume;

    @FXML
    private TableColumn<Shipment, Double> columnWeight;

    @FXML
    private TableView<Shipment> tableShipmentsClient;

    private DataBase db = DataBase.getInstance();

    private AnchorPane mainContent;

    private User clientLogged;

    private Shipment shipment;

    private ObservableList<Shipment> shipmentsList = FXCollections.observableArrayList();

    void setClientLogged(User client){
        this.clientLogged = client;
        loadShipmentsListFromUserList();
    }

    void loadShipmentsListFromUserList(){
        shipmentsList.clear();
        shipmentsList.addAll(clientLogged.getShipmentsList());
    }

    void setMainContentManageShipments(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    void setShipment(Shipment shipment){
        this.shipment = shipment;
    }


    @FXML
    void initialize() {
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
        columnDeliveryDriver.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAssignedDriver() != null ? cellData.getValue().getAssignedDriver().getName() : "Not assigned"));


        tableShipmentsClient.setItems(shipmentsList);
        refreshShipmentsList();
    }

    @FXML
    void cancelShipment(ActionEvent event) {
        Shipment shipmentSelected = tableShipmentsClient.getSelectionModel().getSelectedItem();
        if(shipmentSelected == null){
            Utils.showAlert("WARNING", "Select a shipment.");
        }else if(shipmentSelected.getStatus().equals(Status.PENDING) || shipmentSelected.getStatus().equals(Status.DELIVERASSIGNED)){
            clientLogged.getShipmentsList().remove(shipmentSelected);
            db.getListaEnvios().remove(shipmentSelected);
            Utils.showAlert("VERIFIED", "Shipping canceled.");
            refreshShipmentsList();
        }else{
            Utils.showAlert("ERROR", "You cannot cancel a shipment that has already been picked up by a delivery driver.");
        }
    }

    @FXML
    void createShipment(ActionEvent event) {
        AddShipmentsClientController shipmentsClientController = Utils.replaceMainContent(mainContent, "addShipmentClient.fxml");
        shipmentsClientController.setClient(clientLogged);
        shipmentsClientController.setMainContent(mainContent);
    }

    @FXML
    void deselectShipment(ActionEvent event) {
        tableShipmentsClient.getSelectionModel().clearSelection();
    }

    @FXML
    void edtiShipment(ActionEvent event) {

    }

    public void refreshShipmentsList() {
        if (clientLogged != null) {
            shipmentsList.clear();
            shipmentsList.addAll(clientLogged.getShipmentsList());
            tableShipmentsClient.setItems(shipmentsList);
        }
    }

}
