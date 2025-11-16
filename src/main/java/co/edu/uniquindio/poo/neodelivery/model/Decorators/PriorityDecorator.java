package co.edu.uniquindio.poo.neodelivery.model.Decorators;

import co.edu.uniquindio.poo.neodelivery.model.IShipment;

public class PriorityDecorator extends ShipmentDecorator {

    public PriorityDecorator(IShipment shipment) {
        super(shipment);
    }

    @Override
    public double getCost() {
        return shipment.getCost() + 8000;
    }

    @Override
    public String getDescription() {
        return shipment.getDescription() + "\n envío prioritario";
    }
}
