package co.edu.uniquindio.poo.neodelivery.model;

public class Shipment {
    private String id;
    private Address origin;
    private Address destination;
    private double weight;
    private double volume;
    private double cost;
    private Status status; // "Solicitado", "Asignado", etc.
    private boolean hasInsurance;
    private boolean isPriority;
    private boolean requiresSignature;

    private Shipment(Builder builder){
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
    }

    public static class Builder{
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

        public Builder id(String id){
            this.id = id;
            return this;
        }

        public Builder origin(Address origin){
            this.origin = origin;
            return this;
        }

        public Builder destination(Address destination){
            this.destination = destination;
            return this;
        }
        public Builder weight(double weight){
            this.weight = weight;
            return this;
        }
        public Builder volume(double volume){
            this.volume = volume;
            return this;
        }
        public Builder cost(double cost){
            this.cost = cost;
            return this;
        }
        public Builder status(Status status){
            this.status = status;
            return this;
        }
        public Builder hasInsurance(boolean hasInsurance){
            this.hasInsurance = hasInsurance;
            return this;
        }
        public Builder isPriority(boolean isPriority){
            this.isPriority = isPriority;
            return this;
        }
        public Builder requiresSignature(boolean requiresSignature){
            this.requiresSignature = requiresSignature;
            return this;
        }
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
