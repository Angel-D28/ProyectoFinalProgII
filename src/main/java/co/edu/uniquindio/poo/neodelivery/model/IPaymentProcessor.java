package co.edu.uniquindio.poo.neodelivery.model;

public interface IPaymentProcessor {
    boolean authenticate(String number, String password);
    boolean process(double amount);
}
