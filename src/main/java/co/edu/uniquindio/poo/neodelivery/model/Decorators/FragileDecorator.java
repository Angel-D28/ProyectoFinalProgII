package co.edu.uniquindio.poo.neodelivery.model.Decorators;

import co.edu.uniquindio.poo.neodelivery.model.IShipment;

public class FragileDecorator extends ShipmentDecorator {

    public FragileDecorator(IShipment shipment) {
        super(shipment);
    }

    @Override
    public double getCost() {
        return shipment.getCost() + 3000;
    }

    @Override
    public String getDescription() {
        return shipment.getDescription() + "\n manejo frágil";
    }
}
