package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.Status;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class AdminHomeController {

    @FXML private Label lblUsuarios;
    @FXML private Label lblRepartidores;
    @FXML private Label lblPedidos;

    @FXML private BarChart<String, Number> barChart;
    @FXML private PieChart pieChart;

    private AnchorPane mainContent;

    void setHomeMainContent(AnchorPane mainContent){
        this.mainContent = mainContent;
    }

    public void initialize() {

        DataBase db = DataBase.getInstance();

        int clients = db.getListaUsuarios().size();
        int deliveryDrivers = db.getListaRepartidores().size();
        int totalShipments = db.getListaEnvios().size();

        long pending  = db.getListaEnvios().stream().filter(p -> p.getStatus().equals(Status.PENDING)).count();
        long assigned   = db.getListaEnvios().stream().filter(p -> p.getStatus().equals(Status.DELIVERASSIGNED)).count();
        long inDelivery      = db.getListaEnvios().stream().filter(p -> p.getStatus().equals(Status.DELIVERING)).count();
        long delivered  = db.getListaEnvios().stream().filter(p -> p.getStatus().equals(Status.DELIVERED)).count();

        lblUsuarios.setText("Clients: " + clients);
        lblRepartidores.setText("Delivery drivers: " + deliveryDrivers);
        lblPedidos.setText("Shipments: " + totalShipments);


        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.getData().add(new XYChart.Data<>("Clients", clients));
        serie.getData().add(new XYChart.Data<>("Delivery Drivers", deliveryDrivers));
        serie.getData().add(new XYChart.Data<>("Total users", clients+deliveryDrivers));

        barChart.getData().add(serie);

        pieChart.getData().add(new PieChart.Data("Pending", pending));
        pieChart.getData().add(new PieChart.Data("Assigned", assigned));
        pieChart.getData().add(new PieChart.Data("In delivery", inDelivery));
        pieChart.getData().add(new PieChart.Data("Delivered", delivered));

    }
}