package co.edu.uniquindio.poo.neodelivery.model.gestores;

import co.edu.uniquindio.poo.neodelivery.model.*;
import co.edu.uniquindio.poo.neodelivery.model.Decorators.*;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;

public class ManageShipments {

    private DataBase db = DataBase.getInstance();
    private final ShipmentCostCalculator calculator = new ShipmentCostCalculator();

    public void assignDriver(Admin admin, Shipment shipment, DeliveryDriver driver) {
        if (shipment.getStatus() == Status.PENDING) {
            shipment.setAssignedDriver(driver);
            shipment.setStatus(Status.DELIVERASSIGNED);
            driver.setAvalibility(false);

            System.out.println("Admin " + admin.getName() +
                    " asignó el envío " + shipment.getId() +
                    " al repartidor " + driver.getName());
        } else {
            System.out.println("No se puede asignar el envío (Estado actual: " + shipment.getStatus() + ")");
        }
    }

    public void collectShipment(DeliveryDriver driver, Shipment shipment) {
        if (shipment.getStatus() == Status.DELIVERASSIGNED &&
                driver.equals(shipment.getAssignedDriver())) {

            shipment.setStatus(Status.DELIVERING);
            System.out.println("El repartidor " + driver.getName() +
                    " recogió el envío con ID: " + shipment.getId());
        } else {
            System.out.println(" El envío no está listo o el repartidor no coincide.");
        }
    }

    public double calculateShipmentCost(Shipment shipment, String method) {
        switch (method.toLowerCase()) {
            case "peso" -> calculator.setStrategy(new WeightBasedCostStrategy());
            case "volumen" -> calculator.setStrategy(new VolumeBasedCostStrategy());
            case "distancia" -> calculator.setStrategy(new DistanceBasedCostStrategy());
            default -> throw new IllegalArgumentException("Método de cálculo desconocido: " + method);
        }

        double baseCost = calculator.calculate(shipment);
        IShipment decorated = new ShipmentDecoratorBase(shipment, baseCost);

        if (shipment.isHasInsurance())
            decorated = new InsuranceDecorator(decorated);
        if (shipment.isPriority())
            decorated = new PriorityDecorator(decorated);
        if (shipment.isRequiresSignature())
            decorated = new SignatureDecorator(decorated);
        if (shipment.isFragile())
            decorated = new FragileDecorator((decorated));

        double finalCost = decorated.getCost();
        System.out.println("Costo final del envío: $" + finalCost);

        shipment.setCost(finalCost);
        return finalCost;

    }

    public String generateID() {
        int maxId = 0;

        for (Shipment shipment : db.getListaEnvios()) {
            String idStr = shipment.getId();
            if (idStr.startsWith("SHP")) {
                try {
                    int num = Integer.parseInt(idStr.substring(3));
                    if (num > maxId) maxId = num;
                } catch (NumberFormatException e) {
                }
            }
        }

        return "SHP" + (maxId + 1);
    }

}
