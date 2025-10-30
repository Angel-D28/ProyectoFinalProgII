package co.edu.uniquindio.poo.neodelivery.model.factory;

import co.edu.uniquindio.poo.neodelivery.model.CardPayment;
import co.edu.uniquindio.poo.neodelivery.model.Payment;

public class CardPaymentFactory extends PaymentFactory {
    private String cardNumber;

    public CardPaymentFactory(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public Payment createPayment(double amount) {
        return new CardPayment(amount, cardNumber);
    }
}
