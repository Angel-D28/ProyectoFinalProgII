package co.edu.uniquindio.poo.neodelivery.model;

public class WeightBasedCostStrategy implements ShipmentCostStrategy{
    @Override
    public double calculateCost(Shipment shipment) {
        double baseRate = 5000;
        double cost = baseRate * shipment.getWeight();
        return cost;
    }
}
