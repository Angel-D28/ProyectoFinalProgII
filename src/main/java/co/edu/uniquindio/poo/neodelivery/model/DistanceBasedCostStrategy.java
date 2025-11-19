package co.edu.uniquindio.poo.neodelivery.model;

public class DistanceBasedCostStrategy implements ShipmentCostStrategy{
    @Override
    public double calculateCost(Shipment shipment) {
        // Simulación: el costo depende de una distancia ficticia
        double distanceKm = 25; // En el futuro se puede calcular real
        return 1000 + (distanceKm * 500);
    }
}
