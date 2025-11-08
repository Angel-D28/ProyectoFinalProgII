package co.edu.uniquindio.poo.neodelivery.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Payment implements Subject  {
    protected String idPayment;
    protected double amount;
    protected LocalDate paymentDate;
    protected StatusPayment status;
    private List<Observer> observers = new ArrayList<>();

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

    public void setStatus(StatusPayment newStatus) {
        this.status = status;
        notifyObservers("El envío " + idPayment + " cambió su estado a: " + newStatus);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "idPayment='" + idPayment + '\'' +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", status=" + status +
                '}';
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
