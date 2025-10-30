package co.edu.uniquindio.poo.neodelivery.model.factory;

import co.edu.uniquindio.poo.neodelivery.model.DigitalWalletPayment;
import co.edu.uniquindio.poo.neodelivery.model.Payment;

public class DigitalWalletPaymentFactory extends PaymentFactory {
    private String provider;
    private String number;

    public DigitalWalletPaymentFactory(String provider, String number) {
        this.provider = provider; //Nequi , daviplata
        this.number = number;
    }

    @Override
    public Payment createPayment(double amount) {
        return new DigitalWalletPayment(amount, provider , number);
    }
}
