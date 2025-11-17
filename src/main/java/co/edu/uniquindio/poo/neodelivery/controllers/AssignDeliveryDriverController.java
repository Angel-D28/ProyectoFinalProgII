package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.ActivityLogService;
import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.DeliveryDriver;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.Shipment;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageShipments;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class AssignDeliveryDriverController {

    @FXML
    private Button btnAssignDeliveryDriver;

    @FXML
    private Button btnCancel;

    @FXML
    private TableColumn<DeliveryDriver, String> columnEmail;

    @FXML
    private TableColumn<DeliveryDriver, String> columnID;

    @FXML
    private TableColumn<DeliveryDriver, String> columnName;

    @FXML
    private TableView<DeliveryDriver> tableDriversList;

    private AnchorPane root;

    private Admin adminLogged;

    private Shipment shipmentSelected;

    ManageShipments manageShipments = new ManageShipments();

    ObservableList<DeliveryDriver> listDrivers = FXCollections.observableArrayList();


    public void setMainContent(AnchorPane mainContent) {
        this.root = mainContent;
    }

    public void setShipment(Shipment shipmentSelected) {
        this.shipmentSelected = shipmentSelected;
    }

    public void setAdmin(Admin admin) {
        this.adminLogged = admin;
    }

    void loadDeliveryDriversFromDatabase(){
        DataBase db = DataBase.getInstance();

        for (DeliveryDriver driver : db.getListaRepartidores()){
            if(driver.isAvalibility()){
                listDrivers.add(driver);
            }
        }
    }

    @FXML
    void initialize() {
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableDriversList.setItems(listDrivers);
        loadDeliveryDriversFromDatabase();
    }

    @FXML
    void assignDeliveryDriver(ActionEvent event) {
        DeliveryDriver deliverySelected = tableDriversList.getSelectionModel().getSelectedItem();

        if (deliverySelected == null) {
            Utils.showAlert("WARNING", "Please select a valid driver.");
            return;
        }
        manageShipments.assignDriver(adminLogged, shipmentSelected, deliverySelected);
        deliverySelected.setShipmentAssigned(shipmentSelected);
        Utils.showAlert("VERIFIED", "Successfully assigned.");
        ActivityLogService.log(adminLogged.getName() +
                " - ID: "+adminLogged.getIdAdmin(), "He assigned an order to "+
                deliverySelected.getName()+" - ID: "+deliverySelected.getId());

        try {
            ManageShipmentsAdminController controller = Utils.replaceMainContent(root, "manageShipments(Admin).fxml");
            controller.setAdminLogged(adminLogged);
            controller.setShipment(shipmentSelected);
            controller.setMainContentManageShipments(root);
        }catch (Exception e){
            e.printStackTrace();
            Utils.showAlert("ERROR", "Something went wrong.");
        }

    }

    @FXML
    void cancel(ActionEvent event) {
        ManageShipmentsAdminController shipmentController = Utils.replaceMainContent(root, "manageShipments(Admin).fxml");
        shipmentController.setMainContentManageShipments(root);
    }

}
