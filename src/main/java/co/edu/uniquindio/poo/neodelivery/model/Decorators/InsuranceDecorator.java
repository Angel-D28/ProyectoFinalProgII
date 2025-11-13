package co.edu.uniquindio.poo.neodelivery.model.Decorators;


import co.edu.uniquindio.poo.neodelivery.model.IShipment;

public class InsuranceDecorator extends ShipmentDecorator {

    public InsuranceDecorator(IShipment shipment) {
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
