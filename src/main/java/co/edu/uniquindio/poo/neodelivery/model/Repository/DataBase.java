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
                .id("SHP1")
                .origin(new Address("Calle 1, Armenia"))
                .destination(new Address("SAO, Armenia"))
                .weight(2.5)
                .volume(1.2)
                .cost(15.0)
                .hasInsurance(true)
                .isPriority(true)
                .requiresSignature(true)
                .fragile(false)
                .build();

        Shipment shipment2 = new Shipment.Builder()
                .id("SHP2")
                .origin(new Address("Mz C Cs 5, Cali"))
                .destination(new Address("Cra 5 Cll 20, Salento"))
                .weight(1.0)
                .volume(0.5)
                .cost(7.5)
                .hasInsurance(false)
                .isPriority(false)
                .requiresSignature(false)
                .fragile(true)
                .build();

        Shipment shipment3 = new Shipment.Builder()
                .id("SHP3")
                .origin(new Address("Calle 3, Armenia"))
                .destination(new Address("Calle 9, Armenia"))
                .weight(3.0)
                .volume(2.0)
                .cost(20.0)
                .hasInsurance(true)
                .isPriority(false)
                .requiresSignature(true)
                .fragile(false)
                .build();

        Shipment shipment4 = new Shipment.Builder()
                .id("SHP4")
                .origin(new Address("Calle 4, Ciudad G"))
                .destination(new Address("Calle 10, Ciudad H"))
                .weight(0.5)
                .volume(0.3)
                .cost(5.0)
                .hasInsurance(false)
                .isPriority(false)
                .requiresSignature(false)
                .fragile(true)
                .build();

        Shipment shipment5 = new Shipment.Builder()
                .id("SHP5")
                .origin(new Address("Calle 20 #15-30, Manizales"))
                .destination(new Address("Centro Comercial Portal del Quindío, Armenia"))
                .weight(4.2)
                .volume(1.8)
                .cost(32.0)
                .hasInsurance(true)
                .isPriority(true)
                .requiresSignature(true)
                .fragile(true)
                .build();

        Shipment shipment6 = new Shipment.Builder()
                .id("SHP6")
                .origin(new Address("Zona Franca, Pereira"))
                .destination(new Address("Cra 14 #7-20, Armenia"))
                .weight(1.7)
                .volume(0.9)
                .cost(12.5)
                .hasInsurance(false)
                .isPriority(false)
                .requiresSignature(true)
                .fragile(false)
                .build();

        DeliveryDriver driverLuis = listaRepartidores.get(0);
        DeliveryDriver driverPedro = listaRepartidores.get(1);

        shipment1.setAssignedDriver(driverLuis);
        shipment1.setStatus(Status.DELIVERASSIGNED);

        shipment2.setAssignedDriver(driverLuis);
        shipment2.setStatus(Status.DELIVERING);

        shipment3.setAssignedDriver(driverPedro);
        shipment3.setStatus(Status.DELIVERED);

        shipment5.setAssignedDriver(driverPedro);
        shipment5.setStatus(Status.DELIVERASSIGNED);

        shipment6.setAssignedDriver(driverPedro);
        shipment6.setStatus(Status.DELIVERING);

        listaShipments.add(shipment1);
        listaShipments.add(shipment2);
        listaShipments.add(shipment3);
        listaShipments.add(shipment4);
        listaShipments.add(shipment5);
        listaShipments.add(shipment6);

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
