package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.ActivityLogService;
import co.edu.uniquindio.poo.neodelivery.model.Address;
import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.User;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class ManageClientController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnDeselect;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<User, Address> columnAddress;

    @FXML
    private TableColumn<User, String> columnEmail;

    @FXML
    private TableColumn<User, Integer> columnID;

    @FXML
    private TableColumn<User, String> columnName;

    @FXML
    private TableColumn<User, String> columnPhoneNumber;

    @FXML
    private TableView<User> manageUsers;

    private Admin adminLogged;

    private ObservableList<User> userList = FXCollections.observableArrayList();

    private AnchorPane mainContent;

    public void setMainContent(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    public void setAdminLogged(Admin adminLogged) {
        this.adminLogged = adminLogged;
    }

    @FXML
    public void initialize() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("numbre"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        manageUsers.setItems(userList);
        loadUsersFromDatabase();
    }

    private void loadUsersFromDatabase() {
        DataBase db = DataBase.getInstance();
        userList.clear();
        userList.addAll(db.getListaUsuarios());
    }

    @FXML
    void deleteUser(ActionEvent event) {
        User selected = manageUsers.getSelectionModel().getSelectedItem();
        if (selected != null) {
            userList.remove(selected);
            DataBase.getInstance().getListaUsuarios().remove(selected);
            Utils.showAlert("VERIFIED", "Successfully removed");
            ActivityLogService.log(adminLogged.getName(), "Deleted a user");
        }else{
            Utils.showAlert("WARNING", "Select a user to delete");
        }
    }

    @FXML
    void deselect(ActionEvent event) {
        manageUsers.getSelectionModel().clearSelection();
    }

    @FXML
    void updateUser(ActionEvent event) {
        User selected = manageUsers.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Utils.showAlert("WARNING", "Select a user to update");
            return;
        }

        try {
            UpdateClientController controller = Utils.replaceMainContent(mainContent, "updateClient(Admin).fxml");
            controller.setClient(selected);
            controller.setAdminLogged(adminLogged);
            controller.setContentUpdateClient(mainContent);

        } catch (Exception e) {
            e.printStackTrace();
            Utils.showAlert("ERROR", "Could not open update client window");
        }
    }

    @FXML
    void addUser(ActionEvent event) {
        AddClientController addClientController = Utils.replaceMainContent(mainContent, "addClient(Admin).fxml");
        addClientController.setAdminLogged(adminLogged);
        addClientController.setContentAddClient(mainContent);
    }

}
