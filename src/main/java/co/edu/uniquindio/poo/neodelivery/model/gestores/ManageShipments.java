package co.edu.uniquindio.poo.neodelivery.model.gestores;

import co.edu.uniquindio.poo.neodelivery.model.*;
import co.edu.uniquindio.poo.neodelivery.model.Decorators.*;
import co.edu.uniquindio.poo.neodelivery.model.Decorators.FragileDecorator;
import co.edu.uniquindio.poo.neodelivery.model.Decorators.PriorityDecorator;
import co.edu.uniquindio.poo.neodelivery.model.Decorators.SignatureDecorator;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;

public class ManageShipments {

    private final DataBase db = DataBase.getInstance();
    private final ShipmentCostCalculator calculator = new ShipmentCostCalculator();

    public void assignDriver(Admin admin, Shipment shipment, DeliveryDriver driver) {
        if (shipment.getStatus() == Status.PENDING) {
            shipment.assignerDriver(admin, driver);
            driver.setAvalibility(false);
            driver.setShipmentAssigned(shipment);
            shipment.notifyObservers("Tu envío " + shipment.getId() + " fue asignado a " + driver.getName());
            DataBase.getInstance().saveToJson();
        } else {
            System.out.println("No se puede asignar el envío (Estado actual: " + shipment.getStatus() + ")");
        }
    }

    public void collectShipment(DeliveryDriver driver, Shipment shipment) {
        if (shipment.getAssignedDriver() != null && driver.equals(shipment.getAssignedDriver())) {
            shipment.collect(driver);
            driver.setShipmentAssigned(shipment);
            driver.setAvalibility(false);
            shipment.notifyObservers("Tu envío " + shipment.getId() + " ha sido recogido por el repartidor " + driver.getName());
            DataBase.getInstance().saveToJson();
        } else {
            System.out.println("El envío no está listo o el repartidor no coincide.");
        }
    }

    public void deliverShipment(DeliveryDriver driver, Shipment shipment) {
        if (shipment.getAssignedDriver() != null && driver.equals(shipment.getAssignedDriver())) {
            shipment.deliver(driver);
            driver.setShipmentAssigned(null);
            driver.setAvalibility(true);
            shipment.notifyObservers("Tu envío " + shipment.getId() + " ha sido entregado exitosamente.");
            DataBase.getInstance().saveToJson();
        } else {
            System.out.println("Solo el repartidor asignado puede entregar el envío.");
        }
    }

    public Shipment createShipment(User client, Address origin, Address destination, double weight, double volume,
                                   boolean insurance, boolean priority, boolean requireSignature, boolean fragile, Payment payment) {

        Shipment shipment = new Shipment.Builder()
                .id(generateID())
                .origin(origin)
                .destination(destination)
                .weight(weight)
                .volume(volume)
                .hasInsurance(insurance)
                .isPriority(priority)
                .requiresSignature(requireSignature)
                .fragile(fragile)
                .build();

        shipment.setPayment(payment);
        shipment.getPayment().addObserver(client);

        db.getListaEnvios().add(shipment);
        client.getShipmentsList().add(shipment);
        DataBase.getInstance().saveToJson();

        return shipment;
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

        if (shipment.isHasInsurance()) decorated = new InsuranceDecorator(decorated);
        if (shipment.isPriority()) decorated = new PriorityDecorator(decorated);
        if (shipment.isRequiresSignature()) decorated = new SignatureDecorator(decorated);
        if (shipment.isFragile()) decorated = new FragileDecorator(decorated);

        double finalCost = decorated.getCost();
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
                } catch (NumberFormatException ignored) {}
            }
        }
        return "SHP" + (maxId + 1);
    }
}
