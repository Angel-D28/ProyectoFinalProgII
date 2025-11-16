package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageAdmin;
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

public class ManageAdminController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnDeselect;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Admin, String> columnEmail;

    @FXML
    private TableColumn<Admin, String> columnID;

    @FXML
    private TableColumn<Admin, String> columnName;

    @FXML
    private TableColumn<Admin, String> columnPhoneNumber;

    @FXML
    private TableView<Admin> tableAdmins;

    ManageAdmin manageAdmin = new ManageAdmin();

    private ObservableList<Admin> listAdmins = FXCollections.observableArrayList();

    private AnchorPane mainContent;

    private Admin adminLogged;

    void setLoggedAdmin(Admin loggedAdmin) {
        this.adminLogged = loggedAdmin;
    }

    public void setMainContent(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    @FXML
    public void initialize() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("idAdmin"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("number"));

        tableAdmins.setItems(listAdmins);
        loadUsersFromDatabase();
    }

    private void loadUsersFromDatabase() {
        DataBase db = DataBase.getInstance();
        listAdmins.clear();
        listAdmins.addAll(db.getListaAdmin());
    }
    @FXML
    void addAdmin(ActionEvent event) {
        AddAdminController addAdminController = Utils.replaceMainContent(mainContent, "addAdmin.fxml");
        addAdminController.adminLogged(adminLogged);
        addAdminController.setContentAddAdmin(mainContent);
    }

    @FXML
    void deleteAdmin(ActionEvent event) {
        Admin selected = tableAdmins.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Utils.showAlert("WARNING", "Select an admin to delete");
            return;
        }

        if (selected.getIdAdmin().equals("1")) {
            Utils.showAlert("ERROR", "You can't eliminate the Super Admin");
            return;
        }

        if (adminLogged != null && selected.getIdAdmin().equals(adminLogged.getIdAdmin())) {
            Utils.showAlert("ERROR", "You can't eliminate yourself");
            return;
        }

        listAdmins.remove(selected);
        manageAdmin.deleteAdmin(selected);
        Utils.showAlert("VERIFIED", "Successfully removed");
    }

    @FXML
    void deselect(ActionEvent event) {
        tableAdmins.getSelectionModel().clearSelection();
    }

    @FXML
    void updateAdmin(ActionEvent event) {
        Admin selected = tableAdmins.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Utils.showAlert("WARNING", "Select a admin to update");
            return;
        } else if (selected.getIdAdmin().equals(adminLogged.getIdAdmin())) {
            Utils.showAlert("ERROR", "You can't update yourself");
            return;
        }

        try {
            UpdateAdminController controller = Utils.replaceMainContent(mainContent, "updateAdmin.fxml");
            controller.setAdmin(selected);
            controller.setAdminLogged(adminLogged);
            controller.setAdminUpdateContent(mainContent);

        } catch (Exception e) {
            e.printStackTrace();
            Utils.showAlert("ERROR", "Could not open update admin window");
        }
    }

}