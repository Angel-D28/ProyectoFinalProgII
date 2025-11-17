package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.ActivityLogService;
import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.DeliveryDriver;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageDeliveryDrivers;
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

public class ManageDeliveryDriverController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnDeselect;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<DeliveryDriver, String> colAssignedShipment;

    @FXML
    private TableColumn<DeliveryDriver, String> columnEmail;

    @FXML
    private TableColumn<DeliveryDriver, String> columnID;

    @FXML
    private TableColumn<DeliveryDriver, String> columnName;

    @FXML
    private TableView<DeliveryDriver> tableDriversList;

    private Admin adminLogged;

    private ObservableList<DeliveryDriver> driversList = FXCollections.observableArrayList();

    private AnchorPane mainContent;

    private ManageDeliveryDrivers manageDriver = new ManageDeliveryDrivers();

    void setAdminLogged(Admin adminLogged) {
        this.adminLogged = adminLogged;
    }

    public void setMainContent(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    @FXML
    private void initialize() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAssignedShipment.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getShipmentAssigned() != null ? "Assigned" : "None")
        );

        tableDriversList.setItems(driversList);
        loadUsersFromDatabase();
    }

    private void loadUsersFromDatabase() {
        DataBase db = DataBase.getInstance();
        driversList.clear();
        driversList.addAll(db.getListaRepartidores());
    }

    @FXML
    void createDriver(ActionEvent event) {
        AddDeliveryDriverController addDriverController = Utils.replaceMainContent(mainContent, "addDeliveryDriver(Admin).fxml");
        addDriverController.setContentAddDriver(mainContent);
        addDriverController.setAdminL(adminLogged);
    }

    @FXML
    void deleteDriver(ActionEvent event) {
        DeliveryDriver selectedDriver = tableDriversList.getSelectionModel().getSelectedItem();
        if (selectedDriver != null) {
            driversList.remove(selectedDriver);
            manageDriver.deleteDeliveyDriver(selectedDriver);
            Utils.showAlert("VERIFIED", "Successfully deleted");
            ActivityLogService.log(adminLogged.getName(), "Deleted a delivery driver");
        }else{
            Utils.showAlert("WARNING", "Select a driver first");
        }
    }

    @FXML
    void deselect(ActionEvent event) {
        tableDriversList.getSelectionModel().clearSelection();
    }

    @FXML
    void updateDriver(ActionEvent event) {

        DeliveryDriver selectedDriver = tableDriversList.getSelectionModel().getSelectedItem();
        if (selectedDriver == null) {
            Utils.showAlert("WARNING", "Select a driver to update");
            return;
        }
        try {
            UpdateDeliveryDriverController deliveryDriverController = Utils.replaceMainContent(mainContent, "updateDriver(Admin).fxml");
            deliveryDriverController.setDriverToUpdate(selectedDriver);
            deliveryDriverController.setAdminLogged(adminLogged);
            deliveryDriverController.setContentUpdateDriver(mainContent);

        }catch (Exception e){
            e.printStackTrace();
            Utils.showAlert("ERROR", "Could not open update delivery driver window");
        }
    }

}
