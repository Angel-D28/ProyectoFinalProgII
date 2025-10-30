package co.edu.uniquindio.poo.neodelivery.model;

import java.io.Serializable;

public class NequiAdapter implements IPaymentProcessor{

    private ExternalAPIs.ExternalNequiAPI nequiAPI = new ExternalAPIs.ExternalNequiAPI();

    @Override
    public boolean process(double amount) {
        System.out.println("Adaptando pago a nequi Api");
        return nequiAPI.sendPayment(amount);
    }
}
