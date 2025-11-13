package co.edu.uniquindio.poo.neodelivery.model.gestores;

import co.edu.uniquindio.poo.neodelivery.model.DeliveryDriver;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.User;

import java.util.List;

public class ManageDeliveryDrivers {
    private DataBase db = DataBase.getInstance();
    private int driverId;

    public DeliveryDriver findDeliveryDriver(String findId){
        DeliveryDriver driver = null;
        for(DeliveryDriver deliveryAux : db.getListaRepartidores()){
            if(deliveryAux.getId().equals(findId)){
                driver = deliveryAux;
            }
        }
        return driver;
    }

    public String showDeliveryDrivers() {
        String deliveryDrivers = "";
        for (DeliveryDriver driver : db.getListaRepartidores()) {
            deliveryDrivers += driver.toString() + "\n";
        }
        return deliveryDrivers;
    }

    public String generateId(){
        return String.valueOf(driverId++);
    }


    public void createDeliveryDriver(DeliveryDriver deliveryDriver) {
        db.getListaRepartidores().add(deliveryDriver);
    }

    public void deleteDeliveyDriver(DeliveryDriver deliveryDriver) {
        db.getListaRepartidores().remove(deliveryDriver);
    }

    //Falta el método de update


    public List<DeliveryDriver> getAllDeliveryUser() {
        return db.getListaRepartidores();
    }
}
