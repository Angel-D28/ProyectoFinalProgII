package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.Shipment;
import co.edu.uniquindio.poo.neodelivery.model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class HomeClientController {

    @FXML
    private TableColumn<Shipment, String> columnStatus;

    @FXML
    private TableColumn<Shipment, String> columnDestination;

    @FXML
    private TableColumn<Shipment, String> columnID;

    @FXML
    private TableColumn<Shipment, String> columnOrigin;

    @FXML
    private TableColumn<Shipment, String> columnPaymentMethod;

    @FXML
    private TableColumn<Shipment, Double> columnTotal;

    @FXML
    private TableView<Shipment> tableOrderLog;

    private AnchorPane mainContent;

    private ObservableList<Shipment> listShipmentsClient = FXCollections.observableArrayList();

    void setMainContentHomeClient(AnchorPane mainContent){
        this.mainContent = mainContent;
    }

    @FXML
    void initialize() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnOrigin.setCellValueFactory(new PropertyValueFactory<>("origin"));
        columnPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        columnTotal.setCellValueFactory(new PropertyValueFactory<>("cost"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        columnDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));

        tableOrderLog.setItems(listShipmentsClient);
        loadShipmentFromDatabase();
    }

    void loadShipmentFromDatabase(){
        DataBase db = DataBase.getInstance();
        for (Shipment shipment : db.getListaEnvios()){
            if(shipment.getStatus().equals(Status.DELIVERED)){
                listShipmentsClient.add(shipment);
            }
        }
    }
}
