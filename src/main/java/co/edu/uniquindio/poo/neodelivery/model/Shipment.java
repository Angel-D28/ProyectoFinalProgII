package co.edu.uniquindio.poo.neodelivery.model;


import java.util.ArrayList;
import java.util.List;

public class Shipment implements IShipment , Subject {

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
    private DeliveryDriver assignedDriver;

    private List<Observer> observers = new ArrayList<>();

    private Shipment(Builder builder) {
        this.id = builder.id;
        this.origin = builder.origin;
        this.destination = builder.destination;
        this.weight = builder.weight;
        this.volume = builder.volume;
        this.cost = builder.cost;
        this.status = builder.status;
        this.hasInsurance = builder.hasInsurance;
        this.isPriority = builder.isPriority;
        this.requiresSignature = builder.requiresSignature;
        this.fragile = builder.fragile;
    }
    public static class Builder {

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

        public Builder id(String id) { this.id = id; return this; }
        public Builder origin(Address origin) { this.origin = origin; return this; }
        public Builder destination(Address destination) { this.destination = destination; return this; }
        public Builder weight(double weight) { this.weight = weight; return this; }
        public Builder volume(double volume) { this.volume = volume; return this; }
        public Builder cost(double cost) { this.cost = cost; return this; }
        public Builder status(Status status) { this.status = status; return this; }
        public Builder hasInsurance(boolean hasInsurance) { this.hasInsurance = hasInsurance; return this; }
        public Builder isPriority(boolean isPriority) { this.isPriority = isPriority; return this; }
        public Builder requiresSignature(boolean requiresSignature) { this.requiresSignature = requiresSignature; return this; }
        public Builder fragile(boolean fragile) { this.fragile = fragile; return this; }

        public Shipment build() { return new Shipment(this); }
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public String getDescription() {
        return "Envío desde " + origin + " hasta " + destination;
    }

    public void setStatus(Status newStatus) {
        this.status = newStatus;
        notifyObservers("El envío " + id + " cambió su estado a: " + newStatus);
    }
    @Override
    public void addObserver(Observer o) {
        observers.add(o); }
    @Override
    public void removeObserver(Observer o) {
        observers.remove(o); }
    @Override
    public void notifyObservers(String msg) {
        observers.forEach(o -> o.update(msg)); }

    public void assignDriver(DeliveryDriver driver) {
        this.assignedDriver = driver;
        this.status = Status.DELIVERASSIGNED;
        notifyObservers("El envío " + id + " ha sido asignado al repartidor " + driver.getName());
    }

    public Status getStatus() {
        return status;
    }
    public DeliveryDriver getAssignedDriver() { return assignedDriver; }

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

    public void setAssignedDriver(DeliveryDriver assignedDriver) {
        this.assignedDriver = assignedDriver;
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "id='" + id + '\'' +
                ", origin=" + origin +
                ", destination=" + destination +
                ", weight=" + weight +
                ", volume=" + volume +
                ", cost=" + cost +
                ", status=" + status +
                ", hasInsurance=" + hasInsurance +
                ", isPriority=" + isPriority +
                ", requiresSignature=" + requiresSignature +
                '}';
    }
}
