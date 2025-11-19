package co.edu.uniquindio.poo.neodelivery.model;

import java.io.Serializable;

public class NequiAdapter implements IPaymentProcessor{

    private ExternalAPIs.ExternalNequiAPI nequiAPI = new ExternalAPIs.ExternalNequiAPI();

    @Override
    public boolean authenticate(String number, String password) {
        // Simulación realista
        System.out.println("📲 Autenticando con Nequi...");
        return number.matches("\\d{10}") && password.matches("\\d{6}");
    }

    @Override
    public boolean process(double amount) {
        System.out.println("Adaptando pago a nequi Api");
        return nequiAPI.sendPayment(amount);
    }
}
