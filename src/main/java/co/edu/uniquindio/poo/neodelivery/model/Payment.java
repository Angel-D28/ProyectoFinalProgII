package co.edu.uniquindio.poo.neodelivery.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DigitalWalletPayment.class, name = "digitalWallet"),
        @JsonSubTypes.Type(value = CardPayment.class, name = "card"),
        @JsonSubTypes.Type(value = CashPayment.class, name = "cash")
})

public abstract class Payment implements Subject  {
    protected String idPayment;
    protected double amount;
    protected LocalDate paymentDate;
    protected StatusPayment status;
    private List<Observer> observers = new ArrayList<>();
    protected String paymentMethodName = "Unknown";

    public Payment(){}

    public Payment(double amount){
        this.amount = amount;
        this.paymentDate = LocalDate.now();
        this.idPayment = "PAY-" + System.currentTimeMillis();
        this.status= StatusPayment.Pending;
    }

    public abstract void processPayment();

    public String getIdPayment() {
        return idPayment;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public StatusPayment getStatus() {
        return status;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setStatus(StatusPayment newStatus) {
        this.status = status;
        notifyObservers("El envío " + idPayment + " cambió su estado a: " + newStatus);
    }

    @Override
    public String toString() {
        return "idPayment: " + idPayment + "Cantidad" + amount + "FechaPago" + paymentDate + "Estado: " + status;
    }
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer obs : observers) {
            obs.update(message);
        }
    }
}
