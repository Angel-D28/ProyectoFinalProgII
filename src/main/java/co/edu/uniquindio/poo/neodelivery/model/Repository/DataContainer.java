package co.edu.uniquindio.poo.neodelivery.model.Repository;

import co.edu.uniquindio.poo.neodelivery.model.*;

import java.util.List;

public class DataContainer {

    public List<User> users;
    public List<Shipment> shipments;
    public List<DeliveryDriver> drivers;
    public List<Payment> payments;
    public List<Admin> admins;

    public DataContainer() {
    }
}
