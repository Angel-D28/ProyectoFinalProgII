package co.edu.uniquindio.poo.neodelivery.model.State;


import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.DeliveryDriver;
import co.edu.uniquindio.poo.neodelivery.model.Shipment;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PendingState.class, name = "Pending"),
        @JsonSubTypes.Type(value = DeliveredState.class, name = "Delivered"),
        @JsonSubTypes.Type(value = DeliveringState.class, name = "Delivering"),
        @JsonSubTypes.Type(value = DeliverAssignedState.class, name = "Deliver assigned")
})


public interface ShipmentState {
    void assignerDriver(Shipment shipment , Admin admin , DeliveryDriver driver);
    void collect(Shipment shipment , DeliveryDriver driver);
    void deliver(Shipment shipment , DeliveryDriver driver);
    String getStatusName();
}
