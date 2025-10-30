package co.edu.uniquindio.poo.neodelivery.model;

import java.time.LocalDate;

public abstract class Payment {
    protected String idPayment;
    protected double amount;
    protected LocalDate paymentDate;
    protected StatusPayment status;

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

    public void setStatus(StatusPayment status) {
        this.status = status;
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
}
