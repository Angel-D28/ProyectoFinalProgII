package co.edu.uniquindio.poo.neodelivery.model;

public enum Status {
    PENDING, DELIVERASSIGNED ,DELIVERED, DELIVERING;

    @Override
    public String toString() {
        return switch(this) {
            case PENDING -> "Pending";
            case DELIVERASSIGNED -> "Deliver Assigned";
            case DELIVERED -> "Delivered";
            case DELIVERING -> "Delivering";
        };
    }

}
