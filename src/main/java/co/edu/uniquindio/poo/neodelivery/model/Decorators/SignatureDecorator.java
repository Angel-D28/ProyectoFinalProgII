package co.edu.uniquindio.poo.neodelivery.model.Decorators;

import co.edu.uniquindio.poo.neodelivery.model.IShipment;

public class SignatureDecorator extends ShipmentDecorator {

    public SignatureDecorator(IShipment shipment) {
        super(shipment);
    }

    @Override
    public double getCost() {
        return shipment.getCost() + 2000;
    }

    @Override
    public String getDescription() {
        return shipment.getDescription() + "\n requiere firma del receptor";
    }
}
