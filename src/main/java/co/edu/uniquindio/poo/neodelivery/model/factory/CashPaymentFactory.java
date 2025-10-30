package co.edu.uniquindio.poo.neodelivery.model.factory;

import co.edu.uniquindio.poo.neodelivery.model.CashPayment;
import co.edu.uniquindio.poo.neodelivery.model.Payment;

public class CashPaymentFactory extends PaymentFactory {
    @Override
    public Payment createPayment(double amount) {
        return new CashPayment(amount);
    }
}
