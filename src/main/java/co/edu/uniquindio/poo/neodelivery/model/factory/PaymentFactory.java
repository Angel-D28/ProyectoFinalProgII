package co.edu.uniquindio.poo.neodelivery.model.factory;

import co.edu.uniquindio.poo.neodelivery.model.Payment;

public abstract class PaymentFactory {
    public abstract Payment createPayment(double amount);
}
