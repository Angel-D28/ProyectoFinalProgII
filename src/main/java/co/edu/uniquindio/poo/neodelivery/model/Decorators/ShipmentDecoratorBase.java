package co.edu.uniquindio.poo.neodelivery.model.Decorators;

import co.edu.uniquindio.poo.neodelivery.model.IShipment;
import co.edu.uniquindio.poo.neodelivery.model.Shipment;

//Se crea para unir el patron strategy y decorator
public class ShipmentDecoratorBase implements IShipment {

    private final Shipment shipment;
    private final double baseCost;

    public ShipmentDecoratorBase(Shipment shipment, double baseCost) {
        this.shipment = shipment;
        this.baseCost = baseCost;
    }

    @Override
    public double getCost() {
        return baseCost;
    }

    @Override
    public String getDescription() {
        return shipment.getDescription() + " | costo base calculado";
    }

    public Shipment getShipment() {
        return shipment;
    }
}
