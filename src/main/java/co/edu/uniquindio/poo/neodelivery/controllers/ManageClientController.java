package co.edu.uniquindio.poo.neodelivery.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ManageClientController {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnDeselect;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> columnAddress;

    @FXML
    private TableColumn<?, ?> columnEmail;

    @FXML
    private TableColumn<?, ?> columnID;

    @FXML
    private TableColumn<?, ?> columnName;

    @FXML
    private TableColumn<?, ?> columnPhoneNumber;

    @FXML
    private TableColumn<?, ?> columnRol;

    @FXML
    private TableView<?> manageUsers;

    @FXML
    void deleteUser(ActionEvent event) {

    }

    @FXML
    void deselect(ActionEvent event) {

    }

    @FXML
    void updateUser(ActionEvent event) {

    }

}
