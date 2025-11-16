package co.edu.uniquindio.poo.neodelivery.model;

public class VolumeBasedCostStrategy implements ShipmentCostStrategy{
    @Override
    public double calculateCost(Shipment shipment) {
        double rate = 1500;
        return rate * shipment.getVolume();
    }
}
