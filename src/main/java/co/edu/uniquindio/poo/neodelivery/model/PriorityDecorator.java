package co.edu.uniquindio.poo.neodelivery.model;

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
