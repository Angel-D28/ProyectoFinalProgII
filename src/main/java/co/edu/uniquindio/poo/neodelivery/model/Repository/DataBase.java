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

        listaAdmin.add(new Admin("1", "Carlos Molina", "cadmin@neo.com",
                "3208489702", Utils.hashPassword("admin1")));

        Shipment shipment1 = new Shipment.Builder()
                .id("SHP001")
                .origin(new Address("Calle 1, Armenia"))
                .destination(new Address("SAO, Armenia"))
                .weight(2.5)
                .volume(1.2)
                .cost(15.0)
                .status(Status.PENDING)
                .hasInsurance(true)
                .isPriority(true)
                .requiresSignature(true)
                .fragile(false)
                .build();

        Shipment shipment2 = new Shipment.Builder()
                .id("SHP002")
                .origin(new Address("Mz C Cs 5, Cali"))
                .destination(new Address("Cra 5 Cll 20, Salento"))
                .weight(1.0)
                .volume(0.5)
                .cost(7.5)
                .status(Status.PENDING)
                .hasInsurance(false)
                .isPriority(false)
                .requiresSignature(false)
                .fragile(true)
                .build();

        Shipment shipment3 = new Shipment.Builder()
                .id("SHP003")
                .origin(new Address("Calle 3, Armenia"))
                .destination(new Address("Calle 9, Armenia"))
                .weight(3.0)
                .volume(2.0)
                .cost(20.0)
                .status(Status.PENDING)
                .hasInsurance(true)
                .isPriority(false)
                .requiresSignature(true)
                .fragile(false)
                .build();

        Shipment shipment4 = new Shipment.Builder()
                .id("SHP004")
                .origin(new Address("Calle 4, Ciudad G"))
                .destination(new Address("Calle 10, Ciudad H"))
                .weight(0.5)
                .volume(0.3)
                .cost(5.0)
                .status(Status.PENDING)
                .hasInsurance(false)
                .isPriority(false)
                .requiresSignature(false)
                .fragile(true)
                .build();

        shipment1.assignDriver(listaRepartidores.get(0));
        shipment2.assignDriver(listaRepartidores.get(1));

        listaShipments.add(shipment1);
        listaShipments.add(shipment2);
        listaShipments.add(shipment3);
        listaShipments.add(shipment4);

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
