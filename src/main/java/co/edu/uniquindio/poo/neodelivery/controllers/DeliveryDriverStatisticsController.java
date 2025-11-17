package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.DeliveryDriver;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.Status;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

public class DeliveryDriverStatisticsController {

    @FXML private Label lblAssignedShipments;
    @FXML private Label lblDeliveredShipments;
    @FXML private Label lblPendingShipments;
    @FXML private Label lblPickedUpShipments;

    @FXML private BarChart<String, Number> barChart;
    @FXML private PieChart pieChart;

    private DeliveryDriver currentCourier;

    public void setCurrentCourier(DeliveryDriver courier) {
        this.currentCourier = courier;
        loadStatistics();
    }

    @FXML
    public void initialize() {
        if (currentCourier != null) {
            loadStatistics();
        }
    }

    private void loadStatistics() {
        if (currentCourier == null) return;

        DataBase db = DataBase.getInstance();

        long assigned = db.getListaEnvios().stream()
                .filter(s -> s.getAssignedDriver() != null 
                        && s.getAssignedDriver().getId().equals(currentCourier.getId())
                        && s.getStatus() == Status.DELIVERASSIGNED)
                .count();

        long delivering = db.getListaEnvios().stream()
                .filter(s -> s.getAssignedDriver() != null 
                        && s.getAssignedDriver().getId().equals(currentCourier.getId())
                        && s.getStatus() == Status.DELIVERING)
                .count();

        long delivered = db.getListaEnvios().stream()
                .filter(s -> s.getAssignedDriver() != null 
                        && s.getAssignedDriver().getId().equals(currentCourier.getId())
                        && s.getStatus() == Status.DELIVERED)
                .count();

        long pending = db.getListaEnvios().stream()
                .filter(s -> s.getAssignedDriver() != null 
                        && s.getAssignedDriver().getId().equals(currentCourier.getId())
                        && s.getStatus() == Status.PENDING)
                .count();

        long totalAssigned = assigned + delivering + delivered + pending;

        lblAssignedShipments.setText("Assigned: " + totalAssigned);
        lblDeliveredShipments.setText("Delivered: " + delivered);
        lblPendingShipments.setText("Pending: " + pending);
        lblPickedUpShipments.setText("In Delivery: " + delivering);

        // Bar Chart
        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Assigned", totalAssigned));
        series.getData().add(new XYChart.Data<>("Delivered", delivered));
        series.getData().add(new XYChart.Data<>("Pending", pending));
        series.getData().add(new XYChart.Data<>("In Delivery", delivering));
        barChart.getData().add(series);

        // Pie Chart
        pieChart.getData().clear();
        if (totalAssigned > 0) {
            pieChart.getData().add(new PieChart.Data("Assigned", assigned));
            pieChart.getData().add(new PieChart.Data("In Delivery", delivering));
            pieChart.getData().add(new PieChart.Data("Delivered", delivered));
            if (pending > 0) {
                pieChart.getData().add(new PieChart.Data("Pending", pending));
            }
        }
    }
}

