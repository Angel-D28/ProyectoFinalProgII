package co.edu.uniquindio.poo.neodelivery.model.Repository;

import co.edu.uniquindio.poo.neodelivery.model.Shipment;
import co.edu.uniquindio.poo.neodelivery.model.ShipmentDecorator;

public class InsuraceDecorator extends ShipmentDecorator {

    public InsuraceDecorator(Shipment shipment) {
        super(shipment);
    }

    @Override
    public double getCost() {
        double costShipmentIns = shipment.getCost()+ 5000;
        return costShipmentIns;
    }

    @Override
    public String getDescription() {
        return shipment.getDescription() + "\n con seguro adicional";
    }
}
