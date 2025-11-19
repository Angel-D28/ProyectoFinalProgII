package co.edu.uniquindio.poo.neodelivery.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

public class DeliveryDriver {
    private String id;
    private String name;
    private String email;
    private String password;

    @JsonBackReference
    private Shipment shipmentAssigned;
    private boolean avalibility;
    private String profilePicturePath;

    public DeliveryDriver(){}

    public DeliveryDriver(String id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.shipmentAssigned = null;
        this.avalibility = true;
    }

    public String toString(){
        return "Nombre: " +name+ " Id: "+ id + " Envio asignado: "+ shipmentAssigned.getId();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Shipment getShipmentAssigned() {
        return shipmentAssigned;
    }

    public void setShipmentAssigned(Shipment shipmentAssigned) {
        this.shipmentAssigned = shipmentAssigned;
    }

    public String getName() { return name; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAvalibility() {
        return avalibility;
    }

    public void setAvalibility(boolean avalibility) {
        this.avalibility = avalibility;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }
}
