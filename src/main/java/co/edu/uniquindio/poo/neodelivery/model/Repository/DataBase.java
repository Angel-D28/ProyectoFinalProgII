package co.edu.uniquindio.poo.neodelivery.model.Repository;

import co.edu.uniquindio.poo.neodelivery.model.*;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private static DataBase Instance;

    private List<User> listaUsers;
    private List<Shipment> listaShipments;
    private List<DeliveryDriver> listaRepartidores;
    private List<Payment> listaPayments;
    private List<Admin> listaAdmin;

    private DataBase() {
    this.listaUsers = new ArrayList<>();
    this.listaShipments = new ArrayList<>();
    this.listaRepartidores = new ArrayList<>();
    this.listaPayments = new ArrayList<>();
    this.listaAdmin = new ArrayList<>();
    }




    public static DataBase getInstance() {
        if(Instance == null) {
            Instance = new DataBase();
        }
        return Instance;
    }

    public List<User> getListaUsuarios() {
        return listaUsers;
    }

    public void setListaUsuarios(List<User> listaUsers) {
        this.listaUsers = listaUsers;
    }

    public List<Shipment> getListaEnvios() {
        return listaShipments;
    }

    public void setListaEnvios(List<Shipment> listaShipments) {
        this.listaShipments = listaShipments;
    }

    public List<DeliveryDriver> getListaRepartidores() {
        return listaRepartidores;
    }

    public void setListaRepartidores(List<DeliveryDriver> listaRepartidores) {
        this.listaRepartidores = listaRepartidores;
    }

    public List<Payment> getListaPagos() {
        return listaPayments;
    }

    public void setListaPagos(List<Payment> listaPayments) {
        this.listaPayments = listaPayments;
    }

    public List<Admin> getListaAdmin() {
        return listaAdmin;
    }

    public void setListaAdmin(List<Admin> listaAdmin) {
        this.listaAdmin = listaAdmin;
    }
}
