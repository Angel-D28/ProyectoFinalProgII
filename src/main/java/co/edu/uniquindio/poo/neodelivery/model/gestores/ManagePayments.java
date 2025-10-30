package co.edu.uniquindio.poo.neodelivery.model.gestores;

import co.edu.uniquindio.poo.neodelivery.model.*;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.factory.CardPaymentFactory;
import co.edu.uniquindio.poo.neodelivery.model.factory.CashPaymentFactory;
import co.edu.uniquindio.poo.neodelivery.model.factory.DigitalWalletPaymentFactory;
import co.edu.uniquindio.poo.neodelivery.model.factory.PaymentFactory;

import java.util.List;

public class ManagePayments {
    private DataBase db = DataBase.getInstance();

    // Crea y procesa un pago según el tipo seleccionado
    public Payment createAndProcessPayment(double amount, String type, String cardNumber, String walletProvider, String number) {
        PaymentFactory factory;
        Payment payment;

        switch (type.toLowerCase()) {
            case "tarjeta" -> {
                factory = new CardPaymentFactory(cardNumber);
                payment = factory.createPayment(amount);
            }
            case "efecty" -> {
                factory = new CashPaymentFactory();
                payment = factory.createPayment(amount);
            }
            case "billetera digital" -> {
                factory = new DigitalWalletPaymentFactory(walletProvider, number);
                payment = factory.createPayment(amount);
            }
            default -> throw new IllegalArgumentException("Tipo de pago no válido: " + type);
        }

        payment.processPayment();
        db.getListaPagos().add(payment); // guardamos el pago en la base de datos simulada
        return payment;
    }

    // Lista todos los pagos registrados
    public List<Payment> listPayments() {
        return db.getListaPagos();
    }

    // Busca un pago por ID
    public Payment findPaymentById(String id) {
        return db.getListaPagos()
                .stream()
                .filter(p -> p.getIdPayment().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Confirma manualmente un pago Efecty
    public void confirmEfectyPayment(String referenceCode) {
        for (Payment payment : db.getListaPagos()) {
            if (payment instanceof CashPayment efecty && efecty.getReferenceCode().equals(referenceCode)) {
                efecty.confirmPayment();
                break;
            }
        }
    }


}
