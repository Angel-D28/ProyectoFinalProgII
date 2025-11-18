package co.edu.uniquindio.poo.neodelivery.model;

import co.edu.uniquindio.poo.neodelivery.model.State.PendingState;
import co.edu.uniquindio.poo.neodelivery.model.State.ShipmentState;

import java.util.ArrayList;
import java.util.List;

public class Shipment implements IShipment, Subject {

    private String id;
    private Address origin;
    private Address destination;
    private double weight;
    private double volume;
    private double cost;
    private Status status;
    private boolean hasInsurance;
    private boolean isPriority;
    private boolean requiresSignature;
    private boolean fragile;
    private Payment payment;

    private ShipmentState state;
    private DeliveryDriver assignedDriver;

    private final List<Observer> observers = new ArrayList<>();

    private Shipment(Builder builder) {
        this.id = builder.id;
        this.origin = builder.origin;
        this.destination = builder.destination;
        this.weight = builder.weight;
        this.volume = builder.volume;
        this.cost = builder.cost;

        this.hasInsurance = builder.hasInsurance;
        this.isPriority = builder.isPriority;
        this.requiresSignature = builder.requiresSignature;
        this.fragile = builder.fragile;


        this.state = new PendingState();
        this.status = Status.PENDING;
    }

    // BUILDER
    public static class Builder {
        private String id;
        private Address origin;
        private Address destination;
        private double weight;
        private double volume;
        private double cost;
        private boolean hasInsurance;
        private boolean isPriority;
        private boolean requiresSignature;
        private boolean fragile;

        public Builder id(String id) { this.id = id; return this; }
        public Builder origin(Address origin) { this.origin = origin; return this; }
        public Builder destination(Address destination) { this.destination = destination; return this; }
        public Builder weight(double weight) { this.weight = weight; return this; }
        public Builder volume(double volume) { this.volume = volume; return this; }
        public Builder cost(double cost) { this.cost = cost; return this; }
        public Builder hasInsurance(boolean hasInsurance) { this.hasInsurance = hasInsurance; return this; }
        public Builder isPriority(boolean isPriority) { this.isPriority = isPriority; return this; }
        public Builder requiresSignature(boolean requiresSignature) { this.requiresSignature = requiresSignature; return this; }
        public Builder fragile(boolean fragile) { this.fragile = fragile; return this; }

        public Shipment build() { return new Shipment(this); }
    }

    // STATE DELEGATION

    public void assignerDriver(Admin admin, DeliveryDriver driver) {
        state.assignerDriver(this, admin, driver);
    }

    public void collect(DeliveryDriver driver) {
        state.collect(this, driver);
    }

    public void deliver(DeliveryDriver driver) {
        state.deliver(this, driver);
    }

    public void setState(ShipmentState newState) {
        this.state = newState;
    }

    public ShipmentState getState() {
        return state;
    }



    public void setStatus(Status newStatus) {
        this.status = newStatus;
        notifyObservers("El envío " + id + " cambió su estado a: " + newStatus);
    }

    public String getPaymentMethodName() {
        if (payment == null) return "Not paid";

        return switch (payment.getClass().getSimpleName()) {
            case "CashPayment" -> "Cash";
            case "CardPayment" -> "Card";
            case "DaviplataPayment" -> "Daviplata";
            case "NequiPayment" -> "Nequi";
            default -> "Unknown";
        };
    }

    public DeliveryDriver getAssignedDriver() {
        return assignedDriver;
    }

    public void setAssignedDriver(DeliveryDriver assignedDriver) {
        this.assignedDriver = assignedDriver;
    }

    @Override
    public double getCost() { return cost; }

    @Override
    public String getDescription() {
        return "Envío desde " + origin + " hasta " + destination;
    }

  //OBSERVER

    @Override
    public void addObserver(Observer o) { observers.add(o); }

    @Override
    public void removeObserver(Observer o) { observers.remove(o); }

    @Override
    public void notifyObservers(String msg) {
        observers.forEach(o -> o.update(msg));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Address getOrigin() {
        return origin;
    }

    public void setOrigin(Address origin) {
        this.origin = origin;
    }

    public Address getDestination() {
        return destination;
    }

    public void setDestination(Address destination) {
        this.destination = destination;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isHasInsurance() {
        return hasInsurance;
    }

    public void setHasInsurance(boolean hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

    public boolean isPriority() {
        return isPriority;
    }

    public void setPriority(boolean priority) {
        isPriority = priority;
    }

    public boolean isRequiresSignature() {
        return requiresSignature;
    }

    public void setRequiresSignature(boolean requiresSignature) {
        this.requiresSignature = requiresSignature;
    }

    public boolean isFragile() {
        return fragile;
    }

    public void setFragile(boolean fragile) {
        this.fragile = fragile;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public List<Observer> getObservers() {
        return observers;
    }
}
