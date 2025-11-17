package co.edu.uniquindio.poo.neodelivery.model;

import java.util.ArrayList;
import java.util.List;

public class User implements Observer {
    private String name;
    private String password;
    private String email;
    private Address address;
    private String number;
    private String idUser;
    private String profilePicturePath;
    private List<Payment> paymentsMethodsList = new ArrayList<>();
    private List<Shipment>  shipmentsList = new ArrayList<>();

    public User(String name, String password, String email, Address address, String numbre, String idUser) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.number = numbre;
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    @Override
    public void update(String message) {
        System.out.println("Enviando gmail en tiempo real");
        EmailService.sendEmail(email,"Actualizacion de Envio", message);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getNumbre() {
        return number;
    }

    public void setNumbre(String numbre) {
        this.number = numbre;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }
    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public List<Payment> getPaymentsMethodsList() {
        return paymentsMethodsList;
    }

    public void setPaymentsMethodsList(List<Payment> paymentsMethodsList) {
        this.paymentsMethodsList = paymentsMethodsList;
    }

    public List<Shipment> getShipmentsList() {
        return shipmentsList;
    }

    public void setShipmentsList(List<Shipment> shipmentsList) {
        this.shipmentsList = shipmentsList;
    }

    public String toString() {
        return "Nombre:" + name + "Contraseña:" + password + "Email:" + email + "Direccion:" + address + "Número:" + number + "IdUsuario:" + idUser;
    }
}
