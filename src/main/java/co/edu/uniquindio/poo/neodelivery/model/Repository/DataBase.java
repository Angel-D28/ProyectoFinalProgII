package co.edu.uniquindio.poo.neodelivery.model.Repository;

import co.edu.uniquindio.poo.neodelivery.model.*;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;

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
        System.out.println(">>> Constructor ejecutado");
        this.listaUsers = new ArrayList<>();
        this.listaShipments = new ArrayList<>();
        this.listaRepartidores = new ArrayList<>();
        this.listaPayments = new ArrayList<>();
        this.listaAdmin = new ArrayList<>();

        inicializarDatos();
    }


    public static DataBase getInstance() {
        if(Instance == null) {
            Instance = new DataBase();
        }
        return Instance;
    }

    private void inicializarDatos() {

        System.out.println(">>> Cargando datos dummy...");

        listaUsers.add(new User("Alberto Pérez", Utils.hashPassword("112233"), "alberto@neo.com",
                new Address("Mz C Casa 2"), "3007778888", "1"));

        listaUsers.add(new User("Federico Castaño", Utils.hashPassword("1234"), "fedecastaño@neo.com",
                new Address("Cra 14 Apto 2"), "3213332233", "2"));

        listaUsers.add(new User("Daniela Vélez", Utils.hashPassword("danielita"), "danielav@neo.com",
                new Address("Cra 15 Apto 401, Torre B"), "3018889302", "3"));

        listaRepartidores.add(new DeliveryDriver(
                "1", "Luis Herrera", Utils.hashPassword("1234"),
                "luis@neo.com"
        ));

        listaRepartidores.add(new DeliveryDriver(
                "2", "Pedro Sanchez", Utils.hashPassword("5678"),
                "pedro@neo.com"
        ));

        listaAdmin.add(new Admin("1", "Admin", "admin@neo.com",
                "3000000000", Utils.hashPassword("admin1")));

        System.out.println("Admins = " + listaAdmin.size());
        System.out.println("Users = " + listaUsers.size());
        System.out.println("Drivers = " + listaRepartidores.size());

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
