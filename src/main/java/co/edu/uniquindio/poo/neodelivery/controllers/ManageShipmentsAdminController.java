package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.Admin;
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

public class ManageShipmentsAdminController {

    @FXML
    private Button btnAssignDeliveryDriver;

    @FXML
    private Button btnDeselect;

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
    private TableColumn<Shipment, Status> columnStatus;

    @FXML
    private TableColumn<Shipment, Double> columnVolume;

    @FXML
    private TableColumn<Shipment, Double> columnWeight;

    @FXML
    private TableView<Shipment> tableShipments;

    private AnchorPane mainContent;

    private Admin adminLogged;

    private Shipment shipment;

    private ObservableList<Shipment> shipmentsList = FXCollections.observableArrayList();

    void loadShipmentsListFromDatabase(){
        DataBase db = DataBase.getInstance();
        shipmentsList.clear();
        shipmentsList.addAll(db.getListaEnvios());
    }

    void setMainContentManageShipments(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    void setAdminLogged(Admin adminLogged){
        this.adminLogged = adminLogged;
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

        loadShipmentsListFromDatabase();
        tableShipments.setItems(shipmentsList);
    }

    @FXML
    void assignDeliveryDriver(ActionEvent event) {
        Shipment selectedShipment = tableShipments.getSelectionModel().getSelectedItem();
        if(selectedShipment == null){
            Utils.showAlert("WARNING", "Select a shipment");
            return;
        }

        if(selectedShipment.getStatus().equals(Status.PENDING)){
            AssignDeliveryDriverController assignController = Utils.replaceMainContent(mainContent, "assignDeliveryDriver.fxml");
            assignController.setAdmin(adminLogged);
            assignController.setShipment(selectedShipment);
            assignController.setMainContent(mainContent);
        }else{
            Utils.showAlert("WARNING", "Select a shipment with a pending status.");
        }

    }

    @FXML
    void deselectShipment(ActionEvent event) {
        tableShipments.getSelectionModel().clearSelection();
    }

}