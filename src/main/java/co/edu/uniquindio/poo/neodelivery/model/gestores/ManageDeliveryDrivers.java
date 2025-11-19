package co.edu.uniquindio.poo.neodelivery.model.gestores;

import co.edu.uniquindio.poo.neodelivery.model.DeliveryDriver;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;

import java.util.List;

public class ManageDeliveryDrivers {
    private DataBase db = DataBase.getInstance();

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
        int maxId = 0;

        for (DeliveryDriver deliveryDriver : db.getListaRepartidores()) {
            try {
                int id = Integer.parseInt(deliveryDriver.getId());
                if (id > maxId) maxId = id;
            } catch (NumberFormatException e) {

            }
        }

        return String.valueOf(maxId + 1);
    }


    public void createDeliveryDriver(DeliveryDriver deliveryDriver) {
        db.getListaRepartidores().add(deliveryDriver);
        DataBase.getInstance().saveToJson();
    }

    public void deleteDeliveyDriver(DeliveryDriver deliveryDriver) {
        db.getListaRepartidores().remove(deliveryDriver);
        DataBase.getInstance().saveToJson();
    }

    //Falta el metodo de update
    public void updateDeliveryDriverr(String idDeliveryDriver, DeliveryDriver deliveryDriverUpdated) {
        DeliveryDriver deliveryDriverToUpdate = findDeliveryDriver(idDeliveryDriver);
        deliveryDriverToUpdate.setName(deliveryDriverUpdated.getName());
        deliveryDriverToUpdate.setEmail(deliveryDriverUpdated.getEmail());
        deliveryDriverToUpdate.setPassword(deliveryDriverUpdated.getPassword());
        DataBase.getInstance().saveToJson();
    }


    public List<DeliveryDriver> getAllDeliveryUser() {
        return db.getListaRepartidores();
    }
}
