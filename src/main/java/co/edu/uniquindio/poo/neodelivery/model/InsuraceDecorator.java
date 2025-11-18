package co.edu.uniquindio.poo.neodelivery.model;



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
