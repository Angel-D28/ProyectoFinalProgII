package co.edu.uniquindio.poo.neodelivery.model.Repository;

import co.edu.uniquindio.poo.neodelivery.model.*;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private static DataBase instance;

    private List<User> listaUsers;
    private List<Shipment> listaShipments;
    private List<DeliveryDriver> listaRepartidores;
    private List<Payment> listaPayments;
    private List<Admin> listaAdmin;

    private final String DATA_PATH = "data/database.json";

    private DataBase() {

        if (!loadFromJson()) {
            this.listaUsers = new ArrayList<>();
            this.listaShipments = new ArrayList<>();
            this.listaRepartidores = new ArrayList<>();
            this.listaPayments = new ArrayList<>();
            this.listaAdmin = new ArrayList<>();

            inicializarDatos();
            saveToJson();
        }
    }

    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    private boolean loadFromJson() {
        File file = new File(DATA_PATH);
        if (!file.exists()) return false;

        DataContainer container = JsonStorage.load(DATA_PATH, DataContainer.class);

        if (container == null) return false;

        this.listaUsers = container.users != null ? container.users : new ArrayList<>();
        this.listaShipments = container.shipments != null ? container.shipments : new ArrayList<>();
        this.listaRepartidores = container.drivers != null ? container.drivers : new ArrayList<>();
        this.listaPayments = container.payments != null ? container.payments : new ArrayList<>();
        this.listaAdmin = container.admins != null ? container.admins : new ArrayList<>();

        return true;
    }

    public void saveToJson() {
        DataContainer container = new DataContainer();
        container.users = this.listaUsers;
        container.shipments = this.listaShipments;
        container.drivers = this.listaRepartidores;
        container.payments = this.listaPayments;
        container.admins = this.listaAdmin;

        JsonStorage.save(DATA_PATH, container);
    }

    private void inicializarDatos() {
        if(listaAdmin.isEmpty()){
            Admin adminDefault = new Admin("1", "Carlos Molina", "caml.7carlos@gmail.com", "3208489702", Utils.hashPassword("Admin123@"));
            listaAdmin.add(adminDefault);
            saveToJson();
        }

    }


    public List<User> getListaUsuarios() { return listaUsers; }
    public void setListaUsuarios(List<User> listaUsers) { this.listaUsers = listaUsers; saveToJson(); }

    public List<Shipment> getListaEnvios() { return listaShipments; }
    public void setListaEnvios(List<Shipment> listaShipments) { this.listaShipments = listaShipments; saveToJson(); }

    public List<DeliveryDriver> getListaRepartidores() { return listaRepartidores; }
    public void setListaRepartidores(List<DeliveryDriver> listaRepartidores) { this.listaRepartidores = listaRepartidores; saveToJson(); }

    public List<Payment> getListaPagos() { return listaPayments; }
    public void setListaPagos(List<Payment> listaPayments) { this.listaPayments = listaPayments; saveToJson(); }

    public List<Admin> getListaAdmin() { return listaAdmin; }
    public void setListaAdmin(List<Admin> listaAdmin) { this.listaAdmin = listaAdmin; saveToJson(); }
}
