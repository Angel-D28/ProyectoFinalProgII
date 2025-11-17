package co.edu.uniquindio.poo.neodelivery.model;

public class ShipmentCostCalculator {
    private ShipmentCostStrategy strategy;

    public void setStrategy(ShipmentCostStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculate(Shipment shipment) {
        if (strategy == null) {
            throw new IllegalStateException("Debe asignarse una estrategia de cálculo antes de usar el calculador.");
        }
        return strategy.calculateCost(shipment);
    }
}
