package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.Address;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.User;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class ManageAddressesController {

    @FXML private TableView<Address> tableAddresses;
    @FXML private TableColumn<Address, String> colAddress;
    @FXML private TextField txtNewAddress;
    @FXML private Button btnAdd;
    @FXML private Button btnDelete;
    @FXML private Button btnSetDefault;

    private User clientLogged;
    private ObservableList<Address> addressesObs;
    private AnchorPane mainContent;

    public void setMainContent(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    public void setClient(User client) {
        this.clientLogged = client;
        addressesObs = FXCollections.observableArrayList(clientLogged.getAddressList());
        tableAddresses.setItems(addressesObs);

        colAddress.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().toString())
        );
    }

    @FXML
    void addAddress() {
        String text = txtNewAddress.getText().trim();
        if (text.isEmpty()) {
            Utils.showAlert("WARNING", "Write an address");
            return;
        }

        Address newAddress = new Address(text);

        clientLogged.getAddressList().add(newAddress);
        addressesObs.add(newAddress);
        DataBase.getInstance().saveToJson();

        txtNewAddress.clear();
        Utils.showAlert("VERIFIED", "Address added");
    }

    @FXML
    void deleteAddress() {
        Address selected = tableAddresses.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Utils.showAlert("WARNING", "Select an address to delete");
            return;
        }

        clientLogged.getAddressList().remove(selected);
        addressesObs.remove(selected);
        DataBase.getInstance().saveToJson();

        Utils.showAlert("VERIFIED", "Address deleted");
    }

    @FXML
    void setDefault() {
        Address selected = tableAddresses.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Utils.showAlert("WARNING", "Select an address");
            return;
        }


        clientLogged.setAddress(selected);
        DataBase.getInstance().saveToJson();
        Utils.showAlert("VERIFIED", "Default address updated");
    }

    @FXML
    void cancelAddress() {
        HomeClientController controller = Utils.replaceMainContent(mainContent, "homeClient.fxml");
        controller.setClient(clientLogged);
        controller.setMainContentHomeClient(mainContent);
    }

}
